package com.robotemi.sdk

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.os.RemoteException
import android.util.Log
import androidx.annotation.*
import androidx.annotation.RestrictTo.Scope.LIBRARY
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import com.robotemi.sdk.activitystream.ActivityStreamObject
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage
import com.robotemi.sdk.activitystream.ActivityStreamUtils
import com.robotemi.sdk.constants.*
import com.robotemi.sdk.constants.SdkConstants.FALSE
import com.robotemi.sdk.constants.SdkConstants.NOT_SET
import com.robotemi.sdk.constants.SdkConstants.TRUE
import com.robotemi.sdk.exception.OnSdkExceptionListener
import com.robotemi.sdk.exception.SdkException
import com.robotemi.sdk.face.ContactModel
import com.robotemi.sdk.face.OnContinuousFaceRecognizedListener
import com.robotemi.sdk.face.OnFaceRecognizedListener
import com.robotemi.sdk.face.compatible
import com.robotemi.sdk.listeners.*
import com.robotemi.sdk.map.*
import com.robotemi.sdk.mediabar.AidlMediaBarController
import com.robotemi.sdk.mediabar.MediaBarData
import com.robotemi.sdk.model.CallEventModel
import com.robotemi.sdk.model.DetectionData
import com.robotemi.sdk.model.MemberStatusModel
import com.robotemi.sdk.model.RecentCallModel
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToDestinationChangedListener
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener
import com.robotemi.sdk.navigation.listener.OnReposeStatusChangedListener
import com.robotemi.sdk.navigation.model.Position
import com.robotemi.sdk.navigation.model.SafetyLevel
import com.robotemi.sdk.navigation.model.SpeedLevel
import com.robotemi.sdk.notification.AlertNotification
import com.robotemi.sdk.notification.NormalNotification
import com.robotemi.sdk.notification.NotificationCallback
import com.robotemi.sdk.permission.OnRequestPermissionResultListener
import com.robotemi.sdk.permission.Permission
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener
import com.robotemi.sdk.sequence.SequenceModel
import com.robotemi.sdk.sequence.compatible
import com.robotemi.sdk.telepresence.CallState
import com.robotemi.sdk.voice.ITtsService
import org.json.JSONException
import org.json.JSONObject
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArraySet

@SuppressWarnings("unused")
class Robot private constructor(private val context: Context) {

    private val applicationInfo: ApplicationInfo

    private val uiHandler = Handler(Looper.getMainLooper())

    private var mediaBar = AidlMediaBarController(null)

    private var mediaButtonListener: MediaButtonListener? = null

    private var sdkService: ISdkService? = null

    private val listenersMap = HashMap<String, NotificationListener>()

    private val conversationViewAttachesListeners =
        CopyOnWriteArraySet<ConversationViewAttachesListener>()

    private val ttsListeners = CopyOnWriteArraySet<TtsListener>()

    private val asrListeners = CopyOnWriteArraySet<AsrListener>()

    private val nlpListeners = CopyOnWriteArraySet<NlpListener>()

    private val wakeUpWordListeners = CopyOnWriteArraySet<WakeupWordListener>()

    private val onRobotReadyListeners = CopyOnWriteArraySet<OnRobotReadyListener>()

    private val onBeWithMeStatusChangeListeners =
        CopyOnWriteArraySet<OnBeWithMeStatusChangedListener>()

    private val onGoToLocationStatusChangeListeners =
        CopyOnWriteArraySet<OnGoToLocationStatusChangedListener>()

    private val onTelepresenceStatusChangedListeners =
        CopyOnWriteArraySet<OnTelepresenceStatusChangedListener>()

    private val onTelepresenceEventChangedListener =
        CopyOnWriteArraySet<OnTelepresenceEventChangedListener>()

    private val onLocationsUpdatedListeners = CopyOnWriteArraySet<OnLocationsUpdatedListener>()

    private val onUsersUpdatedListeners = CopyOnWriteArraySet<OnUsersUpdatedListener>()

    private val onBatteryStatusChangedListeners =
        CopyOnWriteArraySet<OnBatteryStatusChangedListener>()

    private val onPrivacyModeStateChangedListeners =
        CopyOnWriteArraySet<OnPrivacyModeChangedListener>()

    private val onConstraintBeWithStatusChangedListeners =
        CopyOnWriteArraySet<OnConstraintBeWithStatusChangedListener>()

    private val onUserInteractionChangedListeners =
        CopyOnWriteArraySet<OnUserInteractionChangedListener>()

    private val onDetectionStateChangedListeners =
        CopyOnWriteArraySet<OnDetectionStateChangedListener>()

    private val onRequestPermissionResultListeners =
        CopyOnWriteArraySet<OnRequestPermissionResultListener>()

    private val onDistanceToLocationChangedListeners =
        CopyOnWriteArraySet<OnDistanceToLocationChangedListener>()

    private val onCurrentPositionChangedListeners =
        CopyOnWriteArraySet<OnCurrentPositionChangedListener>()

    private val onSequencePlayStatusChangedListeners =
        CopyOnWriteArraySet<OnSequencePlayStatusChangedListener>()

    private val onRobotLiftedListeners = CopyOnWriteArraySet<OnRobotLiftedListener>()

    private val onDetectionDataChangedListeners =
        CopyOnWriteArraySet<OnDetectionDataChangedListener>()

    private val onFaceRecognizedListeners = CopyOnWriteArraySet<OnFaceRecognizedListener>()

    private val onContinuousFaceRecognizedListeners =
        CopyOnWriteArraySet<OnContinuousFaceRecognizedListener>()

    private val onSdkExceptionListeners = CopyOnWriteArraySet<OnSdkExceptionListener>()

    private val onConversationStatusChangedListeners =
        CopyOnWriteArraySet<OnConversationStatusChangedListener>()

    private val onTtsVisualizerWaveFormDataChangedListeners =
        CopyOnWriteArraySet<OnTtsVisualizerWaveFormDataChangedListener>()

    private val onTtsVisualizerFftDataChangedListeners =
        CopyOnWriteArraySet<OnTtsVisualizerFftDataChangedListener>()

    private val onReposeStatusChangedListeners =
        CopyOnWriteArraySet<OnReposeStatusChangedListener>()

    private val onLoadMapStatusChangedListeners =
        CopyOnWriteArraySet<OnLoadMapStatusChangedListener>()

    private val onDisabledFeatureListUpdatedListeners =
        CopyOnWriteArraySet<OnDisabledFeatureListUpdatedListener>()

    private val onMovementVelocityChangedListeners =
        CopyOnWriteArraySet<OnMovementVelocityChangedListener>()

    private val onMovementStatusChangedListeners =
        CopyOnWriteArraySet<OnMovementStatusChangedListener>()

    private val onGreetModeStateChangedListeners =
        CopyOnWriteArraySet<OnGreetModeStateChangedListener>()

    private val onLoadFloorStatusChangedListeners =
        CopyOnWriteArraySet<OnLoadFloorStatusChangedListener>()

    private val onDistanceToDestinationChangedListeners =
        CopyOnWriteArraySet<OnDistanceToDestinationChangedListener>()

    private var ttsService: ITtsService? = null

    private var activityStreamPublishListener: ActivityStreamPublishListener? = null

    init {
        val appContext = context.applicationContext
        val packageName = appContext.packageName
        val packageManager = appContext.packageManager
        try {
            this.applicationInfo =
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        } catch (e: PackageManager.NameNotFoundException) {
            throw RuntimeException(e)
        }
    }

    private val sdkServiceCallback = object : ISdkServiceCallback.Stub() {

        /*****************************************/
        /*                 Voice                 */
        /*****************************************/

        override fun onTtsStatusChanged(ttsRequest: TtsRequest): Boolean {
            if (ttsListeners.isEmpty()) return false
            uiHandler.post {
                for (ttsListener in ttsListeners) {
                    ttsListener.onTtsStatusChanged(ttsRequest)
                }
            }
            return true
        }

        override fun onWakeupWord(wakeupWord: String, direction: Int): Boolean {
            if (wakeUpWordListeners.isEmpty()) return false
            uiHandler.post {
                for (wakeupWordListener in wakeUpWordListeners) {
                    wakeupWordListener.onWakeupWord(wakeupWord, direction)
                }
            }
            return true
        }

        override fun onNlpCompleted(nlpResult: NlpResult): Boolean {
            if (nlpListeners.isEmpty()) return false
            uiHandler.post {
                for (nlpListener in nlpListeners) {
                    nlpListener.onNlpCompleted(nlpResult)
                }
            }
            return true
        }

        override fun onAsrResult(asrText: String): Boolean {
            if (asrListeners.isEmpty()) return false
            uiHandler.post {
                for (asrListener in asrListeners) {
                    asrListener.onAsrResult(asrText)
                }
            }
            return true
        }

        override fun onConversationViewAttaches(isAttached: Boolean): Boolean {
            if (conversationViewAttachesListeners.isEmpty()) return false
            uiHandler.post {
                for (conversationViewAttachesListener in conversationViewAttachesListeners) {
                    conversationViewAttachesListener.onConversationAttaches(isAttached)
                }
            }
            return true
        }

        override fun hasActiveNlpListeners(): Boolean {
            return nlpListeners.isNotEmpty()
        }

        override fun onConversationStatusChanged(status: Int, text: String): Boolean {
            if (onConversationStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (onConversationStatusChangedListener in onConversationStatusChangedListeners) {
                    onConversationStatusChangedListener.onConversationStatusChanged(status, text)
                }
            }
            return true
        }

        override fun onTtsVisualizerWaveFormDataChanged(wave: ByteArray): Boolean {
            if (onTtsVisualizerWaveFormDataChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (onTtsVisualizerWaveFormDataChangedListener in onTtsVisualizerWaveFormDataChangedListeners) {
                    onTtsVisualizerWaveFormDataChangedListener.onTtsVisualizerWaveFormDataChanged(
                        wave
                    )
                }
            }
            return true
        }

        override fun onTtsVisualizerFftDataChanged(fft: ByteArray): Boolean {
            if (onTtsVisualizerFftDataChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (onTtsVisualizerFftDataChangedListener in onTtsVisualizerFftDataChangedListeners) {
                    onTtsVisualizerFftDataChangedListener.onTtsVisualizerFftDataChanged(fft)
                }
            }
            return false
        }

        override fun onTtsSpeak(ttsRequest: TtsRequest) {
            ttsService?.speak(ttsRequest)
        }

        override fun onTtsCancel() {
            ttsService?.cancel()
        }

        override fun onTtsPause() {
            ttsService?.pause()
        }

        override fun onTtsResume() {
            ttsService?.resume()
        }

        /*****************************************/
        /*               Navigation              */
        /*****************************************/

        override fun onGoToLocationStatusChanged(
            location: String,
            status: String,
            descriptionId: Int,
            description: String
        ): Boolean {
            if (onGoToLocationStatusChangeListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onGoToLocationStatusChangeListeners) {
                    listener.onGoToLocationStatusChanged(
                        location,
                        status,
                        descriptionId,
                        description
                    )
                }
            }
            return true
        }

