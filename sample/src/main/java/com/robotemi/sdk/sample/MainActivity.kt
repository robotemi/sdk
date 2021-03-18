package com.robotemi.sdk.sample

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.RemoteException
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.robotemi.sdk.*
import com.robotemi.sdk.Robot.*
import com.robotemi.sdk.Robot.Companion.getInstance
import com.robotemi.sdk.TtsRequest.Companion.create
import com.robotemi.sdk.activitystream.ActivityStreamObject
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage
import com.robotemi.sdk.constants.ContentType
import com.robotemi.sdk.constants.Page
import com.robotemi.sdk.constants.Platform
import com.robotemi.sdk.constants.SdkConstants
import com.robotemi.sdk.exception.OnSdkExceptionListener
import com.robotemi.sdk.exception.SdkException
import com.robotemi.sdk.face.ContactModel
import com.robotemi.sdk.face.OnFaceRecognizedListener
import com.robotemi.sdk.listeners.*
import com.robotemi.sdk.map.MapModel
import com.robotemi.sdk.map.OnLoadMapStatusChangedListener
import com.robotemi.sdk.model.CallEventModel
import com.robotemi.sdk.model.DetectionData
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener
import com.robotemi.sdk.navigation.listener.OnReposeStatusChangedListener
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.navigation.model.SafetyLevel
import com.robotemi.sdk.navigation.model.SpeedLevel
import com.robotemi.sdk.permission.OnRequestPermissionResultListener
import com.robotemi.sdk.permission.Permission
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener
import com.robotemi.sdk.sequence.SequenceModel
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity(), NlpListener, OnRobotReadyListener,
    ConversationViewAttachesListener, WakeupWordListener, ActivityStreamPublishListener,
    TtsListener, OnBeWithMeStatusChangedListener, OnGoToLocationStatusChangedListener,
    OnLocationsUpdatedListener, OnConstraintBeWithStatusChangedListener,
    OnDetectionStateChangedListener, AsrListener, OnTelepresenceEventChangedListener,
    OnRequestPermissionResultListener, OnDistanceToLocationChangedListener,
    OnCurrentPositionChangedListener, OnSequencePlayStatusChangedListener, OnRobotLiftedListener,
    OnDetectionDataChangedListener, OnUserInteractionChangedListener, OnFaceRecognizedListener,
    OnConversationStatusChangedListener, OnTtsVisualizerWaveFormDataChangedListener,
    OnTtsVisualizerFftDataChangedListener, OnReposeStatusChangedListener,
    OnLoadMapStatusChangedListener, OnDisabledFeatureListUpdatedListener,
    OnMovementVelocityChangedListener, OnMovementStatusChangedListener, OnSdkExceptionListener {

    private lateinit var robot: Robot

    private val executorService = Executors.newSingleThreadExecutor()

    /**
     * Setting up all the event listeners
     */
    override fun onStart() {
        super.onStart()
        robot.addOnRobotReadyListener(this)
        robot.addNlpListener(this)
        robot.addOnBeWithMeStatusChangedListener(this)
        robot.addOnGoToLocationStatusChangedListener(this)
        robot.addConversationViewAttachesListener(this)
        robot.addWakeupWordListener(this)
        robot.addTtsListener(this)
        robot.addOnLocationsUpdatedListener(this)
        robot.addOnConstraintBeWithStatusChangedListener(this)
        robot.addOnDetectionStateChangedListener(this)
        robot.addAsrListener(this)
        robot.addOnDistanceToLocationChangedListener(this)
        robot.addOnCurrentPositionChangedListener(this)
        robot.addOnSequencePlayStatusChangedListener(this)
        robot.addOnRobotLiftedListener(this)
        robot.addOnDetectionDataChangedListener(this)
        robot.addOnUserInteractionChangedListener(this)
        robot.addOnConversationStatusChangedListener(this)
        robot.addOnTtsVisualizerWaveFormDataChangedListener(this)
        robot.addOnTtsVisualizerFftDataChangedListener(this)
        robot.addOnReposeStatusChangedListener(this)
        robot.addOnMovementVelocityChangedListener(this)
        robot.showTopBar()
    }

    /**
     * Removing the event listeners upon leaving the app.
     */
    override fun onStop() {
        super.onStop()
        robot.removeOnRobotReadyListener(this)
        robot.removeNlpListener(this)
        robot.removeOnBeWithMeStatusChangedListener(this)
        robot.removeOnGoToLocationStatusChangedListener(this)
        robot.removeConversationViewAttachesListener(this)
        robot.removeWakeupWordListener(this)
        robot.removeTtsListener(this)
        robot.removeOnLocationsUpdateListener(this)
        robot.removeOnDetectionStateChangedListener(this)
        robot.removeAsrListener(this)
        robot.removeOnDistanceToLocationChangedListener(this)
        robot.removeOnCurrentPositionChangedListener(this)
        robot.removeOnSequencePlayStatusChangedListener(this)
        robot.removeOnRobotLiftedListener(this)
        robot.removeOnDetectionDataChangedListener(this)
        robot.addOnUserInteractionChangedListener(this)
        robot.stopMovement()
        if (robot.checkSelfPermission(Permission.FACE_RECOGNITION) == Permission.GRANTED) {
            robot.stopFaceRecognition()
        }
        robot.removeOnConversationStatusChangedListener(this)
        robot.removeOnTtsVisualizerWaveFormDataChangedListener(this)
        robot.removeOnTtsVisualizerFftDataChangedListener(this)
        robot.removeOnReposeStatusChangedListener(this)
        robot.removeOnMovementVelocityChangedListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        verifyStoragePermissions(this)
        robot = getInstance()
        initOnClickListener()
        tvLog.movementMethod = ScrollingMovementMethod.getInstance()
        robot.addOnRequestPermissionResultListener(this)
        robot.addOnTelepresenceEventChangedListener(this)
        robot.addOnFaceRecognizedListener(this)
        robot.addOnLoadMapStatusChangedListener(this)
        robot.addOnDisabledFeatureListUpdatedListener(this)
        robot.addOnSdkExceptionListener(this)
        robot.addOnMovementStatusChangedListener(this)
    }

    override fun onDestroy() {
        robot.removeOnRequestPermissionResultListener(this)
        robot.removeOnTelepresenceEventChangedListener(this)
        robot.removeOnFaceRecognizedListener(this)
        robot.removeOnSdkExceptionListener(this)
        robot.removeOnLoadMapStatusChangedListener(this)
        robot.removeOnDisabledFeatureListUpdatedListener(this)
        robot.removeOnMovementStatusChangedListener(this)
        if (!executorService.isShutdown) {
            executorService.shutdownNow()
        }
        super.onDestroy()
    }

    private fun initOnClickListener() {
        btnSpeak.setOnClickListener { speak() }
        btnSaveLocation.setOnClickListener { saveLocation() }
        btnGoTo.setOnClickListener { goTo() }
        btnStopMovement.setOnClickListener { stopMovement() }
        btnFollow.setOnClickListener { followMe() }
        btnskidJoy.setOnClickListener { skidJoy() }
        btnTiltAngle.setOnClickListener { tiltAngle() }
        btnTiltBy.setOnClickListener { tiltBy() }
        btnTurnBy.setOnClickListener { turnBy() }
        btnBatteryInfo.setOnClickListener { getBatteryData() }
        btnSavedLocations.setOnClickListener { savedLocationsDialog() }
        btnCallOwner.setOnClickListener { callOwner() }
        btnPublish.setOnClickListener { publishToActivityStream() }
        btnHideTopBar.setOnClickListener { hideTopBar() }
        btnShowTopBar.setOnClickListener { showTopBar() }
        btnDisableWakeup.setOnClickListener { disableWakeup() }
        btnEnableWakeup.setOnClickListener { enableWakeup() }
        btnToggleNavBillboard.setOnClickListener { toggleNavBillboard() }
        btnTogglePrivacyModeOn.setOnClickListener { privacyModeOn() }
        btnTogglePrivacyModeOff.setOnClickListener { privacyModeOff() }
        btnGetPrivacyMode.setOnClickListener { getPrivacyModeState() }
        btnEnableHardButtons.setOnClickListener { enableHardButtons() }
        btnDisableHardButtons.setOnClickListener { disableHardButtons() }
        btnIsHardButtonsDisabled.setOnClickListener { isHardButtonsEnabled() }
        btnGetOSVersion.setOnClickListener { getOSVersion() }
        btnCheckFace.setOnClickListener { requestFace() }
        btnCheckMap.setOnClickListener { requestMap() }
        btnCheckSettings.setOnClickListener { requestSettings() }
        btnCheckSequence.setOnClickListener { requestSequence() }
        btnCheckAllPermission.setOnClickListener { requestAll() }
        btnStartFaceRecognition.setOnClickListener { startFaceRecognition() }
        btnStopFaceRecognition.setOnClickListener { stopFaceRecognition() }
        btnSetGoToSpeed.setOnClickListener { setGoToSpeed() }
        btnSetGoToSafety.setOnClickListener { setGoToSafety() }
        btnToggleTopBadge.setOnClickListener { toggleTopBadge() }
        btnToggleDetectionMode.setOnClickListener { toggleDetectionMode() }
        btnToggleAutoReturn.setOnClickListener { toggleAutoReturn() }
        btnTrackUser.setOnClickListener { toggleTrackUser() }
        btnGetVolume.setOnClickListener { getVolume() }
        btnSetVolume.setOnClickListener { setVolume() }
        btnRequestToBeKioskApp.setOnClickListener { requestToBeKioskApp() }
        btnStartDetectionModeWithDistance.setOnClickListener { startDetectionWithDistance() }
        btnFetchSequence.setOnClickListener { getAllSequences() }
        btnPlayFirstSequence.setOnClickListener { playFirstSequence() }
        btnPlayFirstSequenceWithoutPlayer.setOnClickListener { playFirstSequenceWithoutPlayer() }
        btnFetchMap.setOnClickListener { getMap() }
        btnClearLog.setOnClickListener { clearLog() }
        btnNlu.setOnClickListener { startNlu() }
        btnGetAllContacts.setOnClickListener { getAllContacts() }
        btnGoToPosition.setOnClickListener { goToPosition() }
        btnStartTelepresenceToCenter.setOnClickListener { startTelepresenceToCenter() }
        btnStartPage.setOnClickListener { startPage() }
        btnRestart.setOnClickListener { restartTemi() }
        btnGetMembersStatus.setOnClickListener { getMembersStatus() }
        btnRepose.setOnClickListener { repose() }
        btnGetMapList.setOnClickListener { getMapListBtn() }
        btnLoadMap.setOnClickListener { loadMap() }
        btnLock.setOnClickListener { lock() }
        btnUnlock.setOnClickListener { unlock() }
        btnMuteAlexa.setOnClickListener { muteAlexa() }
        btnShutdown.setOnClickListener { shutdown() }
        btnLoadMapWithPosition.setOnClickListener { loadMapWithPosition() }
        btnLoadMapWithReposePosition.setOnClickListener { loadMapWithReposePosition() }
        btnLoadMapWithRepose.setOnClickListener { loadMapWithRepose() }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Places this application in the top bar for a quick access shortcut.
     */
    override fun onRobotReady(isReady: Boolean) {
        if (isReady) {
            try {
                val activityInfo =
                    packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
                // Robot.getInstance().onStart() method may change the visibility of top bar.
                robot.onStart(activityInfo)
            } catch (e: PackageManager.NameNotFoundException) {
                throw RuntimeException(e)
            }
        }
    }

    /**
     * Have the robot speak while displaying what is being said.
     */
    private fun speak() {
        val ttsRequest = create(etSpeak.text.toString().trim { it <= ' ' }, true)
        robot.speak(ttsRequest)
        hideKeyboard()
    }

    /**
     * This is an example of saving locations.
     */
    private fun saveLocation() {
        val location =
            etSaveLocation.text.toString().toLowerCase(Locale.getDefault()).trim { it <= ' ' }
        val result = robot.saveLocation(location)
        if (result) {
            robot.speak(create("I've successfully saved the $location location.", true))
        } else {
            robot.speak(create("Saved the $location location failed.", true))
        }
        hideKeyboard()
    }

    /**
     * goTo checks that the location sent is saved then goes to that location.
     */
    private fun goTo() {
        for (location in robot.locations) {
            if (location == etGoTo.text.toString().toLowerCase(Locale.getDefault())
                    .trim { it <= ' ' }
            ) {
                robot.goTo(
                    etGoTo.text.toString().toLowerCase(Locale.getDefault()).trim { it <= ' ' })
                hideKeyboard()
            }
        }
    }

    /**
     * stopMovement() is used whenever you want the robot to stop any movement
     * it is currently doing.
     */
    private fun stopMovement() {
        robot.stopMovement()
        robot.speak(create("And so I have stopped", true))
    }

    /**
     * Simple follow me example.
     */
    private fun followMe() {
        robot.beWithMe()
        hideKeyboard()
    }

    /**
     * Manually navigate the robot with skidJoy, tiltAngle, turnBy and tiltBy.
     * skidJoy moves the robot exactly forward for about a second. It controls both
     * the linear and angular velocity. Float numbers must be between -1.0 and 1.0
     */
    private fun skidJoy() {
        val t = System.currentTimeMillis()
        val end = t + 500
        val speedX = try {
            etX.text.toString().toFloat()
        } catch (e: Exception) {
            1f
        }
        val speedY = try {
            etY.text.toString().toFloat()
        } catch (e: Exception) {
            0f
        }
        printLog("speedX: $speedX, speedY: $speedY")
        while (System.currentTimeMillis() < end) {
            robot.skidJoy(speedX, speedY)
        }
    }

    /**
     * tiltAngle controls temi's head by specifying which angle you want
     * to tilt to and at which speed.
     */
    private fun tiltAngle() {
        val speed = try {
            etDistance.text.toString().toFloat()
        } catch (e: Exception) {
            1f
        }
        robot.tiltAngle(23, speed)
    }

    /**
     * turnBy allows for turning the robot around in place. You can specify
     * the amount of degrees to turn by and at which speed.
     */
    private fun turnBy() {
        val speed = try {
            etDistance.text.toString().toFloat()
        } catch (e: Exception) {
            1f
        }
        robot.turnBy(90, speed)
    }

    /**
     * tiltBy is used to tilt temi's head from its current position.
     */
    private fun tiltBy() {
        val speed = try {
            etDistance.text.toString().toFloat()
        } catch (e: Exception) {
            1f
        }
        robot.tiltBy(70, speed)
    }

    /**
     * getBatteryData can be used to return the current battery status.
     */
    private fun getBatteryData() {
        val batteryData = robot.batteryData
        if (batteryData == null) {
            printLog("getBatteryData()", "batteryData is null")
            return
        }
        if (batteryData.isCharging) {
            val ttsRequest =
                create(batteryData.level.toString() + " percent battery and charging.", true)
            robot.speak(ttsRequest)
        } else {
            val ttsRequest =
                create(batteryData.level.toString() + " percent battery and not charging.", true)
            robot.speak(ttsRequest)
        }
    }

    /**
     * Display the saved locations in a dialog
     */
    private fun savedLocationsDialog() {
        hideKeyboard()
        val locations = robot.locations.toMutableList()
        val locationAdapter = ArrayAdapter(this, R.layout.location_row, R.id.name, locations)
        val versionsDialog = AlertDialog.Builder(this@MainActivity)
        versionsDialog.setTitle("Saved Locations: (Click to delete the location)")
        versionsDialog.setPositiveButton("OK", null)
        versionsDialog.setAdapter(locationAdapter, null)
        val dialog = versionsDialog.create()
        dialog.listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Delete location \"" + locationAdapter.getItem(position) + "\" ?")
                builder.setPositiveButton("No thanks") { _: DialogInterface?, _: Int -> }
                builder.setNegativeButton("Yes") { _: DialogInterface?, _: Int ->
                    val location = locationAdapter.getItem(position) ?: return@setNegativeButton
                    val result = robot.deleteLocation(location)
                    if (result) {
                        locations.removeAt(position)
                        robot.speak(create(location + "delete successfully!", false))
                        locationAdapter.notifyDataSetChanged()
                    } else {
                        robot.speak(create(location + "delete failed!", false))
                    }
                }
                val deleteDialog: Dialog = builder.create()
                deleteDialog.show()
            }
        dialog.show()
    }

    /**
     * When adding the Nlp Listener to your project you need to implement this method
     * which will listen for specific intents and allow you to respond accordingly.
     *
     *
     * See AndroidManifest.xml for reference on adding each intent.
     */
    override fun onNlpCompleted(nlpResult: NlpResult) {
        //do something with nlp result. Base the action specified in the AndroidManifest.xml
        Toast.makeText(this@MainActivity, nlpResult.action, Toast.LENGTH_SHORT).show()
        printLog("NlpCompleted: $nlpResult")
        when (nlpResult.action) {
            ACTION_HOME_WELCOME -> robot.tiltAngle(23)
            ACTION_HOME_DANCE -> {
                val t = System.currentTimeMillis()
                val end = t + 5000
                while (System.currentTimeMillis() < end) {
                    robot.skidJoy(0f, 1f)
                }
            }
            ACTION_HOME_SLEEP -> robot.goTo(HOME_BASE_LOCATION)
        }
    }

    /**
     * callOwner is an example of how to use telepresence to call an individual.
     */
    private fun callOwner() {
        val admin = robot.adminInfo
        if (admin == null) {
            printLog("callOwner()", "adminInfo is null.")
            return
        }
        robot.startTelepresence(admin.name, admin.userId)
    }

    /**
     * publishToActivityStream takes an image stored in the resources folder
     * and uploads it to the mobile application under the Activities tab.
     */
    private fun publishToActivityStream() {
        val activityStreamObject: ActivityStreamObject
        val fileName = "puppy.png"
        val bm = BitmapFactory.decodeResource(resources, R.drawable.puppy)
        val puppiesFile = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).absolutePath,
            fileName
        )
        val fileOutputStream: FileOutputStream
        try {
            fileOutputStream = FileOutputStream(puppiesFile)
            bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        activityStreamObject = ActivityStreamObject.builder()
            .activityType(ActivityStreamObject.ActivityType.PHOTO)
            .title("Puppy")
            .media(MediaObject.create(MediaObject.MimeType.IMAGE, puppiesFile))
            .build()
        try {
            robot.shareActivityObject(activityStreamObject)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        robot.speak(create("Uploading Image", false))
    }

    private fun hideTopBar() {
        robot.hideTopBar()
    }

    private fun showTopBar() {
        robot.showTopBar()
    }

    override fun onWakeupWord(wakeupWord: String, direction: Int) {
        // Do anything on wakeup. Follow, go to location, or even try creating dance moves.
        printLog("onWakeupWord", "$wakeupWord, $direction")
    }

    override fun onTtsStatusChanged(ttsRequest: TtsRequest) {
        // Do whatever you like upon the status changing. after the robot finishes speaking
    }

    override fun onBeWithMeStatusChanged(status: String) {
        //  When status changes to "lock" the robot recognizes the user and begin to follow.
        printLog("BeWithMeStatus: $status")
    }

    override fun onGoToLocationStatusChanged(
        location: String,
        status: String,
        descriptionId: Int,
        description: String
    ) {
        printLog("GoToStatusChanged: status=$status, descriptionId=$descriptionId, description=$description")
        robot.speak(create(status, false))
        if (description.isNotBlank()) {
            robot.speak(create(description, false))
        }
    }

    override fun onConversationAttaches(isAttached: Boolean) {
        //Do something as soon as the conversation is displayed.
        printLog("onConversationAttaches", "isAttached:$isAttached")
    }

    override fun onPublish(message: ActivityStreamPublishMessage) {
        //After the activity stream finished publishing (photo or otherwise).
        //Do what you want based on the message returned.
        robot.speak(create("Uploaded.", false))
    }

    override fun onLocationsUpdated(locations: List<String>) {
        //Saving or deleting a location will update the list.
        printLog("Locations updated :\n$locations")
    }

    private fun disableWakeup() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.toggleWakeup(true)
    }

    private fun enableWakeup() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.toggleWakeup(false)
    }

    private fun toggleNavBillboard() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.toggleNavigationBillboard(!robot.navigationBillboardDisabled)
    }

    override fun onConstraintBeWithStatusChanged(isConstraint: Boolean) {
        printLog("onConstraintBeWith", "status = $isConstraint")
    }

    override fun onDetectionStateChanged(state: Int) {
        printLog("onDetectionStateChanged: state = $state")
        if (state == OnDetectionStateChangedListener.DETECTED) {
            robot.constraintBeWith()
        } else if (state == OnDetectionStateChangedListener.IDLE) {
            robot.stopMovement()
        }
    }

    /**
     * If you want to cover the voice flow in Launcher OS,
     * please add following meta-data to AndroidManifest.xml.
     * <pre>
     * <meta-data android:name="com.robotemi.sdk.metadata.KIOSK" android:value="true"></meta-data>
     *
     * <meta-data android:name="com.robotemi.sdk.metadata.OVERRIDE_NLU" android:value="true"></meta-data>
     * <pre>
     * And also need to select this App as the Kiosk Mode App in Settings > App > Kiosk.
     *
     * @param asrResult The result of the ASR after waking up temi.
    </pre></pre> */
    override fun onAsrResult(asrResult: String) {
        printLog("onAsrResult", "asrResult = $asrResult")
        try {
            val metadata = packageManager
                .getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData ?: return
            if (!robot.isSelectedKioskApp()) return
            if (!metadata.getBoolean(SdkConstants.METADATA_OVERRIDE_NLU)) return
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return
        }
        when {
            asrResult.equals("Hello", ignoreCase = true) -> {
                robot.askQuestion("Hello, I'm temi, what can I do for you?")
            }
            asrResult.equals("Play music", ignoreCase = true) -> {
                robot.speak(create("Okay, please enjoy.", false))
                robot.finishConversation()
                playMusic()
            }
            asrResult.equals("Play movie", ignoreCase = true) -> {
                robot.speak(create("Okay, please enjoy.", false))
                robot.finishConversation()
                playMovie()
            }
            asrResult.toLowerCase(Locale.getDefault()).contains("follow me") -> {
                robot.finishConversation()
                robot.beWithMe()
            }
            asrResult.toLowerCase(Locale.getDefault()).contains("go to home base") -> {
                robot.finishConversation()
                robot.goTo("home base")
            }
            else -> {
                robot.askQuestion("Sorry I can't understand you, could you please ask something else?")
            }
        }
    }

    private fun playMovie() {
        // Play movie...
        printLog("onAsrResult", "Play movie...")
    }

    private fun playMusic() {
        // Play music...
        printLog("onAsrResult", "Play music...")
    }

    private fun privacyModeOn() {
        robot.privacyMode = true
        printLog("Privacy mode: ${robot.privacyMode}")
    }

    private fun privacyModeOff() {
        robot.privacyMode = false
        printLog("Privacy mode: ${robot.privacyMode}")
    }

    private fun getPrivacyModeState() {
        printLog("Privacy mode: ${robot.privacyMode}")
    }

    private fun isHardButtonsEnabled() {
        printLog("Hard buttons disabled: ${robot.isHardButtonsDisabled}")
    }

    private fun disableHardButtons() {
        robot.isHardButtonsDisabled = true
        printLog("Hard buttons disabled: ${robot.isHardButtonsDisabled}")
    }

    private fun enableHardButtons() {
        robot.isHardButtonsDisabled = false
        printLog("Hard buttons disabled: ${robot.isHardButtonsDisabled}")
    }

    private fun getOSVersion() {
        printLog("LauncherOs: ${robot.launcherVersion}, RoboxVersion: ${robot.roboxVersion}")
    }

    override fun onTelepresenceEventChanged(callEventModel: CallEventModel) {
        printLog("onTelepresenceEvent", callEventModel.toString())
    }

    override fun onRequestPermissionResult(
        permission: Permission,
        grantResult: Int,
        requestCode: Int
    ) {
        val log = String.format("Permission: %s, grantResult: %d", permission.value, grantResult)
        printLog("onRequestPermission", log)
        if (grantResult == Permission.DENIED) {
            return
        }
        when (permission) {
            Permission.FACE_RECOGNITION -> if (requestCode == REQUEST_CODE_FACE_START) {
                robot.startFaceRecognition()
            } else if (requestCode == REQUEST_CODE_FACE_STOP) {
                robot.stopFaceRecognition()
            }
            Permission.SEQUENCE -> when (requestCode) {
                REQUEST_CODE_SEQUENCE_FETCH_ALL -> {
                    getAllSequences()
                }
                REQUEST_CODE_SEQUENCE_PLAY -> {
                    playFirstSequence(true)
                }
                REQUEST_CODE_SEQUENCE_PLAY_WITHOUT_PLAYER -> {
                    playFirstSequence(false)
                }
            }
            Permission.MAP -> if (requestCode == REQUEST_CODE_MAP) {
                getMap()
            } else if (requestCode == REQUEST_CODE_GET_MAP_LIST) {
                getMapList()
            }
            Permission.SETTINGS -> if (requestCode == REQUEST_CODE_START_DETECTION_WITH_DISTANCE) {
                startDetectionWithDistance()
            }
            else -> {
                // no-op
            }
        }
    }

    private fun requestFace() {
        if (robot.checkSelfPermission(Permission.FACE_RECOGNITION) == Permission.GRANTED) {
            printLog("You already had FACE_RECOGNITION permission.")
            return
        }
        val permissions: MutableList<Permission> = ArrayList()
        permissions.add(Permission.FACE_RECOGNITION)
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL)
    }

    private fun requestMap() {
        if (robot.checkSelfPermission(Permission.MAP) == Permission.GRANTED) {
            printLog("You already had MAP permission.")
            return
        }
        val permissions: MutableList<Permission> = ArrayList()
        permissions.add(Permission.MAP)
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL)
    }

    private fun requestSettings() {
        if (robot.checkSelfPermission(Permission.SETTINGS) == Permission.GRANTED) {
            printLog("You already had SETTINGS permission.")
            return
        }
        val permissions: MutableList<Permission> = ArrayList()
        permissions.add(Permission.SETTINGS)
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL)
    }

    private fun requestSequence() {
        if (robot.checkSelfPermission(Permission.SEQUENCE) == Permission.GRANTED) {
            printLog("You already had SEQUENCE permission.")
            return
        }
        val permissions: MutableList<Permission> = ArrayList()
        permissions.add(Permission.SEQUENCE)
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL)
    }

    private fun requestAll() {
        val permissions: MutableList<Permission> = ArrayList()
        for (permission in Permission.values()) {
            if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
                printLog("You already had $permission permission.")
                continue
            }
            permissions.add(permission)
        }
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL)
    }

    private fun startFaceRecognition() {
        if (requestPermissionIfNeeded(Permission.FACE_RECOGNITION, REQUEST_CODE_FACE_START)) {
            return
        }
        robot.startFaceRecognition()
    }

    private fun stopFaceRecognition() {
        robot.stopFaceRecognition()
    }

    private fun setGoToSpeed() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        val speedLevels: MutableList<String> = ArrayList()
        speedLevels.add(SpeedLevel.HIGH.value)
        speedLevels.add(SpeedLevel.MEDIUM.value)
        speedLevels.add(SpeedLevel.SLOW.value)
        val adapter = ArrayAdapter(this, R.layout.location_row, R.id.name, speedLevels)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Select Go To Speed Level")
            .setAdapter(adapter, null)
            .create()
        dialog.listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                robot.goToSpeed = SpeedLevel.valueToEnum(adapter.getItem(position)!!)
                printLog("Set go to speed to: ${adapter.getItem(position)}")
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun setGoToSafety() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        val safetyLevel: MutableList<String> = ArrayList()
        safetyLevel.add(SafetyLevel.HIGH.value)
        safetyLevel.add(SafetyLevel.MEDIUM.value)
        val adapter = ArrayAdapter(this, R.layout.location_row, R.id.name, safetyLevel)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Select Go To Safety Level")
            .setAdapter(adapter, null)
            .create()
        dialog.listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                robot.navigationSafety = SafetyLevel.valueToEnum(adapter.getItem(position)!!)
                printLog("Set go to safety level to: ${adapter.getItem(position)}")
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun toggleTopBadge() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.topBadgeEnabled = !robot.topBadgeEnabled
    }

    private fun toggleDetectionMode() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.detectionModeOn = !robot.detectionModeOn
    }

    private fun toggleAutoReturn() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.autoReturnOn = !robot.autoReturnOn
    }

    private fun toggleTrackUser() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        robot.trackUserOn = !robot.trackUserOn
    }

    private fun getVolume() {
        printLog("Current volume is: " + robot.volume)
    }

    private fun setVolume() {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return
        }
        val volumeList: List<String> =
            ArrayList(listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"))
        val adapter = ArrayAdapter(this, R.layout.location_row, R.id.name, volumeList)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Volume")
            .setAdapter(adapter, null)
            .create()
        dialog.listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                robot.volume = adapter.getItem(position)!!.toInt()
                printLog("Set volume to ${adapter.getItem(position)}")
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun requestToBeKioskApp() {
        if (robot.isSelectedKioskApp()) {
            printLog("${getString(R.string.app_name)} was the selected Kiosk App.")
            return
        }
        robot.requestToBeKioskApp()
    }

    private fun startDetectionWithDistance() {
        hideKeyboard()
        if (requestPermissionIfNeeded(
                Permission.SETTINGS,
                REQUEST_CODE_START_DETECTION_WITH_DISTANCE
            )
        ) {
            return
        }
        var distanceStr = etDistance.text.toString()
        if (distanceStr.isEmpty()) distanceStr = "0"
        try {
            val distance = distanceStr.toFloat()
            robot.setDetectionModeOn(true, distance)
            printLog("Start detection mode with distance: $distance")
        } catch (e: Exception) {
            printLog("startDetectionModeWithDistance", e.message ?: "")
        }
    }

    override fun onDistanceToLocationChanged(distances: Map<String, Float>) {
        for (location in distances.keys) {
            printLog(
                "onDistanceToLocation",
                "location:" + location + ", distance:" + distances[location]
            )
        }
    }

    override fun onCurrentPositionChanged(position: Position) {
        printLog("onCurrentPosition", position.toString())
    }

    override fun onSequencePlayStatusChanged(status: Int) {
        printLog(String.format("onSequencePlayStatus status:%d", status))
        if (status == OnSequencePlayStatusChangedListener.ERROR
            || status == OnSequencePlayStatusChangedListener.IDLE
        ) {
            robot.showTopBar()
        }
    }

    override fun onRobotLifted(isLifted: Boolean, reason: String) {
        printLog("onRobotLifted: isLifted: $isLifted, reason: $reason")
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        hideKeyboard()
        return super.dispatchTouchEvent(ev)
    }

    @CheckResult
    private fun requestPermissionIfNeeded(permission: Permission, requestCode: Int): Boolean {
        if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
            return false
        }
        robot.requestPermissions(listOf(permission), requestCode)
        return true
    }

    override fun onDetectionDataChanged(detectionData: DetectionData) {
        printLog("onDetectionDataChanged", detectionData.toString())
    }

    override fun onUserInteraction(isInteracting: Boolean) {
        printLog("onUserInteraction", "isInteracting:$isInteracting")
    }

    @Volatile
    private var allSequences: List<SequenceModel> = emptyList()

    private fun getAllSequences() {
        if (requestPermissionIfNeeded(Permission.SEQUENCE, REQUEST_CODE_SEQUENCE_FETCH_ALL)) {
            return
        }
        Thread {
            allSequences = robot.getAllSequences()
            val imageKeys: MutableList<String> = ArrayList()
            for ((_, _, _, imageKey) in allSequences) {
                if (imageKey.isEmpty()) continue
                imageKeys.add(imageKey)
            }
            val pairs = robot.getSignedUrlByMediaKey(imageKeys)
            runOnUiThread {
                for (sequenceModel in allSequences) {
                    printLog(sequenceModel.toString())
                }
                for (pair in pairs) {
                    printLog(pair.component2())
                }
            }
        }.start()
    }

    private fun playFirstSequence() {
        if (requestPermissionIfNeeded(Permission.SEQUENCE, REQUEST_CODE_SEQUENCE_PLAY)) {
            return
        }
        playFirstSequence(true)
    }

    private fun playFirstSequenceWithoutPlayer() {
        if (requestPermissionIfNeeded(
                Permission.SEQUENCE,
                REQUEST_CODE_SEQUENCE_PLAY_WITHOUT_PLAYER
            )
        ) {
            return
        }
        playFirstSequence(false)
    }

    private fun playFirstSequence(withPlayer: Boolean) {
        if (!allSequences.isNullOrEmpty()) {
            robot.playSequence(allSequences[0].id, withPlayer)
        }
    }

    private fun getMap() {
        if (requestPermissionIfNeeded(Permission.MAP, REQUEST_CODE_MAP)) {
            return
        }
        startActivity(Intent(this, MapActivity::class.java))
    }

    override fun onFaceRecognized(contactModelList: List<ContactModel>) {
        for (contactModel in contactModelList) {
            printLog("onFaceRecognized", contactModel.toString())
            showFaceRecognitionImage(contactModel.imageKey)
        }
    }

    private fun showFaceRecognitionImage(mediaKey: String) {
        if (mediaKey.isEmpty()) {
            imageViewFace.setImageResource(R.drawable.app_icon)
            imageViewFace.visibility = View.GONE
            return
        }
        executorService.execute {
            val inputStream =
                robot.getInputStreamByMediaKey(ContentType.FACE_RECOGNITION_IMAGE, mediaKey)
                    ?: return@execute
            runOnUiThread {
                imageViewFace.visibility = View.VISIBLE
                imageViewFace.setImageBitmap(BitmapFactory.decodeStream(inputStream))
                try {
                    inputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun printLog(msg: String) {
        printLog("", msg)
    }

    private fun printLog(tag: String, msg: String) {
        Log.d(if (tag.isEmpty()) "MainActivity" else tag, msg)
//        if (!msg.contains("GoToStatusChanged", true)) return
        tvLog.gravity = Gravity.BOTTOM
        tvLog.append("· $msg \n")
    }

    private fun clearLog() {
        tvLog.text = ""
    }

    private fun startNlu() {
        robot.startDefaultNlu(etNlu.text.toString())
    }

    override fun onSdkError(sdkException: SdkException) {
        printLog("onSdkError: $sdkException")
    }

    private fun getAllContacts() {
        val allContacts = robot.allContact
        for (userInfo in allContacts) {
            printLog("UserInfo: $userInfo")
        }
    }

    private fun goToPosition() {
        try {
            val x = etX.text.toString().toFloat()
            val y = etY.text.toString().toFloat()
            val yaw = etYaw.text.toString().toFloat()
            robot.goToPosition(Position(x, y, yaw, 0))
        } catch (e: Exception) {
            e.printStackTrace()
            printLog(e.message ?: "")
        }
    }

    override fun onConversationStatusChanged(status: Int, text: String) {
        printLog("Conversation", "status=$status, text=$text")
    }

    override fun onTtsVisualizerWaveFormDataChanged(waveForm: ByteArray) {
        visualizerView.visibility = View.VISIBLE
        visualizerView.updateVisualizer(waveForm)
    }

    override fun onTtsVisualizerFftDataChanged(fft: ByteArray) {
        Log.d("TtsVisualizer", fft.contentToString())
        //        ttsVisualizerView.updateVisualizer(fft);
    }

    private fun startTelepresenceToCenter() {
        val target = robot.adminInfo
        if (target == null) {
            printLog("target is null.")
            return
        }
        robot.startTelepresence(target.name, target.userId, Platform.TEMI_CENTER)
    }

    private fun startPage() {
        val systemPages: MutableList<String> = ArrayList()
        for (page in Page.values()) {
            systemPages.add(page.toString())
        }
        val arrayAdapter = ArrayAdapter(this, R.layout.location_row, R.id.name, systemPages)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Select System Page")
            .setAdapter(arrayAdapter, null)
            .create()
        dialog.listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
                robot.startPage(
                    Page.values()[position]
                )
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun restartTemi() {
        robot.restart()
    }

    private fun getMembersStatus() {
        val memberStatusModels = robot.membersStatus
        for (memberStatusModel in memberStatusModels) {
            printLog(memberStatusModel.toString())
        }
    }

    private fun repose() {
        robot.repose()
    }

    override fun onReposeStatusChanged(status: Int, description: String) {
        printLog("repose status: $status, description: $description")
    }

    override fun onLoadMapStatusChanged(status: Int) {
        printLog("load map status: $status")
    }

    private var mapList: List<MapModel> = ArrayList()
    private fun getMapList() {
        if (requestPermissionIfNeeded(Permission.MAP, REQUEST_CODE_GET_MAP_LIST)) {
            return
        }
        mapList = robot.getMapList()
    }

    private fun loadMap(reposeRequired: Boolean, position: Position?) {
        if (mapList.isEmpty()) {
            getMapList()
        }
        if (robot.checkSelfPermission(Permission.MAP) != Permission.GRANTED) {
            return
        }
        val mapListString: MutableList<String> = ArrayList()
        for (i in mapList.indices) {
            mapListString.add(mapList[i].name)
        }
        val mapListAdapter = ArrayAdapter(this, R.layout.location_row, R.id.name, mapListString)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Click item to load specific map")
        builder.setAdapter(mapListAdapter, null)
        val dialog = builder.create()
        dialog.listView.onItemClickListener =
            OnItemClickListener { _: AdapterView<*>?, _: View?, pos: Int, _: Long ->
                printLog("Loading map: " + mapList[pos])
                if (position == null) {
                    robot.loadMap(mapList[pos].id, reposeRequired)
                } else {
                    robot.loadMap(mapList[pos].id, reposeRequired, position)
                }
                dialog.dismiss()
            }
        dialog.show()
    }

    private fun getMapListBtn() {
        getMapList()
        for (mapModel in mapList) {
            printLog("Map: $mapModel")
        }
    }

    private fun loadMap() {
        loadMap(false, null)
    }

    override fun onDisabledFeatureListUpdated(disabledFeatureList: List<String>) {
        printLog("Disabled features: $disabledFeatureList")
    }

    private fun lock() {
        robot.locked = true
        printLog("Is temi locked: " + robot.locked)
    }

    private fun unlock() {
        robot.locked = false
        printLog("Is temi locked: " + robot.locked)
    }

    private fun muteAlexa() {
        if (robot.launcherVersion.contains("usa")) {
            printLog("Mute Alexa")
            robot.muteAlexa()
            return
        }
        printLog("muteAlexa() is useful only for Global version")
    }

    private fun shutdown() {
        if (!robot.isSelectedKioskApp()) {
            return
        }
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Shutdown temi?").create()
        builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
            printLog("shutdown")
            robot.shutdown()
        }
        builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
        builder.create().show()
    }

    override fun onMovementVelocityChanged(velocity: Float) {
        printLog("Movement velocity: " + velocity + "m/s")
    }

    private fun loadMapWithPosition() {
        loadMapWithPosition(false)
    }

    private fun loadMapWithReposePosition() {
        loadMapWithPosition(true)
    }

    private fun loadMapWithRepose() {
        loadMap(true, null)
    }

    private fun loadMapWithPosition(reposeRequired: Boolean) {
        try {
            val x = etX.text.toString().toFloat()
            val y = etY.text.toString().toFloat()
            val yaw = etYaw.text.toString().toFloat()
            loadMap(true, Position(x, y, yaw, 0))
            val position = Position(x, y, yaw, 0)
            loadMap(reposeRequired, position)
        } catch (e: Exception) {
            e.printStackTrace()
            printLog(e.message ?: "")
        }
    }

    override fun onMovementStatusChanged(type: String, status: String) {
        printLog("Movement response - $type status: $status")
    }

    companion object {
        const val ACTION_HOME_WELCOME = "home.welcome"
        const val ACTION_HOME_DANCE = "home.dance"
        const val ACTION_HOME_SLEEP = "home.sleep"
        const val HOME_BASE_LOCATION = "home base"

        // Storage Permissions
        private const val REQUEST_EXTERNAL_STORAGE = 1
        private const val REQUEST_CODE_NORMAL = 0
        private const val REQUEST_CODE_FACE_START = 1
        private const val REQUEST_CODE_FACE_STOP = 2
        private const val REQUEST_CODE_MAP = 3
        private const val REQUEST_CODE_SEQUENCE_FETCH_ALL = 4
        private const val REQUEST_CODE_SEQUENCE_PLAY = 5
        private const val REQUEST_CODE_START_DETECTION_WITH_DISTANCE = 6
        private const val REQUEST_CODE_SEQUENCE_PLAY_WITHOUT_PLAYER = 7
        private const val REQUEST_CODE_GET_MAP_LIST = 8
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        /**
         * Checks if the app has permission to write to device storage
         * If the app does not has permission then the user will be prompted to grant permissions
         */
        fun verifyStoragePermissions(activity: Activity?) {
            // Check if we have write permission
            val permission = ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // We don't have permission so prompt the user
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        }
    }
}