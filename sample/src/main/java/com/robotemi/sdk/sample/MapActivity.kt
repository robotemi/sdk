package com.robotemi.sdk.sample

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.robotemi.sdk.Robot
import com.robotemi.sdk.TtsRequest.Companion.create
import com.robotemi.sdk.map.GREEN_PATH
import com.robotemi.sdk.map.LOCATION
import com.robotemi.sdk.map.Layer
import com.robotemi.sdk.map.LayerPose
import com.robotemi.sdk.map.MapDataModel
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.sample.EditDialog.EditorActionListener
import com.robotemi.sdk.sample.databinding.ActivityMapBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.roundToInt


class MapActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_FILE_PICKER = 1

        private const val AUTHORITY = "${BuildConfig.APPLICATION_ID}.provider"
    }

    private lateinit var binding: ActivityMapBinding

    @Volatile
    private var bitmap: Bitmap? = null

    @Volatile
    private var mapDataModel: MapDataModel? = null

    private val robot: Robot
        get() = Robot.getInstance()

    private val singleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.ibBack.setOnClickListener { finish() }
        binding.textViewMapElements.setOnClickListener { binding.refreshMap() }
        binding.textViewMapElements.movementMethod = ScrollingMovementMethod()

        binding.buttonBackupMap.setOnClickListener {
            // This code block will take current map from temi and create a backup file as ParcelFileDescriptor
            // Write the file to a desired location to finish the backup
            val withoutUI = binding.checkBoxLoadMapWithoutUI.isChecked
            val parcelFileDescriptor =
                Robot.getInstance().getCurrentMapBackupFile(withoutUI) ?: return@setOnClickListener
            lifecycleScope.launch(Dispatchers.IO) {
                val dir = File(applicationContext.getExternalFilesDir(null), "maps")
                if (!dir.exists()) {
                    dir.mkdir()
                }

                val file = File(dir, "map-${System.currentTimeMillis()}.tar.gz")
                file.createNewFile()
                val inputStream = ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor)
                Log.d("Map-SDK", "Loading map 1")

                inputStream.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }

                if (file.length() > 0) {
                    Log.d("Map-SDK", "Loading map 2")

                    launch(Dispatchers.Main) {
                        Toast.makeText(applicationContext, "File generated", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.buttonLoadMapFromPrivateFile.setOnClickListener {
            // This code block will load a map backup to temi.
            // The backup files are taken from either application's internal storage or external storage.
            // These files are securely store this way and transferred by content provider that only temi launcher can read.

            // The folder needs to be declared in res/xml/provider_paths.xml
            // <files-path name="map_internal_file" path="maps/" />
            val internalMapDirectory = File(filesDir, "maps")

            // The folder needs to be declared in res/xml/provider_paths.xml
            // <external-files-path name="map_external_file" path="maps/"/>
            val externalMapDirectory = File(getExternalFilesDir(null), "maps")

            Log.d("SDK-Path", "externalMapDirectory $externalMapDirectory")

            lifecycleScope.launch(Dispatchers.IO) {
                val internalFiles = internalMapDirectory.listFiles()?.toList() ?: listOf()
                val externalFiles = externalMapDirectory.listFiles()?.toList() ?: listOf()
                val files = (internalFiles + externalFiles).filter {
                    it.isFile
                            && (it.path.endsWith("tar.gz", true)
                            || it.path.endsWith("zip", true)
                            || it.path.endsWith("tgz", true)
                            || it.path.endsWith("tar", true))
                }

                val builder = AlertDialog.Builder(this@MapActivity)

                if (files.isNotEmpty()) {
                    builder.setItems(files.map { it.path }.toTypedArray()) { _, which ->
                        val fileSelected = files[which]
                        Log.d("SDK-Sample", "Map file selected ${fileSelected.path}")
                        val uri =
                            FileProvider.getUriForFile(this@MapActivity, AUTHORITY, fileSelected)
                        loadMap(uri)

                        Log.d("SDK-Sample", "Map file loaded")

                    }.setTitle("Select one map file to load")
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                } else {
                    builder.setTitle("No map backup files found")
                        .setMessage("This sample takes map files from\n/sdcard/Android/data/com.robotemi.sdk.sample/files/maps/\nand /data/data/com.robotemi.sdk.sample/files/maps/")
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                }

                launch(Dispatchers.Main) {
                    builder.show()
                }
            }
        }

        binding.buttonLoadMapFromFileSelector.setOnClickListener {
            // This code block is launching a file picker to select a public accessible backup file.
            // So if you app is loaded in the USB drive on V3 robot, this could be an easy way to load it.

            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            val mimeTypes = arrayOf("application/gzip", "application/zip")
            intent.setType("*/*")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(intent, REQUEST_FILE_PICKER)
        }

        binding.buttonLoadMapFromPublicFile.setOnClickListener {
            // This is possible but not recommended.
            // As Android doesn't recommend to use file:// scheme to send files.
            val file = File("/sdcard/map-1690428181150.tar.gz")
            if (file.exists()) {
                loadMap(Uri.fromFile(file))
            } else {
                Toast.makeText(this, "Please place a map file at public storage", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnResetMap.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                val resp = robot.resetMap(false, saveHomeBaseIfCharging = true)
                withContext(Dispatchers.Main) {
                    printLog("Reset map result $resp")
                    binding.progressBar.visibility = View.GONE
                    binding.refreshMap()
                }
            }
        }

        binding.btnFinishMapping.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
//                val resp = robot.finishMapping("MAP-${System.currentTimeMillis()}")
                val resp = robot.finishMapping()
                withContext(Dispatchers.Main) {
                    printLog("Finish mapping result $resp")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        binding.btnFinishMapping.setOnLongClickListener {
            val resp = robot.updateMapName("MAP-${System.currentTimeMillis()}")
            printLog("Update map name result $resp")
            binding.refreshMap()
            true
        }

        binding.btnContinueMapping.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                val resp = robot.continueMapping()
                withContext(Dispatchers.Main) {
                    printLog("Continue mapping result $resp")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
        var factor = 1f
        binding.btnCreatePath.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                val layer = Layer.upsertLayer(null, GREEN_PATH, listOf(
                    LayerPose(0.5f, 0.5f, 0f),
                    LayerPose(0.5f, 1f * factor, 0f),
                    LayerPose(1.5f, 1.5f * factor, 0f),
                    LayerPose(1.5f * factor, 0.5f, 0f),
                    LayerPose(0.5f, 0.5f, 0f)
                ))
                factor *= 2
                if (layer == null) {
                    // Invalid parameter
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                    }
                    return@launch
                }
                val resp = robot.upsertMapLayer(layer)
                withContext(Dispatchers.Main) {
                    printLog("Create path result $resp")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }


        binding.btnMovePath.setOnClickListener {
            val path = mapDataModel?.greenPaths?.firstOrNull() ?: return@setOnClickListener
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                val layer = Layer.upsertLayer(path.layerId, GREEN_PATH, listOf(
                    LayerPose(2.5f, 2.5f, 0f),
                    LayerPose(2.5f, 3.5f, 0f),
                    LayerPose(3.5f, 3.5f, 0f),
                    LayerPose(3.5f, 2.5f, 0f),
                    LayerPose(2.5f, 2.5f, 0f)
                ))
                if (layer == null) {
                    // Invalid parameter
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                    }
                    return@launch
                }
                val resp = robot.upsertMapLayer(layer)
                withContext(Dispatchers.Main) {
                    printLog("Update path result $resp")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        binding.btnDeletePath.setOnClickListener {
            val path = mapDataModel?.greenPaths?.firstOrNull()
            if (path != null) {
                binding.progressBar.visibility = View.VISIBLE
                lifecycleScope.launch(Dispatchers.IO) {
                    val resp = robot.deleteMapLayer(path.layerId, GREEN_PATH)
                    withContext(Dispatchers.Main) {
                        printLog("Delete path result $resp")
                        binding.refreshMap()
                    }
                }
            }
        }

        binding.btnUpsertLocation.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch(Dispatchers.IO) {
                val layer = Layer.upsertLayer(
                    "office entrance",
                    LOCATION,
                    listOf(LayerPose(1.5f, 1.5f, 1f)),
                    tiltAngle = 10)
                if (layer == null) {
                    // Invalid parameter
                    withContext(Dispatchers.Main) {
                        binding.progressBar.visibility = View.GONE
                    }
                    return@launch
                }
                val resp = robot.upsertMapLayer(layer)
                withContext(Dispatchers.Main) {
                    printLog("Add / Update location result $resp")
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

        binding.btnRenameLocation.setOnClickListener {
            val dialog = EditDialog(
                this,
                robot.locations.toMutableList(),
                object : EditorActionListener {
                    override fun editCompleted(
                        editDialog: EditDialog,
                        oldLocationName: String,
                        newLocationName: String
                    ) {
                        if (oldLocationName == newLocationName) return
                        binding.progressBar.visibility = View.VISIBLE
                        lifecycleScope.launch(Dispatchers.IO) {
                            val layer = Layer.upsertLayer(
                                oldLocationName,
                                LOCATION,
                                listOf(LayerPose(1.5f, 1.5f, 1f)),
                                10
                            )
                            if (layer == null) {
                                // Invalid parameter
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.visibility = View.GONE
                                }
                                return@launch
                            }

//                            val resp = robot.renameLocation(oldLocationName, newLocationName)
                            val resp = robot.renameLocation(oldLocationName, newLocationName, layer)
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.GONE
                                if (resp == 200) {
                                    printLog("rename location success:${robot.getCurrentFloor()?.locations} ")
                                } else {
                                    robot.speak(
                                        create(
                                            "rename the $newLocationName location failed.",
                                            true
                                        )
                                    )
                                }
                                editDialog.dismiss()
                            }
                        }
                    }
                })
            dialog.show()
        }


        binding.btnCreatePathOnFloor.setOnClickListener {
            showInputDialog("CreatePathOnFloor")
        }

        binding.btnMovePathOnFloor.setOnClickListener {
            showInputDialog("MovePathOnFloor")
        }

        binding.btnUpsertLocationOnFloor.setOnClickListener {
            showInputDialog("UpsertLocationOnFloor")
        }

        binding.btnDeletePathOnFloor.setOnClickListener {
            showInputDialog("DeletePathOnFloor")
        }

        binding.refreshMap()
    }

    private fun ActivityMapBinding.refreshMap() {
        progressBar.visibility = View.VISIBLE
        singleThreadExecutor.execute {
            mapDataModel = Robot.getInstance().getMapData() ?: return@execute
            val mapImage = mapDataModel!!.mapImage
            Log.i("Map-mapImage", mapDataModel!!.mapImage.typeId)

            bitmap = Bitmap.createBitmap(
                mapImage.data.map { Color.argb((it * 2.55).roundToInt(), 0, 0, 0) }.toIntArray(),
                mapImage.cols,
                mapImage.rows,
                Bitmap.Config.ARGB_8888
            )
            runOnUiThread {
                progressBar.visibility = View.GONE
                buttonBackupMap.visibility = View.VISIBLE
                buttonLoadMapFromPrivateFile.visibility = View.VISIBLE
                buttonLoadMapFromFileSelector.visibility = View.VISIBLE
                textViewMapElements.text = ""
                Log.i("Map-mapId", mapDataModel!!.mapId)
                textViewMapElements.append("[map_id]: ${mapDataModel!!.mapId} \n")
                Log.i("Map-mapInfo", mapDataModel!!.mapInfo.toString())
                textViewMapElements.append("[map_info]: ${mapDataModel!!.mapInfo} \n")
                Log.i("Map-greenPaths", mapDataModel!!.greenPaths.toString())
                textViewMapElements.append("[map_green_path]: ${mapDataModel!!.greenPaths} \n")
                Log.i("Map-virtualWalls", mapDataModel!!.virtualWalls.toString())
                textViewMapElements.append("[map_virtual_walls]: ${mapDataModel!!.virtualWalls} \n")
                Log.i("Map-zones", mapDataModel!!.zones.toString())
                textViewMapElements.append("[map_zones]: ${mapDataModel!!.zones} \n")
                Log.i("Map-locations", mapDataModel!!.locations.toString())
                textViewMapElements.append("[map_locations]: ${mapDataModel!!.locations} \n")
                textViewMapElements.append("[map_eraser_data_length]: ${mapDataModel!!.mapEraser.firstOrNull()?.layerData?.length} \n")
                textViewMapElements.append("[map_name]: ${mapDataModel!!.mapName} \n")
                imageViewMap.setImageBitmap(bitmap)
            }
        }
    }

    private fun printLog(log: String) {
        binding.textViewMapElements.append("$log \n")
    }

    override fun onDestroy() {
        bitmap?.recycle()
        singleThreadExecutor.shutdownNow()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FILE_PICKER && resultCode == RESULT_OK) {
            if (data != null) {
                val selectedFileUri = data.data
                Log.d("SDK-Sample", "Map file selected from file selector, uri $selectedFileUri")

                if (selectedFileUri != null) {
                    loadMap(selectedFileUri)
                    Log.d("SDK-Sample", "Map file loaded")
                    // It is safe to delete the file here if needed.
                }
            }
        }
    }

    /**
     * We can set a few arguments when loading map.
     * They function the same as [Robot.loadMap]
     */
    private fun loadMap(uri: Uri) {
        val reposeRequired = binding.checkBoxLoadMapWithRepose.isChecked
        val withoutUI = binding.checkBoxLoadMapWithoutUI.isChecked
        val position: Position? = if (binding.checkBoxLoadMapFromPose.isChecked) {
            Position(1f, 1f, 1f)
        } else {
            null
        }
        Robot.getInstance().loadMapWithBackupFile(
            uri,
            reposeRequired = reposeRequired,
            withoutUI = withoutUI,
            position = position
        )
    }

    private fun showInputDialog(type: String) {
        var factor = 1f
        val dialogView = layoutInflater.inflate(R.layout.dialog_input_loading, null)
        val builder = android.app.AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()
        val editTextInput = dialogView.findViewById<EditText>(R.id.editTextInput)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

        when (type) {
            "CreatePathOnFloor" -> {
                editTextInput.hint = "Please enter a number"
            }

            "MovePathOnFloor" -> {
                editTextInput.hint = "Please enter a number"
            }

            "UpsertLocationOnFloor" -> {
                editTextInput.hint = "Please enter a number"
            }

            "DeletePathOnFloor" -> {
                editTextInput.hint = "Please enter a number"
            }
        }
        btnConfirm.setOnClickListener {
            val input = editTextInput.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter the content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            when (type) {
                "CreatePathOnFloor" -> {
                    binding.progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        val layer = Layer.upsertLayer(null, GREEN_PATH, listOf(
                            LayerPose(0.5f, 0.5f, 0f),
                            LayerPose(0.5f, 1f * factor, 0f),
                            LayerPose(1.5f, 1.5f * factor, 0f),
                            LayerPose(1.5f * factor, 0.5f, 0f),
                            LayerPose(0.5f, 0.5f, 0f)
                        ))
                        factor *= 2
                        if (layer == null) {
                            // Invalid parameter
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.GONE
                            }
                            return@launch
                        }
                        val resp = robot.upsertMapLayer(layer,input.toIntOrNull())
                        withContext(Dispatchers.Main) {
                            printLog("Create path result $resp")
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }

                "MovePathOnFloor" -> {
                    val path = mapDataModel?.greenPaths?.firstOrNull() ?: return@setOnClickListener
                    binding.progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        val layer = Layer.upsertLayer(path.layerId, GREEN_PATH, listOf(
                            LayerPose(2.5f, 2.5f, 0f),
                            LayerPose(2.5f, 3.5f, 0f),
                            LayerPose(3.5f, 3.5f, 0f),
                            LayerPose(3.5f, 2.5f, 0f),
                            LayerPose(2.5f, 2.5f, 0f)
                        ))
                        if (layer == null) {
                            // Invalid parameter
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.GONE
                            }
                            return@launch
                        }
                        val resp = robot.upsertMapLayer(layer,input.toIntOrNull())
                        withContext(Dispatchers.Main) {
                            printLog("Update path result $resp")
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }

                "UpsertLocationOnFloor" -> {
                    binding.progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        val layer = Layer.upsertLayer(
                            "office entrance",
                            LOCATION,
                            listOf(LayerPose(1.5f, 1.5f, 1f)),
                            tiltAngle = 10)
                        if (layer == null) {
                            // Invalid parameter
                            withContext(Dispatchers.Main) {
                                binding.progressBar.visibility = View.GONE
                            }
                            return@launch
                        }
                        val resp = robot.upsertMapLayer(layer,input.toIntOrNull())
                        withContext(Dispatchers.Main) {
                            printLog("Add / Update location result $resp")
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
                "DeletePathOnFloor" -> {
                    binding.progressBar.visibility = View.VISIBLE
                    lifecycleScope.launch(Dispatchers.IO) {
                        val pathLayerId = mapDataModel?.greenPaths?.firstOrNull()?.layerId
                        if (pathLayerId == null) {
                            withContext(Dispatchers.Main) {
                                printLog("No GREEN_PATH layer found to delete.")
                                binding.progressBar.visibility = View.GONE
                            }
                            return@launch
                        }
                        val resp = robot.deleteMapLayer(pathLayerId,GREEN_PATH,input.toIntOrNull())
                        withContext(Dispatchers.Main) {
                            printLog("Add / Update path result $resp")
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                }
            }
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setOnDismissListener {
        }
        dialog.show()
    }
}