        override fun onLocationsUpdated(locations: List<String>): Boolean {
            if (onLocationsUpdatedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onLocationsUpdatedListeners) {
                    listener.onLocationsUpdated(locations)
                }
            }
            return true
        }

        override fun onDistanceToLocationChanged(distances: MutableMap<Any?, Any?>): Boolean {
            if (onDistanceToLocationChangedListeners.isEmpty()) return false
            val distancesMap: Map<String, Float> = distances as Map<String, Float>
            uiHandler.post {
                for (listener in onDistanceToLocationChangedListeners) {
                    listener.onDistanceToLocationChanged(distancesMap)
                }
            }
            return true
        }

        override fun onCurrentPositionChanged(position: Position): Boolean {
            if (onCurrentPositionChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onCurrentPositionChangedListeners) {
                    listener.onCurrentPositionChanged(position)
                }
            }
            return true
        }

        override fun onReposeStatusChanged(status: Int, description: String): Boolean {
            if (onReposeStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onReposeStatusChangedListeners) {
                    listener.onReposeStatusChanged(status, description)
                }
            }
            return true
        }

        override fun onDistanceToDestinationChanged(location: String, distance: Float): Boolean {
            if (onDistanceToDestinationChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onDistanceToDestinationChangedListeners) {
                    listener.onDistanceToDestinationChanged(location, distance)
                }
            }
            return true
        }

        /*****************************************/
        /*            Movement & Follow          */
        /*****************************************/

        override fun onBeWithMeStatusChanged(status: String): Boolean {
            if (onBeWithMeStatusChangeListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onBeWithMeStatusChangeListeners) {
                    listener.onBeWithMeStatusChanged(status)
                }
            }
            return true
        }

        override fun onConstraintBeWithStatusChanged(isContraint: Boolean): Boolean {
            if (onConstraintBeWithStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onConstraintBeWithStatusChangedListeners) {
                    listener.onConstraintBeWithStatusChanged(isContraint)
                }
            }
            return true
        }

        override fun onRobotLifted(isLifted: Boolean, reason: String): Boolean {
            if (onRobotLiftedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onRobotLiftedListeners) {
                    listener.onRobotLifted(isLifted, reason)
                }
            }
            return true
        }

        override fun onMovementVelocityChanged(linear: Float): Boolean {
            if (onMovementVelocityChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onMovementVelocityChangedListeners) {
                    listener.onMovementVelocityChanged(linear)
                }
            }
            return true
        }

        override fun onMovementStatusChanged(type: String, status: String): Boolean {
            if (onMovementStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onMovementStatusChangedListeners) {
                    listener.onMovementStatusChanged(type, status)
                }
            }
            return true
        }

        /*****************************************/
        /*           Users & Telepresence        */
        /*****************************************/

        override fun onTelepresenceStatusChanged(callState: CallState): Boolean {
            if (onTelepresenceStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onTelepresenceStatusChangedListeners) {
                    if (listener != null && callState.sessionId == listener.sessionId) {
                        listener.onTelepresenceStatusChanged(callState)
                    }
                }
            }
            return true
        }

        override fun onUserUpdated(user: UserInfo): Boolean {
            if (onUsersUpdatedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onUsersUpdatedListeners) {
                    val isValidListener = listener != null && (listener.userIds == null
                            || listener.userIds!!.isEmpty()
                            || listener.userIds!!.contains(user.userId))
                    if (isValidListener) {
                        listener!!.onUserUpdated(user)
                    }
                }
            }
            return true
        }

        override fun onTelepresenceEventChanged(callEventModel: CallEventModel): Boolean {
            if (onTelepresenceEventChangedListener.isEmpty()) return false
            uiHandler.post {
                for (listener in onTelepresenceEventChangedListener) {
                    listener.onTelepresenceEventChanged(callEventModel)
                }
            }
            return true
        }

        /*****************************************/
        /*                 System                */
        /*****************************************/

        override fun onNotificationBtnClicked(notificationCallback: NotificationCallback) {
            uiHandler.post {
                val notificationListener = listenersMap[notificationCallback.notificationId]
                if (notificationListener != null) {
                    notificationListener.onNotificationBtnClicked(notificationCallback.event)
                    listenersMap.remove(notificationCallback.notificationId)
                }
            }
        }

        override fun onPrivacyModeStateChanged(state: Boolean): Boolean {
            if (onPrivacyModeStateChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onPrivacyModeStateChangedListeners) {
                    listener.onPrivacyModeChanged(state)
                }
            }
            return true
        }

        override fun onBatteryStatusChanged(batteryData: BatteryData): Boolean {
            if (onBatteryStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onBatteryStatusChangedListeners) {
                    listener.onBatteryStatusChanged(batteryData)
                }
            }
            return true
        }

        override fun onDisabledFeatureListUpdated(disabledFeatureList: MutableList<String>): Boolean {
            if (onDisabledFeatureListUpdatedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onDisabledFeatureListUpdatedListeners) {
                    listener.onDisabledFeatureListUpdated(disabledFeatureList)
                }
            }
            return true
        }

        override fun onGreetModeStateChanged(state: Int): Boolean {
            if (onGreetModeStateChangedListeners.isEmpty()) return false
            uiHandler.post {
                onGreetModeStateChangedListeners.forEach {
                    it.onGreetModeStateChanged(state)
                }
            }
            return true
        }

        /*****************************************/
        /*               Permission              */
        /*****************************************/

        override fun onRequestPermissionResult(
            permission: String,
            grantResult: Int,
            requestCode: Int
        ): Boolean {
            if (onRequestPermissionResultListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onRequestPermissionResultListeners) {
                    listener.onRequestPermissionResult(
                        Permission.valueToEnum(permission),
                        grantResult,
                        requestCode
                    )
                }
            }
            return true
        }

        /*****************************************/
        /*            Activity Stream            */
        /*****************************************/

        override fun onActivityStreamPublish(message: ActivityStreamPublishMessage) {
            if (activityStreamPublishListener != null) {
                activityStreamPublishListener!!.onPublish(message)
            }
        }

        /*****************************************/
        /*                 Media                 */
        /*****************************************/

        override fun onPlayButtonClicked(play: Boolean) {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onPlayButtonClicked(play)
                }
            }
        }

        override fun onNextButtonClicked() {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onNextButtonClicked()
                }
            }
        }

        override fun onBackButtonClicked() {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onBackButtonClicked()
                }
            }
        }

        override fun onTrackBarChanged(position: Int) {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onTrackBarChanged(position)
                }
            }
        }

        /*****************************************/
        /*             Detection Mode            */
        /*****************************************/

        override fun onUserInteractionStatusChanged(isInteracting: Boolean): Boolean {
            if (onUserInteractionChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onUserInteractionChangedListeners) {
                    listener.onUserInteraction(isInteracting)
                }
            }
            return true
        }

        override fun onDetectionStateChanged(state: Int): Boolean {
            if (onDetectionStateChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onDetectionStateChangedListeners) {
                    listener.onDetectionStateChanged(state)
                }
            }
            return true
        }

        override fun onDetectionDataChanged(detectionData: DetectionData): Boolean {
            if (onDetectionDataChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onDetectionDataChangedListeners) {
                    listener.onDetectionDataChanged(detectionData)
                }
            }
            return true
        }

        /*****************************************/
        /*                Sequence               */
        /*****************************************/

        override fun onSequencePlayStatusChanged(status: Int): Boolean {
            if (onSequencePlayStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onSequencePlayStatusChangedListeners) {
                    listener.onSequencePlayStatusChanged(status)
                }
            }
            return true
        }

        /*****************************************/
        /*            Face Recognition           */
        /*****************************************/

        override fun onFaceRecognized(contactModelList: MutableList<ContactModel>): Boolean {
            if (onFaceRecognizedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onFaceRecognizedListeners) {
                    listener.onFaceRecognized(contactModelList.map { it.compatible() })
                }
            }
            return true
        }

        override fun onContinuousFaceRecognized(contactModelList: MutableList<ContactModel>): Boolean {
            if (onContinuousFaceRecognizedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onContinuousFaceRecognizedListeners) {
                    listener.onContinuousFaceRecognized(contactModelList.map { it.compatible() })
                }
            }
            return true
        }

        /*****************************************/
        /*                 Common                */
        /*****************************************/

        override fun onSdkError(sdkException: SdkException): Boolean {
            if (onSdkExceptionListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onSdkExceptionListeners) {
                    listener.onSdkError(sdkException)
                }
            }
            return true
        }

        /*****************************************/
        /*                  Map                  */
        /*****************************************/

        override fun onLoadMapStatusChanged(status: Int): Boolean {
            if (onLoadMapStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                for (listener in onLoadMapStatusChangedListeners) {
                    listener.onLoadMapStatusChanged(status)
                }
            }
            return true
        }

        override fun onLoadFloorStatusChanged(status: Int): Boolean {
            if (onLoadFloorStatusChangedListeners.isEmpty()) return false
            uiHandler.post {
                onLoadFloorStatusChangedListeners.forEach {
                    it.onLoadFloorStatusChanged(status)
                }
            }
            return true
        }

    }

    /*****************************************/
    /*                  Init                 */
    /*****************************************/

    @get:CheckResult
    val isReady
        get() = sdkService != null

    @UiThread
    fun onStart(activityInfo: ActivityInfo) {
        try {
            sdkService?.onStart(activityInfo)
        } catch (e: RemoteException) {
            Log.e(TAG, "onStart(ActivityInfo) - Binder invocation exception.")
        }
    }

    @RestrictTo(LIBRARY)
    @UiThread
    internal fun setSdkService(sdkService: ISdkService?) {
        this.sdkService = sdkService
        mediaBar = AidlMediaBarController(sdkService)
        registerCallback()
        onRobotReadyListeners.forEach { it.onRobotReady(sdkService != null) }
    }

    @UiThread
    private fun registerCallback() {
        try {
            sdkService?.register(applicationInfo, sdkServiceCallback)
        } catch (e: RemoteException) {
            Log.e(TAG, "Remote invocation error")
        }
    }

    @UiThread
    fun addOnRobotReadyListener(onRobotReadyListener: OnRobotReadyListener) {
        onRobotReadyListeners.add(onRobotReadyListener)
        onRobotReadyListener.onRobotReady(isReady)
    }

    @UiThread
    fun removeOnRobotReadyListener(onRobotReadyListener: OnRobotReadyListener) {
        onRobotReadyListeners.remove(onRobotReadyListener)
    }

    /*****************************************/
    /*                 Voice                 */
    /*****************************************/

    /**
     * To ask temi to speak something.
     *
     * @param ttsRequest Which contains all the TTS information temi needs to in order to speak.
     */
    fun speak(ttsRequest: TtsRequest) {
        try {
            sdkService?.speak(ttsRequest.apply { packageName = applicationInfo.packageName })
        } catch (e: RemoteException) {
            Log.e(TAG, "Failed to invoke remote call speak()")
        }
    }

    private val isOverridingTts = applicationInfo.metaData != null
            && applicationInfo.metaData.getBoolean(SdkConstants.METADATA_OVERRIDE_TTS, false)

    fun setTtsService(ttsService: ITtsService?) {
        if (!isOverridingTts) {
            sdkServiceCallback.onSdkError(SdkException.illegalArgument("'com.robotemi.sdk.metadata.OVERRIDE_TTS' should be declared in AndroidManifest.xml"))
            return
        }
        this.ttsService = ttsService
    }

    fun publishTtsStatus(ttsRequest: TtsRequest) {
        if (!isOverridingTts) {
            sdkServiceCallback.onSdkError(SdkException.illegalArgument("'com.robotemi.sdk.metadata.OVERRIDE_TTS' should be declared in AndroidManifest.xml"))
            return
        }
        try {
            sdkService?.publishTtsStatus(applicationInfo.packageName, ttsRequest)
        } catch (e: RemoteException) {
            Log.e(TAG, "publishTtsStatus() error")
        }
    }

    /**
     * The wakeup word of the temi's assistant.
     */
    @get:CheckResult
    val wakeupWord: String
        get() {
            try {
                return sdkService?.wakeupWord ?: ""
            } catch (e: RemoteException) {
                Log.e(TAG, "getWakeupWord() error")
            }
            return ""
        }

    /**
     * Trigger temi's wakeup programmatically.
     */
    fun wakeup() {
        try {
            sdkService?.wakeup()
        } catch (e: RemoteException) {
            Log.e(TAG, "wakeup() error")
        }
    }

    /**
     * Stops currently processed TTS request and empty the queue.
     */
    fun cancelAllTtsRequests() {
        try {
            sdkService?.cancelAll()
        } catch (e: RemoteException) {
            Log.e(TAG, "Failed to invoke remote call cancelAllTtsRequest()")
        }
    }

    /**
     * Request to lock contexts even if skill screen is dismissed.
     * Useful for services running in the background without UI.
     *
     * @param contextsToLock - List of contexts names to lock.
     */
    @Deprecated("No longer supported")
    fun lockContexts(contextsToLock: List<String>) {
        try {
            sdkService?.lockContexts(contextsToLock)
        } catch (e: RemoteException) {
            Log.e(TAG, "lockContexts(List<String>) error")
        }
    }

    /**
     * Release previously locked contexts. See [.lockContexts].
     *
     * @param contextsToRelease - List of contexts names to release.
     */
    @Deprecated("No longer supported")
    fun releaseContexts(contextsToRelease: List<String>) {
        try {
            sdkService?.releaseContexts(contextsToRelease)
        } catch (e: RemoteException) {
            Log.e(TAG, "releaseContexts(List<String>) error")
        }
    }

    /**
     * Start a conversation.
     *
     * @param question - First question from robot.
     */
    fun askQuestion(question: String) {
        try {
            sdkService?.askQuestion(question)
        } catch (e: RemoteException) {
            Log.e(TAG, "Ask question call failed")
        }
    }

    /**
     * Finish conversation.
     */
    fun finishConversation() {
        try {
            sdkService?.finishConversation()
        } catch (e: RemoteException) {
            Log.e(TAG, "Finish conversation call failed")
        }
    }

    /**
     * Trigger temi Launcher's default NLU service.
     *
     * @param text The text to be processed.
     */
    fun startDefaultNlu(text: String) {
        try {
            sdkService?.startDefaultNlu(applicationInfo.packageName, text)
        } catch (e: RemoteException) {
            Log.e(TAG, "startDefaultNlu() error")
        }
    }

    @Deprecated(message = "Use [addOnConversationStatusChangedListener] instead")
    @UiThread
    fun addConversationViewAttachesListenerListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        conversationViewAttachesListeners.add(conversationViewAttachesListener)
    }

    @Deprecated(message = "Use [removeConversationViewAttachesListener] instead")
    @UiThread
    fun removeConversationViewAttachesListenerListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        conversationViewAttachesListeners.remove(conversationViewAttachesListener)
    }

    @UiThread
    fun addConversationViewAttachesListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        conversationViewAttachesListeners.add(conversationViewAttachesListener)
    }

    @UiThread
    fun removeConversationViewAttachesListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        conversationViewAttachesListeners.remove(conversationViewAttachesListener)
    }

    @UiThread
    fun addNlpListener(nlpListener: NlpListener) {
        nlpListeners.add(nlpListener)
    }

    @UiThread
    fun removeNlpListener(nlpListener: NlpListener) {
        nlpListeners.remove(nlpListener)
    }

    @UiThread
    fun addTtsListener(ttsListener: TtsListener) {
        ttsListeners.add(ttsListener)
    }

    @UiThread
    fun removeTtsListener(ttsListener: TtsListener) {
        ttsListeners.remove(ttsListener)
    }

    @UiThread
    fun addWakeupWordListener(wakeupWordListener: WakeupWordListener) {
        wakeUpWordListeners.add(wakeupWordListener)
    }

    @UiThread
    fun removeWakeupWordListener(wakeupWordListener: WakeupWordListener) {
        wakeUpWordListeners.remove(wakeupWordListener)
    }

    @UiThread
    fun removeAsrListener(asrListener: AsrListener) {
        asrListeners.remove(asrListener)
    }

    @UiThread
    fun addAsrListener(asrListener: AsrListener) {
        asrListeners.add(asrListener)
    }

    @UiThread
    fun addOnConversationStatusChangedListener(onConversationStatusChangedListener: OnConversationStatusChangedListener) {
        onConversationStatusChangedListeners.add(onConversationStatusChangedListener)
    }

    @UiThread
    fun removeOnConversationStatusChangedListener(onConversationStatusChangedListener: OnConversationStatusChangedListener) {
        onConversationStatusChangedListeners.remove(onConversationStatusChangedListener)
    }

    @UiThread
    fun addOnTtsVisualizerWaveFormDataChangedListener(onTtsVisualizerWaveFormDataChangedListener: OnTtsVisualizerWaveFormDataChangedListener) {
        onTtsVisualizerWaveFormDataChangedListeners.add(onTtsVisualizerWaveFormDataChangedListener)
    }

    @UiThread
    fun removeOnTtsVisualizerWaveFormDataChangedListener(onTtsVisualizerWaveFormDataChangedListener: OnTtsVisualizerWaveFormDataChangedListener) {
        onTtsVisualizerWaveFormDataChangedListeners.remove(
            onTtsVisualizerWaveFormDataChangedListener
        )
    }

    @UiThread
    fun addOnTtsVisualizerFftDataChangedListener(onTtsVisualizerFftDataChangedListener: OnTtsVisualizerFftDataChangedListener) {
        onTtsVisualizerFftDataChangedListeners.add(onTtsVisualizerFftDataChangedListener)
    }

    @UiThread
    fun removeOnTtsVisualizerFftDataChangedListener(onTtsVisualizerFftDataChangedListener: OnTtsVisualizerFftDataChangedListener) {
        onTtsVisualizerFftDataChangedListeners.remove(onTtsVisualizerFftDataChangedListener)
    }

    /*****************************************/
    /*               Navigation              */
    /*****************************************/

    /**
     * Save location.
     *
     * @param name Location name.
     * @return Result of a successful or failed operation.
     */
    fun saveLocation(name: String): Boolean {
        try {
            return sdkService?.saveLocation(name) ?: false
        } catch (e: RemoteException) {
            Log.e(TAG, "saveLocation(String) error")
        }
        return true
    }

    /**
     * Delete location.
     *
     * @param name Location name.
     * @return Result of a successful or failed operation.
     */
    fun deleteLocation(name: String): Boolean {
        if (name == SdkConstants.LOCATION_HOME_BASE) {
            sdkServiceCallback.onSdkError(SdkException.illegalArgument("Can not delete $name"))
            return false
        }
        try {
            return sdkService?.deleteLocation(name) ?: false
        } catch (e: RemoteException) {
            Log.e(TAG, "deleteLocation(String) error")
        }
        return false
    }

    /**
     * Retrieve list of previously saved locations.
     *
     * @return List of saved locations.
     */
    @get:CheckResult
    val locations: List<String>
        get() {
            try {
                return sdkService?.locations ?: emptyList()
            } catch (e: RemoteException) {
                Log.e(TAG, "getLocations() error")
            }
            return emptyList()
        }

    /**
     * Send robot to previously saved location.
     *
     * @param location Saved location name.
     * @param backwards if `true` will walk backwards to the destination. `false` by default.
     * @param noBypass if `true` will disallow bypass the obstacles during go to.
     *   Pass `null` to follow the  *Settings -> Navigation Settings*
     * @param speedLevel the speed level of this single go to session.
     *   Pass `null` to start with the speed level in *Settings -> Navigation Settings* (see [speedLevel]).
     */
    @JvmOverloads
    fun goTo(
        location: String,
        backwards: Boolean? = null,
        noBypass: Boolean? = null,
        speedLevel: SpeedLevel? = null
    ) {
        require(location.isNotBlank()) { "Location can not be null or empty." }
        try {
            val allowBackwardsInt =
                if (backwards == null) NOT_SET else if (backwards) TRUE else FALSE
            val noBypassInt = if (noBypass == null) NOT_SET else if (noBypass) TRUE else FALSE
            sdkService?.goTo(location, allowBackwardsInt, noBypassInt, speedLevel?.value ?: "")
        } catch (e: RemoteException) {
            Log.e(TAG, "goTo(String) error")
        }
    }

    /**
     * Go to a specific position with (x,y).
     *
     * @param position Position holds (x,y).
     * @param backwards if `true` will walk backwards to the destination. `false` by default.
     * @param noBypass if `true` will disallow bypass the obstacles during go to.
     *   Pass `null` to follow the  *Settings -> Navigation Settings*
     * @param speedLevel the speed level of this single go to session.
     *   Pass `null` to start with the speed level in *Settings -> Navigation Settings* (see [speedLevel]).
     */
    @JvmOverloads
    fun goToPosition(
        position: Position,
        backwards: Boolean? = null,
        noBypass: Boolean? = null,
        speedLevel: SpeedLevel? = null
    ) {
        try {
            val allowBackwardsInt =
                if (backwards == null) NOT_SET else if (backwards) TRUE else FALSE
            val noBypassInt = if (noBypass == null) NOT_SET else if (noBypass) TRUE else FALSE
            sdkService?.goToPosition(
                position,
                allowBackwardsInt,
                noBypassInt,
                speedLevel?.value ?: ""
            )
        } catch (e: RemoteException) {
            Log.e(TAG, "goToPosition() error")
        }
    }

    /**
     *  Set navigation safety level.
     */
    @get:CheckResult
    var navigationSafety: SafetyLevel
        get() {
            try {
                return SafetyLevel.valueToEnum(
                    sdkService?.navigationSafety ?: SafetyLevel.DEFAULT.value
                )
            } catch (e: RemoteException) {
                Log.e(TAG, "getNavigationSafety() error")
            }
            return SafetyLevel.DEFAULT
        }
        set(safetyLevel) {
            try {
                sdkService?.setNavigationSafety(applicationInfo.packageName, safetyLevel.value)
            } catch (e: RemoteException) {
                Log.e(TAG, "setNavigationSafety() error")
            }
        }

    /**
     * Set navigation speed level.
     */
    @get:CheckResult
    var goToSpeed: SpeedLevel
        get() {
            try {
                return SpeedLevel.valueToEnum(
                    sdkService?.goToSpeed ?: SpeedLevel.DEFAULT.value
                )
            } catch (e: RemoteException) {
                Log.e(TAG, "getGoToSpeed() error")
            }
            return SpeedLevel.DEFAULT
        }
        set(speedLevel) {
            try {
                sdkService?.setGoToSpeed(applicationInfo.packageName, speedLevel.value)
            } catch (e: RemoteException) {
                Log.e(TAG, "setGoToSpeed() error")
            }
        }

    /**
     * Start positing to locate the position of temi.
     *
     */
    fun repose() {
        try {
            sdkService?.repose()
        } catch (e: RemoteException) {
            Log.e(TAG, "repose() error")
        }
    }

    @UiThread
    fun addOnGoToLocationStatusChangedListener(listener: OnGoToLocationStatusChangedListener) {
        onGoToLocationStatusChangeListeners.add(listener)
    }

    @UiThread
    fun removeOnGoToLocationStatusChangedListener(listener: OnGoToLocationStatusChangedListener) {
        onGoToLocationStatusChangeListeners.remove(listener)
    }

    @UiThread
    fun addOnLocationsUpdatedListener(listener: OnLocationsUpdatedListener) {
        onLocationsUpdatedListeners.add(listener)
    }

    @UiThread
    fun removeOnLocationsUpdateListener(listener: OnLocationsUpdatedListener) {
        onLocationsUpdatedListeners.remove(listener)
    }

    @UiThread
    fun addOnDistanceToLocationChangedListener(listener: OnDistanceToLocationChangedListener) {
        onDistanceToLocationChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnDistanceToLocationChangedListener(listener: OnDistanceToLocationChangedListener) {
        onDistanceToLocationChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnCurrentPositionChangedListener(listener: OnCurrentPositionChangedListener) {
        onCurrentPositionChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnCurrentPositionChangedListener(listener: OnCurrentPositionChangedListener) {
        onCurrentPositionChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnReposeStatusChangedListener(listener: OnReposeStatusChangedListener) {
        onReposeStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnReposeStatusChangedListener(listener: OnReposeStatusChangedListener) {
        onReposeStatusChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnDistanceToDestinationChangedListener(listener: OnDistanceToDestinationChangedListener) {
        onDistanceToDestinationChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnDistanceToDestinationChangedListener(listener: OnDistanceToDestinationChangedListener) {
        onDistanceToDestinationChangedListeners.remove(listener)
    }

    /*****************************************/
    /*            Movement & Follow          */
    /*****************************************/

    /**
     * Request robot to follow user around.
     * Add [OnBeWithMeStatusChangedListener] to listen for status changes.
     */
    fun beWithMe() {
        try {
            sdkService?.beWithMe()
        } catch (e: RemoteException) {
            Log.e(TAG, "beWithMe() error")
        }
    }

    /**
     * Start constraint follow.
     * Add [OnConstraintBeWithStatusChangedListener] to listen for status changed.
     */
    fun constraintBeWith() {
        try {
            sdkService?.constraintBeWith()
        } catch (e: RemoteException) {
            Log.e(TAG, "constraintBeWith() error")
        }
    }

    /**
     * Request robot to stop any movement.
     */
    fun stopMovement() {
        try {
            sdkService?.stopMovement()
        } catch (e: RemoteException) {
            Log.e(TAG, "stopMovement() error")
        }
    }

    /**
     * Joystick commands.
     *
     * @param x Move on the x axis from -1 to 1.
     * @param y Move on the y axis from -1 to 1.
     * @param smart Moving with bypassing the obstacles
     */
    @JvmOverloads
    fun skidJoy(x: Float, y: Float, smart: Boolean = false) {
        try {
            sdkService?.skidJoy(x, y, smart)
        } catch (e: RemoteException) {
            Log.e(TAG, "skidJoy(float, float, smart) (x=$x, y=$y, smart=$smart) error")
        }
    }

    /**
     * To turn temi by a specific degree.
     *
     * @param degrees the degree amount you want the robot to turn
     * @param speed Coefficient of maximum speed, between 0 to 1
     */
    @JvmOverloads
    fun turnBy(degrees: Int, speed: Float = 1f) {
        try {
            sdkService?.turnBy(degrees, speed)
        } catch (e: RemoteException) {
            Log.e(TAG, "turnBy(int) (degrees=$degrees) error")
        }
    }

    /**
     * To tilt temi's head to a specific angle.
     *
     * @param degrees the degree which you want the robot to tilt to, between 55 and -25
     * @param speed Coefficient of maximum speed, between 0 to 1
     */
    @JvmOverloads
    fun tiltAngle(degrees: Int, speed: Float = 1f) {
        try {
            sdkService?.tiltAngle(degrees, speed)
        } catch (e: RemoteException) {
            Log.e(TAG, "turnBy(int, float) (degrees=$degrees, speed=$speed) error")
        }
    }

    /**
     * To tilt temi's head to by a specific degree.
     *
     * @param degrees The degree amount you want the robot to tilt
     * @param speed Coefficient of maximum speed, between 0 to 1
     */
    @JvmOverloads
    fun tiltBy(degrees: Int, speed: Float = 1f) {
        try {
            sdkService?.tiltBy(degrees, speed)
        } catch (e: RemoteException) {
            Log.e(TAG, "tiltBy(int, float) (degrees=$degrees, speed=$speed) error")
        }
    }

    @UiThread
    fun addOnBeWithMeStatusChangedListener(listener: OnBeWithMeStatusChangedListener) {
        onBeWithMeStatusChangeListeners.add(listener)
    }

    @UiThread
    fun removeOnBeWithMeStatusChangedListener(listener: OnBeWithMeStatusChangedListener) {
        onBeWithMeStatusChangeListeners.remove(listener)
    }

    @UiThread
    fun addOnConstraintBeWithStatusChangedListener(listener: OnConstraintBeWithStatusChangedListener) {
        onConstraintBeWithStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnConstraintBeWithStatusChangedListener(listener: OnConstraintBeWithStatusChangedListener) {
        onConstraintBeWithStatusChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnRobotLiftedListener(listener: OnRobotLiftedListener) {
        onRobotLiftedListeners.add(listener)
    }

    @UiThread
    fun removeOnRobotLiftedListener(listener: OnRobotLiftedListener) {
        onRobotLiftedListeners.remove(listener)
    }

    @UiThread
    fun addOnMovementVelocityChangedListener(listener: OnMovementVelocityChangedListener) {
        onMovementVelocityChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnMovementVelocityChangedListener(listener: OnMovementVelocityChangedListener) {
        onMovementVelocityChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnMovementStatusChangedListener(listener: OnMovementStatusChangedListener) {
        onMovementStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnMovementStatusChangedListener(listener: OnMovementStatusChangedListener) {
        onMovementStatusChangedListeners.remove(listener)
    }

    /*****************************************/
    /*           Users & Telepresence        */
    /*****************************************/

    /**
     * Get the information of temi's admin.
     */
    @get:CheckResult
    val adminInfo: UserInfo?
        get() {
            try {
                return sdkService?.adminInfo
            } catch (e: RemoteException) {
                Log.e(TAG, "getAdminInfo() error")
            }
            return null
        }

    /**
     * Fetch all the temi contacts.
     */
    @get:CheckResult
    val allContact: List<UserInfo>
        get() {
            val contactList = ArrayList<UserInfo>()
            try {
                contactList.addAll(sdkService?.allContacts ?: emptyList())
            } catch (e: RemoteException) {
                Log.e(TAG, "getAllContacts() error")
            }
            return contactList
        }

    /**
     * Fetch recent calls.
     */
    @get:CheckResult
    val recentCalls: List<RecentCallModel>
        get() {
            try {
                return sdkService?.recentCalls ?: emptyList()
            } catch (e: RemoteException) {
                Log.e(TAG, "getRecentCalls() error")
            }
            return emptyList()
        }

    /**
     * Start a video call to the temi user.
     *
     * @param displayName Name of temi user.
     * @param peerId ID of temi user ID.
     * @param platform Platform of the target user.
     * @return
     */
    @JvmOverloads
    fun startTelepresence(
        displayName: String,
        peerId: String,
        platform: Platform = Platform.MOBILE
    ): String {
        try {
            return sdkService?.startTelepresence(displayName, peerId, platform.value) ?: ""
        } catch (e: RemoteException) {
            Log.e(TAG, "startTelepresence() error")
        }
        return ""
    }

    @get:CheckResult
    val membersStatus: List<MemberStatusModel>
        get() {
            try {
                return sdkService?.membersStatus ?: emptyList()
            } catch (e: RemoteException) {
                Log.e(TAG, "getMembersStatus() error")
            }
            return emptyList()
        }

    /**
     * Start listening for Telepresence Status changes.
     *
     * @param listener The listener you want to add.
     */
    @UiThread
    fun addOnTelepresenceStatusChangedListener(listener: OnTelepresenceStatusChangedListener) {
        onTelepresenceStatusChangedListeners.add(listener)
    }

    /**
     * Stop listening for Telepresence Status changes.
     *
     * @param listener The listener you added before.
     */
    @UiThread
    fun removeOnTelepresenceStatusChangedListener(listener: OnTelepresenceStatusChangedListener) {
        onTelepresenceStatusChangedListeners.remove(listener)
    }

    /**
     * Start listening for user information updates.
     *
     * @param listener The listener you want to add.
     */
    @UiThread
    fun addOnUsersUpdatedListener(listener: OnUsersUpdatedListener) {
        onUsersUpdatedListeners.add(listener)
    }

    /**
     * Stop listening for user information updates.
     *
     * @param listener The listener you added before.
     */
    @UiThread
    fun removeOnUsersUpdatedListener(listener: OnUsersUpdatedListener) {
        onUsersUpdatedListeners.remove(listener)
    }

    @UiThread
    fun addOnTelepresenceEventChangedListener(listener: OnTelepresenceEventChangedListener) {
        onTelepresenceEventChangedListener.add(listener)
    }

    @UiThread
    fun removeOnTelepresenceEventChangedListener(listener: OnTelepresenceEventChangedListener) {
        onTelepresenceEventChangedListener.remove(listener)
    }

    /*****************************************/
    /*                 System                */
    /*****************************************/

    /**
     * Request robot's serial number as a String.
     *
     * @return The serial number of the robot.
     */
    @get:CheckResult
    val serialNumber: String?
        get() {
            try {
                return sdkService?.serialNumber
            } catch (e: RemoteException) {
                Log.e(TAG, "getSerialNumber() error")
            }
            return null
        }

    /**
     * Request the robot to provide current battery status.
     *
     * @return The battery data the robot.
     */
    @get:CheckResult
    val batteryData: BatteryData?
        get() {
            try {
                return sdkService?.batteryData
            } catch (e: RemoteException) {
                Log.e(TAG, "getBatteryData() error")
            }
            return null
        }

    /**
     * Go to the App list of Launcher.
     */
    fun showAppList() {
        try {
            sdkService?.showAppList()
        } catch (e: RemoteException) {
            Log.e(TAG, "showAppList() error")
        }
    }

    /**
     * Show the top bar of Launcher.
     */
    fun showTopBar() {
        try {
            sdkService?.showTopBar()
        } catch (e: RemoteException) {
            Log.e(TAG, "showTopBar() error")
        }
    }

    /**
     * Hide the top bar of Launcher.
     */
    fun hideTopBar() {
        try {
            sdkService?.hideTopBar()
        } catch (e: RemoteException) {
            Log.e(TAG, "hideTopBar() error")
        }
    }

    /**
     * Toggle privacy mode on temi.
     */
    @get:CheckResult
    var privacyMode: Boolean
        get() {
            try {
                return sdkService?.privacyModeState ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "getPrivacyModeState() error")
            }
            return false
        }
        set(on) {
            try {
                sdkService?.togglePrivacyMode(on, applicationInfo.packageName)
            } catch (e: RemoteException) {
                Log.e(TAG, "togglePrivacyMode() error")
            }
        }

    /**
     * Get(Set) HardButtons enabled(disabled).
     */
    @get:CheckResult
    var isHardButtonsDisabled: Boolean
        get() {
            try {
                return sdkService?.isHardButtonsDisabled ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "setHardButtonsDisabled() error")
            }
            return false
        }
        set(disable) {
            try {
                sdkService?.toggleHardButtons(disable, applicationInfo.packageName)
            } catch (e: RemoteException) {
                Log.e(TAG, "isHardButtonsEnabled() error")
            }
        }

    fun setHardButtonMode(type: HardButton, mode: HardButton.Mode) {
        try {
            sdkService?.setHardButtonMode(applicationInfo.packageName, type.value, mode.value)
        } catch (e: RemoteException) {
            Log.e(TAG, "setHardButtonMode() error")
        }
    }

    /**
     * Get current mode of the specific hard button.
     *
     * @param type See [HardButton.MAIN], [HardButton.POWER], [HardButton.VOLUME]
     * @return
     */
    @CheckResult
    fun getHardButtonMode(type: HardButton): HardButton.Mode {
        return try {
            HardButton.Mode.valueToEnum(sdkService?.getHardButtonMode(type.value))
        } catch (e: RemoteException) {
            Log.e(TAG, "getHardButtonMode() error")
            HardButton.Mode.DEFAULT
        }
    }

    /**
     * Get version of the Launcher.
     */
    @get:CheckResult
    val launcherVersion: String
        get() {
            try {
                return sdkService?.launcherVersion ?: ""
            } catch (e: RemoteException) {
                Log.e(TAG, "getLauncherVersion() error")
            }
            return ""
        }

    /**
     * Get version of the Robox.
     */
    @get:CheckResult
    val roboxVersion: String
        get() {
            try {
                return sdkService?.roboxVersion ?: ""
            } catch (e: RemoteException) {
                Log.e(TAG, "getRoboxVersion() error")
            }
            return ""
        }

    /**
     * Show or hide the green badge(Movement indicator such as navigation, follow...) at the top of the screen.
     */
    @get:JvmName("isTopBadgeEnabled")
    @get:CheckResult
    var topBadgeEnabled: Boolean
        get() {
            try {
                return sdkService?.isTopBadgeEnabled ?: true
            } catch (e: RemoteException) {
                Log.e(TAG, "isTopBadgeEnabled() error")
            }
            return true
        }
        /**
         * @param enabled true to enable, false to disable the green badge.
         */
        set(enabled) {
            if (!isMetaDataKiosk) return
            try {
                sdkService?.setTopBadgeEnabled(applicationInfo.packageName, enabled)
            } catch (e: RemoteException) {
                Log.e(TAG, "setTopBadgeEnabled() error")
            }
        }

    /**
     * Turn on/off detection mode.
     */
    @get:JvmName("isDetectionModeOn")
    @get:CheckResult
    var detectionModeOn: Boolean
        get() {
            try {
                return sdkService?.isDetectionModeOn ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isDetectionModeOn() error")
            }
            return false
        }
        /**
         * @param on true to turn on, false to turn off.
         */
        set(on) {
            setDetectionModeOn(on, SdkConstants.DETECTION_DISTANCE_DEFAULT)
        }

    /**
     * Turn on/off detection mode with distance.
     *
     * @param on true to turn on, false to turn off.
     * @param distance  Maximum detection distance(0.5~2.0), person will be detected when the distance
     *                  to temi less than this value only.
     */
    fun setDetectionModeOn(on: Boolean, distance: Float) {
        try {
            sdkService?.setDetectionModeOn(applicationInfo.packageName, on, distance)
        } catch (e: RemoteException) {
            Log.e(TAG, "setDetectionModeOn() error")
        }
    }

    /**
     * Turn on/off track user(welcome mode).
     */
    @get:JvmName("isTrackUserOn")
    @get:CheckResult
    var trackUserOn: Boolean
        get() {
            try {
                return sdkService?.isTrackUserOn ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isTrackUser() error")
            }
            return false
        }
        /**
         * @param on true to turn on, false to turn off.
         */
        set(on) {
            try {
                sdkService?.setTrackUserOn(applicationInfo.packageName, on)
            } catch (e: RemoteException) {
                Log.e(TAG, "setTrackUserOn() error")
            }
        }

    /**
     * Turn on/off auto return.
     */
    @get:JvmName("isAutoReturnOn")
    var autoReturnOn: Boolean
        @CheckResult
        get() {
            try {
                return sdkService?.isAutoReturnOn ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isAutoReturnOn() error")
            }
            return false
        }
        /**
         * @param on true to turn on, false to turn off.
         */
        set(on) {
            try {
                sdkService?.setAutoReturnOn(applicationInfo.packageName, on)
            } catch (e: RemoteException) {
                Log.e(TAG, "setAutoReturnOn() error")
            }
        }

    /**
     * Volume of Launcher OS.
     */
    @get:CheckResult
    var volume: Int
        get() {
            try {
                return sdkService?.volume ?: 0
            } catch (e: RemoteException) {
                Log.e(TAG, "getVolume() error")
            }
            return 0
        }
        /**
         * @param volume the volume you want to set to Launcher.
         */
        set(volume) {
            try {
                val validVolume = when {
                    volume < 0 -> 0
                    volume > 10 -> 10
                    else -> volume
                }
                sdkService?.setVolume(applicationInfo.packageName, validVolume)
            } catch (e: RemoteException) {
                Log.e(TAG, "setVolume() error")
            }
        }

    /**
     * Restart temi.
     *
     */
    fun restart() {
        if (!isMetaDataKiosk) {
            Log.w(TAG, "Only Kiosk App can restart temi")
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return
        }
        try {
            sdkService?.restart(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "restart() error")
        }
    }

    /**
     * Start Launcher's internal page.
     *
     * @param page Target page.
     */
    fun startPage(page: Page) {
        try {
            sdkService?.startPage(applicationInfo.packageName, page.value)
        } catch (e: RemoteException) {
            Log.e(TAG, "startPage() error")
        }
    }

    /**
     * Check if temi is locked.
     */
    @get:JvmName("isLocked")
    @get:CheckResult
    var locked: Boolean
        get() {
            return try {
                sdkService?.isLocked ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isLocked() error")
                false
            }
        }
        /**
         * @param lock true(false) to lock(unlock) temi
         */
        set(lock) {
            try {
                sdkService?.lock(applicationInfo.packageName, lock)
            } catch (e: RemoteException) {
                Log.e(TAG, "setLocked() error")
            }
        }

    /**
     * Mute Alexa's MIC. Only useful in Global version with Alexa assistant。
     */
    fun muteAlexa() {
        try {
            sdkService?.muteAlexa(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "muteAlexa() error")
        }
    }

    /**
     * Shut down temi.
     *
     */
    fun shutdown() {
        try {
            sdkService?.shutdown(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "shutdown() error")
        }
    }

    /**
     * Sound mode
     *
     * @param soundMode
     */
    fun setSoundMode(soundMode: SoundMode) {
        try {
            sdkService?.setSoundMode(applicationInfo.packageName, soundMode.value)
        } catch (e: RemoteException) {
            Log.e(TAG, "setSoundMode() error")
        }
    }

    /**
     * Get temi's nick name
     *
     * @return
     */
    @CheckResult
    fun getNickName(): String {
        return try {
            sdkService?.getNickName(applicationInfo.packageName) ?: ""
        } catch (e: RemoteException) {
            Log.e(TAG, "getNickName() error")
            ""
        }
    }

    /**
     * Set system mode.
     *
     * @param mode Default, Greet, Privacy
     */
    fun setMode(mode: Mode) {
        try {
            sdkService?.setMode(applicationInfo.packageName, mode.value)
        } catch (e: RemoteException) {
            Log.e(TAG, "setMode() error")
        }
    }

    /**
     * Get system mode.
     *
     * @return Default, Greet, Privacy
     */
    @CheckResult
    fun getMode(): Mode {
        return try {
            Mode.valueToEnum(sdkService?.mode)
        } catch (e: RemoteException) {
            Log.e(TAG, "getMode() error")
            Mode.DEFAULT
        }
    }

    /**
     * Get the supported latin keyboards
     *
     * @return
     */
    @CheckResult
    fun getSupportedLatinKeyboards(): Map<String, Boolean> {
        return try {
            sdkService?.supportedLatinKeyboards as Map<String, Boolean>
        } catch (e: RemoteException) {
            Log.e(TAG, "getSupportedLatinKeyboards() error")
            emptyMap()
        }
    }

    /**
     * Enabled latin keyboards
     *
     * @param keyboards should be from the keys of map by [getSupportedLatinKeyboards]
     *
     * And the first element will be the selected keyboard
     */
    fun enabledLatinKeyboards(keyboards: List<String>) {
        try {
            sdkService?.enabledLatinKeyboards(applicationInfo.packageName, keyboards)
        } catch (e: RemoteException) {
            Log.e(TAG, "enabledLatinKeyboards() error")
        }
    }

    /**
     * Check if the ground depth cliff detection is enabled.
     */
    @get:JvmName("isGroundDepthCliffDetectionEnabled")
    @get:CheckResult
    var groundDepthCliffDetectionEnabled: Boolean
        get() {
            return try {
                sdkService?.isGroundDepthCliffDetectionEnabled ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isGroundDepthCliffDetectionEnabled() error")
                false
            }
        }
        /**
         * @param enabled Set `true` to enabled the ground depth cliff detection, `false` otherwise.
         */
        set(enabled) {
            try {
                sdkService?.setGroundDepthCliffDetectionEnabled(
                    applicationInfo.packageName,
                    enabled
                )
            } catch (e: RemoteException) {
                Log.e(TAG, "setGroundDepthCliffDetectionEnabled() error")
            }
        }

    /**
     * Check if the robot has the cliff sensor.
     */
    @CheckResult
    fun hasCliffSensor(): Boolean {
        return try {
            sdkService?.hasCliffSensor() ?: false
        } catch (e: RemoteException) {
            Log.e(TAG, "hasCliffSensor() error")
            false
        }
    }

    /**
     * Check and set the mode of cliff sensor, see [CliffSensorMode]
     */
    @get:CheckResult
    var cliffSensorMode: CliffSensorMode
        get() {
            var mode = try {
                sdkService?.cliffSensorMode ?: 0
            } catch (e: RemoteException) {
                Log.d(TAG, "getCliffSensorMode() error")
                0
            }
            if (mode < 0 || mode >= CliffSensorMode.values().size) {
                mode = 0
            }
            return CliffSensorMode.values()[mode]
        }
        set(mode) {
            try {
                sdkService?.setCliffSensorMode(applicationInfo.packageName, mode.ordinal)
            } catch (e: RemoteException) {
                Log.d(TAG, "setCliffSensorMode() error")
            }
        }

    /**
     * Get or set the sensitivity of head depth.
     */
    @get:CheckResult
    var headDepthSensitivity: SensitivityLevel
        get() {
            var level = try {
                sdkService?.headDepthSensitivity ?: 0
            } catch (e: RemoteException) {
                Log.d(TAG, "getHeadDepthSensitivity() error")
                0
            }
            if (level < 0 || level >= SensitivityLevel.values().size) {
                level = 0
            }
            return SensitivityLevel.values()[level]
        }
        set(sensitivityLevel) {
            try {
                sdkService?.setHeadDepthSensitivity(
                    applicationInfo.packageName,
                    sensitivityLevel.ordinal
                )
            } catch (e: RemoteException) {
                Log.d(TAG, "setHeadDepthSensitivity() error")
            }
        }

    /**
     * Check or set if the front TOF is enabled.
     */
    @get:JvmName("isFrontTOFEnabled")
    @get:CheckResult
    var frontTOFEnabled: Boolean
        get() {
            return try {
                sdkService?.isFrontTOFEnabled ?: true
            } catch (e: RemoteException) {
                Log.d(TAG, "isFrontTOFEnabled() error")
                true
            }
        }
        set(enabled) {
            try {
                sdkService?.setFrontTOFEnabled(applicationInfo.packageName, enabled)
            } catch (e: RemoteException) {
                Log.d(TAG, "setFrontTOFEnabled() error")
            }
        }

    /**
     * Check or set if the back TOF is enabled.
     */
    @get:JvmName("isBackTOFEnabled")
    @get:CheckResult
    var backTOFEnabled: Boolean
        get() {
            return try {
                sdkService?.isBackTOFEnabled ?: true
            } catch (e: RemoteException) {
                Log.d(TAG, "isBackTOFEnabled() error")
                true
            }
        }
        set(enabled) {
            try {
                sdkService?.setBackTOFEnabled(applicationInfo.packageName, enabled)
            } catch (e: RemoteException) {
                Log.d(TAG, "setBackTOFEnabled() error")
            }
        }

    @Throws(RemoteException::class)
    fun showNormalNotification(notification: NormalNotification) {
        if (sdkService != null) {
            sdkService!!.showNormalNotification(notification)
        } else {
            throw RemoteException("Sdk service is null.")
        }
    }

    @Throws(RemoteException::class)
    fun showAlertNotification(
        notification: AlertNotification,
        notificationListener: NotificationListener
    ) {
        if (sdkService != null) {
            sdkService!!.showAlertNotification(notification)
            listenersMap[notification.notificationId] = notificationListener
        } else {
            throw RemoteException("Sdk service is null.")
        }
    }

    @Throws(RemoteException::class)
    fun removeAlertNotification(notification: AlertNotification) {
        if (sdkService != null) {
            sdkService!!.removeAlertNotification(notification)
        } else {
            throw RemoteException("Sdk service is null.")
        }
    }

    @UiThread
    fun addOnPrivacyModeStateChangedListener(listener: OnPrivacyModeChangedListener) {
        onPrivacyModeStateChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnPrivacyModeStateChangedListener(listener: OnPrivacyModeChangedListener) {
        onPrivacyModeStateChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnBatteryStatusChangedListener(listener: OnBatteryStatusChangedListener) {
        onBatteryStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnBatteryStatusChangedListener(listener: OnBatteryStatusChangedListener) {
        onBatteryStatusChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnDisabledFeatureListUpdatedListener(listener: OnDisabledFeatureListUpdatedListener) {
        onDisabledFeatureListUpdatedListeners.add(listener)
    }

    @UiThread
    fun removeOnDisabledFeatureListUpdatedListener(listener: OnDisabledFeatureListUpdatedListener) {
        onDisabledFeatureListUpdatedListeners.remove(listener)
    }

    @UiThread
    fun addOnGreetModeStateChangedListener(listener: OnGreetModeStateChangedListener) {
        onGreetModeStateChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnGreetModeStateChangedListener(listener: OnGreetModeStateChangedListener) {
        onGreetModeStateChangedListeners.remove(listener)
    }

    /*****************************************/
    /*               Kiosk Mode              */
    /*****************************************/

    /**
     * Is current skill a Kiosk Mode skill.
     *
     * @return Value of the Metadata Kiosk.
     */
    private val isMetaDataKiosk: Boolean
        get() = applicationInfo.metaData != null
                && applicationInfo.metaData.getBoolean(SdkConstants.METADATA_KIOSK, false)

    /**
     * Toggle the wakeup trigger on and off.
     *
     * @param disabled Set true to disable the wakeup or false to enable it.
     */
    fun toggleWakeup(disabled: Boolean) {
        if (!isMetaDataKiosk) {
            Log.e(TAG, "Wakeup can only be toggled in Kiosk Mode")
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return
        }
        try {
            sdkService?.toggleWakeup(disabled, applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "toggleWakeup() error")
        }
    }

    val wakeupWordDisabled: Boolean
        @CheckResult
        @JvmName("isWakeupDisabled")
        get() {
            try {
                return sdkService?.isWakeupDisabled ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isWakeupDisabled() error")
            }
            return false
        }

    /**
     * Toggle the visibility of the navigation billboard when you perform goTo commands.
     *
     * @param disabled Set true to disable the billboard or false to enable it.
     */
    fun toggleNavigationBillboard(disabled: Boolean) {
        if (!isMetaDataKiosk) {
            Log.e(TAG, "Billboard can only be toggled in Kiosk Mode")
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return
        }
        try {
            sdkService?.setGoToBillboardDisabled(applicationInfo.packageName, disabled)
        } catch (e: RemoteException) {
            Log.e(TAG, "toggleNavigationBillboard() error")
        }
    }

    @get:JvmName("isNavigationBillboardDisabled")
    val navigationBillboardDisabled: Boolean
        @CheckResult
        get() {
            try {
                return sdkService?.isGoToBillboardDisabled ?: false
            } catch (e: RemoteException) {
                Log.e(TAG, "isNavigationBillboardDisabled() error")
            }
            return false
        }

    /**
     * Request to be the selected Kiosk Mode App programmatically.
     */
    fun requestToBeKioskApp() {
        if (!isMetaDataKiosk) {
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return
        }
        try {
            sdkService?.requestToBeKioskApp(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "requestToBeKioskApp() error")
        }
    }

    /**
     * Whether this App is selected as the Kiosk Mode App.
     */
    @CheckResult
    fun isSelectedKioskApp(): Boolean {
        if (!isMetaDataKiosk) {
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return false
        }
        try {
            return sdkService?.isSelectedKioskApp(applicationInfo.packageName) ?: false
        } catch (e: RemoteException) {
            Log.e(TAG, "isSelectedKioskApp() error")
        }
        return false
    }

    fun setKioskModeOn(on: Boolean = true) {
        if (!isMetaDataKiosk) {
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return
        }
        try {
            sdkService?.setKioskModeOn(applicationInfo.packageName, on)
        } catch (e: RemoteException) {
            Log.e(TAG, "setKioskModeOn() error")
        }
    }

    @CheckResult
    fun isKioskModeOn(): Boolean {
        return try {
            sdkService?.isKioskModeOn ?: false
        } catch (e: RemoteException) {
            Log.e(TAG, "isKioskModeOn() error")
            false
        }
    }

    /*****************************************/
    /*            Activity Stream            */
    /*****************************************/

    fun setActivityStreamPublishListener(activityStreamPublishListener: ActivityStreamPublishListener?) {
        this.activityStreamPublishListener = activityStreamPublishListener
    }

    @Throws(RemoteException::class)
    fun shareActivityObject(activityStreamObject: ActivityStreamObject) {
        sdkService?.let {
            AsyncTask.execute {
                ActivityStreamUtils.handleActivityStreamObject(activityStreamObject)
                try {
                    it.shareActivityStreamObject(activityStreamObject)
                } catch (e: RemoteException) {
                    Log.e(TAG, "Sdk service is null")
                }
            }
        }
    }

    /*****************************************/
    /*                 Media                 */
    /*****************************************/

    fun setMediaButtonListener(mediaButtonListener: MediaButtonListener) {
        this.mediaButtonListener = mediaButtonListener
    }

    fun removeMediaButtonListener() {
        mediaButtonListener = null
    }

    @Deprecated("No longer supported")
    @Throws(RemoteException::class)
    fun updateMediaBar(mediaBarData: MediaBarData) {
        mediaBarData.packageName = applicationInfo.packageName
        mediaBar.updateMediaBar(mediaBarData)
    }

    @Deprecated("No longer supported")
    @Throws(RemoteException::class)
    fun pauseMediaBar() {
        mediaBar.pauseMediaBar()
    }

    @Deprecated("No longer supported")
    @Throws(RemoteException::class)
    fun setMediaPlaying(isPlaying: Boolean) {
        mediaBar.setMediaPlaying(isPlaying, applicationInfo.packageName)
    }

    /*****************************************/
    /*             Detection Mode            */
    /*****************************************/

    @UiThread
    fun addOnUserInteractionChangedListener(listener: OnUserInteractionChangedListener) {
        onUserInteractionChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnUserInteractionChangedListener(listener: OnUserInteractionChangedListener) {
        onUserInteractionChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnDetectionStateChangedListener(listener: OnDetectionStateChangedListener) {
        onDetectionStateChangedListeners.add(listener)
    }

    @Deprecated(
        "Use removeOnDetectionStateChangedListener(listener) instead.",
        ReplaceWith("this.removeOnDetectionStateChangedListener(listener)"),
        DeprecationLevel.WARNING
    )
    @UiThread
    fun removeDetectionStateChangedListener(listener: OnDetectionStateChangedListener) {
        onDetectionStateChangedListeners.remove(listener)
    }

    @UiThread
    fun removeOnDetectionStateChangedListener(listener: OnDetectionStateChangedListener) {
        onDetectionStateChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnDetectionDataChangedListener(listener: OnDetectionDataChangedListener) {
        onDetectionDataChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnDetectionDataChangedListener(listener: OnDetectionDataChangedListener) {
        onDetectionDataChangedListeners.remove(listener)
    }

    /*****************************************/
    /*               Permission              */
    /*****************************************/

    /**
     * Check permission grant status.
     *
     * @param permission The [Permission] you want to check.
     * @return Grant status. See [com.robotemi.sdk.permission.Permission.PermissionResult].
     */
    @Permission.PermissionResult
    @CheckResult
    fun checkSelfPermission(permission: Permission): Int {
        if (permission.isKioskPermission && !isMetaDataKiosk) {
            Log.w(TAG, "Only Kiosk App may have kiosk permissions")
            sdkServiceCallback.onSdkError(SdkException.permissionDenied("Kiosk Mode"))
            return Permission.DENIED
        }
        try {
            return sdkService?.checkSelfPermission(applicationInfo.packageName, permission.value)
                ?: Permission.DENIED
        } catch (e: RemoteException) {
            Log.e(TAG, "checkSelfPermission() error")
        }
        return Permission.DENIED
    }

    /**
     * Request permissions.
     *
     * If you already had the permission, Launcher will not handle this request again.
     *
     * Add [OnRequestPermissionResultListener] to listen the request result.
     *
     * @param permissions A list holds the permissions you want to request.
     * @param requestCode Identify which request.
     */
    fun requestPermissions(permissions: List<Permission>, requestCode: Int) {
        val permissionsFromMetadata =
            applicationInfo.metaData?.getString(SdkConstants.METADATA_PERMISSIONS)
        if (permissionsFromMetadata.isNullOrBlank()) {
            Log.w(TAG, "There is no valid permission in metadata")
            return
        }

        val validPermissions = mutableListOf<String>()
        for (permission in permissions) {
            if (!permissionsFromMetadata.contains(permission.value)) {
                Log.w(TAG, "This permission $permission is not declared in AndroidManifest.xml")
                continue
            }
            validPermissions.add(permission.value)
        }

        if (validPermissions.isEmpty()) {
            Log.w(TAG, "There is no valid permission in permissions")
            return
        }

        try {
            sdkService?.requestPermissions(
                applicationInfo.packageName,
                validPermissions,
                requestCode
            )
        } catch (e: RemoteException) {
            Log.e(TAG, "requestPermissions() error")
        }
    }

    @UiThread
    fun addOnRequestPermissionResultListener(listener: OnRequestPermissionResultListener) {
        onRequestPermissionResultListeners.add(listener)
    }

    @UiThread
    fun removeOnRequestPermissionResultListener(listener: OnRequestPermissionResultListener) {
        onRequestPermissionResultListeners.remove(listener)
    }

    /*****************************************/
    /*                Sequence               */
    /*****************************************/

    /**
     * Fetch all sequences user created on the Web platform.
     *
     * @return List holds all sequences.
     */
    @WorkerThread
    @CheckResult
    @JvmOverloads
    fun getAllSequences(tags: List<String> = emptyList()): List<SequenceModel> {
        return try {
            (sdkService?.getAllSequences(applicationInfo.packageName, tags.filter { it != "" })
                ?: emptyList<SequenceModel>()).map { it.compatible() }
        } catch (e: RemoteException) {
            Log.e(TAG, "getAllSequences() error")
            emptyList()
        }
    }

    /**
     * Play sequence by sequence ID.
     *
     * @param sequenceId Sequence ID you want to play.
     * @param withPlayer Whether to play sequence with the player panel.
     * @param repeat How many times will this sequence be played after the first playing.
     */
    @JvmOverloads
    fun playSequence(sequenceId: String, withPlayer: Boolean = false, repeat: Int = 0) {
        try {
            sdkService?.playSequence(applicationInfo.packageName, sequenceId, withPlayer, repeat)
        } catch (e: RemoteException) {
            Log.e(TAG, "playSequence() error")
        }
    }

    /**
     * Control current playing sequence.
     *
     * @param sequenceCommand Operations for controlling sequence, see [SequenceCommand].
     */
    fun controlSequence(sequenceCommand: SequenceCommand) {
        try {
            sdkService?.controlSequence(applicationInfo.packageName, sequenceCommand.ordinal)
        } catch (e: RemoteException) {
            Log.e(TAG, "controlSequence() error")
        }
    }

    @UiThread
    fun addOnSequencePlayStatusChangedListener(listener: OnSequencePlayStatusChangedListener) {
        onSequencePlayStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnSequencePlayStatusChangedListener(listener: OnSequencePlayStatusChangedListener) {
        onSequencePlayStatusChangedListeners.remove(listener)
    }

    /*****************************************/
    /*                  Map                  */
    /*****************************************/

    /**
     * Get map data.
     *
     * @return Map data.
     */
    @Nullable
    @WorkerThread
    @CheckResult
    fun getMapData(): MapDataModel? {
        val gson = Gson()
        var mapDataModel: MapDataModel? = null
        val inputStream =
            getInputStreamByMediaKey(ContentType.MAP_DATA_IMAGE, "") ?: return null
        val inputStreamReader = InputStreamReader(inputStream)

        var cursor: Cursor? = null
        try {
            mapDataModel = MapDataModel(
                gson.fromJson(
                    inputStreamReader,
                    MapDataModel::class.java
                ).mapImage
            )
            val uriStr = StringBuffer("content://")
                .append(SdkConstants.PROVIDER_AUTHORITY)
                .append("/").append(SdkConstants.PROVIDER_PARAMETER_MAP_DATA)
                .toString()
            cursor = context.contentResolver.query(
                Uri.parse(uriStr),
                null,
                null,
                null,
                null
            )
            if (cursor == null || !cursor.moveToFirst()) {
                return mapDataModel
            }
            val mapId = cursor.getString(cursor.getColumnIndex(MAP_ID))
            val mapInfoJson = cursor.getString(cursor.getColumnIndex(MAP_INFO))
            val mapElementsJson = cursor.getString(cursor.getColumnIndex(MAP_ELEMENTS))
            val mapInfo = gson.fromJson(mapInfoJson, MapInfo::class.java)
            val mapElements = gson.fromJson<List<Layer>>(
                mapElementsJson,
                object : TypeToken<List<Layer>>() {}.type
            )
            mapDataModel.mapId = mapId
            mapDataModel.mapInfo = mapInfo
            mapElements?.map {
                if (it.layerCategory == VIRTUAL_WALL) mapDataModel.virtualWalls.add(it)
                if (it.layerCategory == GREEN_PATH) mapDataModel.greenPaths.add(it)
                if (it.layerCategory == LOCATION) mapDataModel.locations.add(it)
            }
        } catch (e: JsonParseException) {
            Log.e(TAG, "getMapData() - JSON parse error: ${e.message}")
        } finally {
            cursor?.close()
            try {
                inputStream.close()
                inputStreamReader.close()
            } catch (e: IOException) {
                Log.e(TAG, "getMapData() - ${e.message}")
            }
        }
        return mapDataModel
    }

    /**
     * Get map list from server.
     *
     * @return Saved map
     */
    @WorkerThread
    fun getMapList(): List<MapModel> {
        try {
            return sdkService?.getMapList(applicationInfo.packageName) ?: emptyList()
        } catch (e: RemoteException) {
            Log.e(TAG, "getMapList() error")
        }
        return emptyList()
    }

    /**
     * Load map by map ID.
     *
     * @param mapId The map ID of the map to be loaded
     * @param reposeRequired If needs to repose after loading map
     * @param position The position for repose
     */
    @JvmOverloads
    fun loadMap(mapId: String, reposeRequired: Boolean = false, position: Position? = null) {
        try {
            if (position == null) {
                sdkService?.loadMap(applicationInfo.packageName, mapId, reposeRequired)
            } else {
                sdkService?.loadMapWithPosition(
                    applicationInfo.packageName,
                    mapId,
                    reposeRequired,
                    position
                )
            }
        } catch (e: RemoteException) {
            Log.e(TAG, "loadMap() error")
        }
    }

    fun getCurrentFloor(): Floor? {
        return try {
            sdkService?.getCurrentFloor(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "getCurrentFloor() error")
            null
        }
    }

    fun getAllFloors(): List<Floor> {
        return try {
            sdkService?.getAllFloors(applicationInfo.packageName) ?: emptyList()
        } catch (e: RemoteException) {
            Log.e(TAG, "getAllFloors() error")
            emptyList()
        }
    }

    fun loadFloor(floorId: Int, position: Position) {
        try {
            sdkService?.loadFloor(applicationInfo.packageName, floorId, position)
        } catch (e: RemoteException) {
            Log.e(TAG, "loadFloor() error")
        }
    }

    @UiThread
    fun addOnLoadMapStatusChangedListener(listener: OnLoadMapStatusChangedListener) {
        onLoadMapStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnLoadMapStatusChangedListener(listener: OnLoadMapStatusChangedListener) {
        onLoadMapStatusChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnLoadFloorStatusChangedListener(listener: OnLoadFloorStatusChangedListener) {
        onLoadFloorStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnLoadFloorStatusChangedListener(listener: OnLoadFloorStatusChangedListener) {
        onLoadFloorStatusChangedListeners.remove(listener)
    }

    /*****************************************/
    /*            Face Recognition           */
    /*****************************************/

    /**
     * Start face recognition.
     */
    fun startFaceRecognition() {
        try {
            sdkService?.startFaceRecognition(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "startFaceRecognition() error")
        }
    }

    /**
     * Stop face recognition.
     */
    fun stopFaceRecognition() {
        try {
            sdkService?.stopFaceRecognition(applicationInfo.packageName)
        } catch (e: RemoteException) {
            Log.e(TAG, "stopFaceRecognition() error")
        }
    }

    @UiThread
    fun addOnFaceRecognizedListener(listener: OnFaceRecognizedListener) {
        onFaceRecognizedListeners.add(listener)
    }

    @UiThread
    fun removeOnFaceRecognizedListener(listener: OnFaceRecognizedListener) {
        onFaceRecognizedListeners.remove(listener)
    }

    @UiThread
    fun addOnContinuousFaceRecognizedListener(listener: OnContinuousFaceRecognizedListener) {
        onContinuousFaceRecognizedListeners.add(listener)
    }

    @UiThread
    fun removeOnContinuousFaceRecognizedListener(listener: OnContinuousFaceRecognizedListener) {
        onContinuousFaceRecognizedListeners.remove(listener)
    }

    /*****************************************/
    /*                 Common                */
    /*****************************************/

    @Nullable
    @WorkerThread
    fun getInputStreamByMediaKey(contentType: ContentType, mediaKey: String): InputStream? {
        val uriStr = StringBuffer("content://")
            .append(SdkConstants.PROVIDER_AUTHORITY)
            .append("/").append(contentType.path)
            .append("?").append(SdkConstants.PROVIDER_PARAMETER_MEDIA_KEY)
            .append("=").append(mediaKey)
            .toString()
        try {
            return context.contentResolver.openInputStream(Uri.parse(uriStr))
        } catch (e: FileNotFoundException) {
            Log.e(TAG, e.message)
            sdkServiceCallback.onSdkError(SdkException.launcherError("No such file exists"))
        } catch (e: IllegalStateException) {
            Log.e(TAG, "getInputStreamByMediaKey error.\n ${e.message}")
        }
        return null
    }

    @WorkerThread
    @JvmOverloads
    fun getSignedUrlByMediaKey(
        mediaKeys: List<String>,
        width: Int = -1,
        height: Int = -1
    ): List<Pair<String, String>> {
        return try {
            val results = sdkService?.getSignedUrlByMediaKey(
                applicationInfo.packageName,
                mediaKeys.filter { it.isNotBlank() },
                width,
                height
            ) ?: emptyList()
            return results.map {
                val jsonObject = JSONObject(it)
                val mediaKey = try {
                    jsonObject.getString("mediaKey")
                } catch (e: JSONException) {
                    ""
                }
                val signedUrl = try {
                    jsonObject.getString("signedUrl")
                } catch (e: JSONException) {
                    ""
                }
                return@map Pair(mediaKey, signedUrl)
            }
        } catch (e: RemoteException) {
            Log.e(TAG, "getSignedUrlByMediaKey() error")
            emptyList()
        }
    }

    @UiThread
    fun addOnSdkExceptionListener(listener: OnSdkExceptionListener) {
        onSdkExceptionListeners.add(listener)
    }

    @UiThread
    fun removeOnSdkExceptionListener(listener: OnSdkExceptionListener) {
        onSdkExceptionListeners.remove(listener)
    }

    /*****************************************/
    /*               Interface               */
    /*****************************************/

    interface WakeupWordListener {
        fun onWakeupWord(wakeupWord: String, direction: Int)
    }

    interface TtsListener {
        fun onTtsStatusChanged(ttsRequest: TtsRequest)
    }

    interface AsrListener {
        fun onAsrResult(asrResult: String)
    }

    interface NlpListener {
        fun onNlpCompleted(nlpResult: NlpResult)
    }

    interface ActivityStreamPublishListener {
        fun onPublish(message: ActivityStreamPublishMessage)
    }

    interface MediaButtonListener {

        fun onPlayButtonClicked(play: Boolean)

        fun onNextButtonClicked()

        fun onBackButtonClicked()

        fun onTrackBarChanged(position: Int)
    }

    interface NotificationListener {
        fun onNotificationBtnClicked(btnNumber: Int)
    }

    interface ConversationViewAttachesListener {
        fun onConversationAttaches(isAttached: Boolean)
    }

    companion object {

        private const val TAG = "Robot"

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: Robot? = null

        @JvmStatic
        fun getInstance(): Robot {
            if (instance == null) {
                synchronized(Robot::class.java) {
                    if (instance == null) {
                        val context = TemiSdkContentProvider.sdkContext
                            ?: throw NullPointerException("context == null")
                        instance = Robot(context)
                    }
                }
            }
            return instance!!
        }
    }
}
