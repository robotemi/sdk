package com.robotemi.sdk.sample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.robotemi.sdk.Robot
import com.robotemi.sdk.face.ContactModel
import com.robotemi.sdk.face.OnContinuousFaceRecognizedListener
import com.robotemi.sdk.sample.databinding.ActivityFaceBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException

@SuppressLint("SetTextI18n")
class FaceActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICKER = 2
        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"
    }

    private lateinit var binding: ActivityFaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibBack.setOnClickListener { finish() }

        binding.btnInsertFromLocalFile.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                insertFromLocalFile()
            }
        }

        binding.btnInsertFromUrl.setOnClickListener {
            insertFromUrl()
        }

        binding.btnGetAllFaces.setOnClickListener {
            queryAllFacesRegistered()
        }

        binding.btnGetAllFacesOfUid.setOnClickListener {
//            queryAllFacesRegistered("uid = ?", arrayOf("0"))
//            queryAllFacesRegistered("uid IN (?,?)", arrayOf("0", "2"))
//            queryAllFacesRegistered("username = ?", arrayOf("Jane Doe"))
            queryAllFacesRegistered("username LIKE ?", arrayOf("%Ja%")) // username contains Ja
        }

        binding.btnDeleteAllFaces.setOnClickListener {
            deleteAll()
        }

        binding.btnDeleteAllFacesByUid.setOnClickListener {
            deleteAllByUid("1", "2")
            deleteAllByUsername("Jane Doe")
        }

        binding.btnInsertFromCamera.setOnClickListener {
            val photo = "photo.jpeg"
            val photoFile = File(cacheDir, photo)
            val uri = FileProvider.getUriForFile(this, AUTHORITY, photoFile)
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }

        binding.btnInsertFromPhotoLibrary.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICKER)
        }

        binding.btnInsertFromContentProvider.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val facesDir = File(filesDir, "faces")
                try {
                    facesDir.mkdir()

                    val files = assets.list("faces")

                    files?.forEach { fileName ->
                        val sourceFilePath = "faces/$fileName"
                        val destinationFile = File(facesDir, fileName)

                        assets.open(sourceFilePath).use { inputStream ->
                            destinationFile.outputStream().use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                    }
                } catch (e: IOException) {
                    Log.e("ERR", "Copy assets to file dir error", e)
                }

                val uris = facesDir.listFiles()?.toList()?.map {
                    Log.d("FileProvider-add", "Add file ${it.name}")
                    val uri = FileProvider.getUriForFile(this@FaceActivity, AUTHORITY, it)
                    grantUriPermission("com.robotemi.face", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    uri
                } ?: listOf()

                val contentValues = ContentValues()
                contentValues.put("uid", "0")
                contentValues.put("username", "Jane Doe")
                val bundle = Bundle()
                bundle.putParcelableArrayList("uri", ArrayList(uris))

                try {
                    val retUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        contentResolver.insert(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), contentValues, bundle)
                    } else {
                        // Only support Android R.
                        null
                    }
                    Log.d("Insert", "$retUri")
                    binding.tvLog.text = "${System.currentTimeMillis()}: Image set ready"
                } catch (e: IllegalArgumentException) {
                    Log.e("Insert", "Insert Exception", e)
                    binding.tvLog.text = "${System.currentTimeMillis()}: Image set failed"
                }
            }
        }

        createRepeatingTest()
        createParallelTest()

        binding.btnStartFaceRecognition.setOnClickListener { v ->
            Robot.getInstance().startFaceRecognition(true)
        }

        binding.btnStopFaceRecognition.setOnClickListener { v ->
            Robot.getInstance().stopFaceRecognition()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                // Thumbnail will not be null if you doesn't set takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                val drawable = BitmapDrawable(imageBitmap)
                binding.tvLog.background = drawable
            }

            val photo = "photo.jpeg"
            val photoFile = File(cacheDir, photo)

            // I have granted read permission here, also declared FileProvider in res.xml and AndroidManifest.
            val uri = FileProvider.getUriForFile(this, AUTHORITY, photoFile)
            grantUriPermission("com.robotemi.face", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)

            val contentValues = ContentValues()
            contentValues.put("uid", "uu-myself")
            contentValues.put("username", "Me the robot owner")
            contentValues.put("uri", uri.toString())
            try {
                val retUri = contentResolver.insert(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), contentValues)
                binding.tvLog.text = "${System.currentTimeMillis()}: Image set ready from camera"
            } catch (e: IllegalArgumentException) {
                Log.e("Insert", "Insert Exception", e)
                binding.tvLog.text = "${System.currentTimeMillis()}: Image set failed from camera"
            }
        } else if (requestCode == REQUEST_IMAGE_PICKER && resultCode == Activity.RESULT_OK) {
            Log.d("Picker", "$data")
            Log.d("Picker", "${data?.data}")
            val uri = data?.data
            val contentValues = ContentValues()
            contentValues.put("uid", "uu-photo-library-1")
            contentValues.put("username", "Someone I know")
            contentValues.put("uri", uri.toString())
            grantUriPermission("com.robotemi.face", uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                val retUri = contentResolver.insert(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), contentValues)
                binding.tvLog.text = "${System.currentTimeMillis()}: Image set ready from gallery"
            } catch (e: IllegalArgumentException) {
                Log.e("Insert", "Insert Exception", e)
                binding.tvLog.text = "${System.currentTimeMillis()}: Image set failed from gallery"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun insertFromLocalFile() {
        lifecycleScope.launch (Dispatchers.IO) {
            // Please add a file to this path.
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "sdk-sample.jpeg")
            val contentValues = ContentValues()
            contentValues.put("uid", "1")
            contentValues.put("username", "Jane Doe")

            contentValues.put("uri", file.toURI().toString())

            try {
                val retUri = contentResolver.insert(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), contentValues)
                Log.d("Insert", "$retUri")
                binding.tvLog.text = "${System.currentTimeMillis()}: Image set ready"
            } catch (e: IllegalArgumentException) {
                Log.e("Insert", "Insert Exception", e)
                binding.tvLog.text = "${System.currentTimeMillis()}: Image set failed"
            }
        }
    }

    @SuppressLint("NewApi")
    private fun insertFromUrl() {
        val img = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQREZ_D6BLC36i4kt8QdNVbXbmW-idmWRD5Xg"
        val img1 = "http://192.168.1.11:8000/face1.jpg"
        val contentValues = ContentValues()
        contentValues.put("uid", "1")
        contentValues.put("username", "John Doe URL")

        // To add only one image
//        contentValues.put("uri", Uri.parse(img).toString())

        // To add multiple images for one person
        val bundle = Bundle()
        bundle.putParcelableArrayList("uri", arrayListOf(Uri.parse(img1), Uri.parse(img)))

        try {
            val retUri = contentResolver.insert(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), contentValues, bundle)
            Log.d("Insert", "$retUri")
            binding.tvLog.text = "${System.currentTimeMillis()}: Image set ready"
        } catch (e: IllegalArgumentException) {
            Log.e("Insert", "Insert Exception", e)
            binding.tvLog.text = "${System.currentTimeMillis()}: Image set failed"
        }
    }

    private fun deleteAll() {
        try {
            val retUri = contentResolver.delete(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), "", arrayOf())
            Log.d("Delete", "$retUri")
        } catch (e: IllegalArgumentException) {
            Log.e("Delete", "Insert Exception", e)
        }
    }

    private fun deleteAllByUid(vararg uids: String) {
        try {
            val retUri = contentResolver.delete(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), "uid", uids)
            Log.d("Delete", "$retUri")
        } catch (e: IllegalArgumentException) {
            Log.e("Delete", "Delete Exception", e)
        }
    }

    private fun deleteAllByUsername(vararg usernames: String) {
        try {
            val retUri = contentResolver.delete(Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"), "username", usernames)
            Log.d("Delete", "$retUri")
        } catch (e: IllegalArgumentException) {
            Log.e("Delete", "Delete Exception", e)
        }
    }

    private fun queryAllFacesRegistered(selection: String? = null, selectionArgs: Array<String>? = null) {
        try {
            val cursor = contentResolver.query(
                Uri.parse("content://com.robotemi.sdk.TemiSdkDocumentContentProvider/face"),
                arrayOf("username", "uid"),
                selection,
                selectionArgs,
                null)

            if (cursor != null) {
                val indexOrUserName = cursor.getColumnIndex("username")
                val colCount = cursor.columnCount
                var counter = 0
                var string = ""
                while (cursor.moveToNext()) {
                    val username = cursor.getString(0)
                    val uid = cursor.getString(1)
                    string += "$counter: USER $username, Uid $uid\n"
                    counter++
                }
                cursor.close()
                binding.tvLog.text = string
                Log.d("Query", "$indexOrUserName, $colCount")
            }

        } catch (e: IllegalArgumentException) {
            Log.e("Query", "Query Exception", e)
        }
    }

    /**
     * This is simulating use cases that apps keeps turning on and off face recognition
     */
    private fun createRepeatingTest() {
        val onContinuousFaceRecognizedListener = object: OnContinuousFaceRecognizedListener {
            var faceDetected: Int = 0

            override fun onContinuousFaceRecognized(contactModelList: List<ContactModel>) {
                faceDetected += contactModelList.size
            }
        }

        var job: Job? = null

        binding.btnPressureTest.setOnClickListener {
            binding.tvPressureTestStatus.text = "${System.currentTimeMillis()}: Pressure test started"

            var failure = 0

            Robot.getInstance().addOnContinuousFaceRecognizedListener(onContinuousFaceRecognizedListener)
            lifecycleScope.launch(Dispatchers.IO) {
                job?.cancelAndJoin()
                job = lifecycleScope.launch(Dispatchers.IO) {
                    for (i in 0..1000) {
                        ensureActive()
                        onContinuousFaceRecognizedListener.faceDetected = 0
                        Robot.getInstance().startFaceRecognition()
                        // Check if there are valid face detected in 5 seconds, if not record a failure.
                        // After 5 seconds, stop face recognition.
                        runOnUiThread {
                            binding.tvPressureTestStatus.text = "${System.currentTimeMillis()}: Pressure test, loop $i running, total failure $failure"
                        }
                        ensureActive()
                        delay(5000)
                        Robot.getInstance().stopFaceRecognition()
                        if (onContinuousFaceRecognizedListener.faceDetected == 0) {
                            failure++
                        }

                        runOnUiThread {
                            binding.tvPressureTestStatus.text = "${System.currentTimeMillis()}: Pressure test started, loop $i completed, detect faces ${onContinuousFaceRecognizedListener.faceDetected}, total failure $failure"
                        }
                        ensureActive()
                        delay(5000)
                    }
                    Robot.getInstance().removeOnContinuousFaceRecognizedListener(onContinuousFaceRecognizedListener)
                }
            }

        }

        binding.tvPressureTestStatus.text = ""
    }

    /**
     * This abusing the face API, creating a lot of parallel tasks to open and close face recognition and camera
     */
    private fun createParallelTest() {
        val onContinuousFaceRecognizedListener2 = object : OnContinuousFaceRecognizedListener {
            var faceDetected: Int = 0

            override fun onContinuousFaceRecognized(contactModelList: List<ContactModel>) {
                faceDetected += contactModelList.size
            }
        }


        binding.btnPressureTestBug.setOnClickListener {
            binding.tvPressureTestStatusBug.text =
                "${System.currentTimeMillis()}: Pressure (Parallel) test started"

            var failure = 0

            Robot.getInstance()
                .addOnContinuousFaceRecognizedListener(onContinuousFaceRecognizedListener2)
            lifecycleScope.launch(Dispatchers.IO) {
                for (i in 0..1000) {
                    ensureActive()
                    onContinuousFaceRecognizedListener2.faceDetected = 0
                    Robot.getInstance().startFaceRecognition()
                    // Check if there are valid face detected in 5 seconds, if not record a failure.
                    // After 5 seconds, stop face recognition.
                    withContext(Dispatchers.Main) {
                        binding.tvPressureTestStatusBug.text =
                            "${System.currentTimeMillis()}: Pressure test, loop $i running, total failure $failure"
                    }
                    ensureActive()
                    delay(5000)
                    Robot.getInstance().stopFaceRecognition()
                    if (onContinuousFaceRecognizedListener2.faceDetected == 0) {
                        failure++
                    }

                    withContext(Dispatchers.Main) {
                        binding.tvPressureTestStatusBug.text =
                            "${System.currentTimeMillis()}: Pressure test started, loop $i completed, detect faces ${onContinuousFaceRecognizedListener2.faceDetected}, total failure $failure"
                    }
                    ensureActive()
                    delay(5000)
                }
                Robot.getInstance()
                    .removeOnContinuousFaceRecognizedListener(onContinuousFaceRecognizedListener2)
            }
        }

        binding.tvPressureTestStatusBug.text = ""
    }
}