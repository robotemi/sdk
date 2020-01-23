package com.robotemi.sdk

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.os.RemoteException
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.annotation.UiThread
import com.robotemi.sdk.activitystream.ActivityStreamObject
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage
import com.robotemi.sdk.activitystream.ActivityStreamUtils
import com.robotemi.sdk.telepresence.CallState
import com.robotemi.sdk.model.RecentCallModel
import com.robotemi.sdk.constants.SdkConstants
import com.robotemi.sdk.listeners.*
import com.robotemi.sdk.mediabar.AidlMediaBarController
import com.robotemi.sdk.mediabar.MediaBarData
import com.robotemi.sdk.notification.AlertNotification
import com.robotemi.sdk.notification.NormalNotification
import com.robotemi.sdk.notification.NotificationCallback
import java.util.*
import java.util.concurrent.CopyOnWriteArraySet

@SuppressWarnings("unused")
class Robot private constructor(context: Context) {

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

    private val onRobotReadyListeners = HashSet<OnRobotReadyListener>()

    private val onBeWithMeStatusChangeListeners =
        CopyOnWriteArraySet<OnBeWithMeStatusChangedListener>()

    private val onGoToLocationStatusChangeListeners =
        CopyOnWriteArraySet<OnGoToLocationStatusChangedListener>()

    private val onTelepresenceStatusChangedListeners =
        CopyOnWriteArraySet<OnTelepresenceStatusChangedListener>()

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
            if (ttsListeners.size > 0) {
                uiHandler.post {
                    for (ttsListener in ttsListeners) {
                        ttsListener.onTtsStatusChanged(ttsRequest)
                    }
                }
                return true
            }
            return false
        }

        override fun onWakeupWord(wakeupWord: String, direction: Int): Boolean {
            if (wakeUpWordListeners.size > 0) {
                uiHandler.post {
                    for (wakeupWordListener in wakeUpWordListeners) {
                        wakeupWordListener.onWakeupWord(wakeupWord, direction)
                    }
                }
                return true
            }
            return false
        }

        override fun onNlpCompleted(nlpResult: NlpResult): Boolean {
            if (nlpListeners.size > 0) {
                uiHandler.post {
                    for (nlpListener in nlpListeners) {
                        nlpListener.onNlpCompleted(nlpResult)
                    }
                }
                return true
            }
            return false
        }

        override fun onAsrResult(asrText: String): Boolean {
            if (asrListeners.size > 0) {
                uiHandler.post {
                    for (asrListener in asrListeners) {
                        asrListener.onAsrResult(asrText)
                    }
                }
                return true
            }
            return false
        }

        override fun onConversationViewAttaches(isAttached: Boolean): Boolean {
            if (conversationViewAttachesListeners.size > 0) {
                uiHandler.post {
                    for (conversationViewAttachesListener in conversationViewAttachesListeners) {
                        conversationViewAttachesListener.onConversationAttaches(isAttached)
                    }
                }
                return true
            }
            return false
        }

        override fun hasActiveNlpListeners(): Boolean {
            val hasActiveNlpListener = !nlpListeners.isEmpty()
            return hasActiveNlpListener
        }

        /*****************************************/
        /*                Location               */
        /*****************************************/

        override fun onGoToLocationStatusChanged(
            location: String,
            status: String,
            descriptionId: Int,
            description: String
        ): Boolean {
            if (!onGoToLocationStatusChangeListeners.isEmpty()) {
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
            return false
        }

        override fun onLocationsUpdated(locations: List<String>): Boolean {
            if (!onLocationsUpdatedListeners.isEmpty()) {
                uiHandler.post {
                    for (listener in onLocationsUpdatedListeners) {
                        listener?.onLocationsUpdated(locations)
                    }
                }
                return true
            }
            return false
        }

        /*****************************************/
        /*            Movement & Follow          */
        /*****************************************/

        override fun onBeWithMeStatusChanged(status: String): Boolean {
            if (onBeWithMeStatusChangeListeners.size > 0) {
                uiHandler.post {
                    for (listener in onBeWithMeStatusChangeListeners) {
                        listener.onBeWithMeStatusChanged(status)
                    }
                }
                return true
            }
            return false
        }

        override fun onConstraintBeWithStatusChanged(isContraint: Boolean): Boolean {
            if (onConstraintBeWithStatusChangedListeners.size > 0) {
                uiHandler.post {
                    for (listener in onConstraintBeWithStatusChangedListeners) {
                        listener.onConstraintBeWithStatusChanged(isContraint)
                    }
                }
                return true
            }
            return false
        }

        /*****************************************/
        /*           Users & Telepresence        */
        /*****************************************/

        override fun onTelepresenceStatusChanged(callState: CallState): Boolean {
            if (!onTelepresenceStatusChangedListeners.isEmpty()) {
                uiHandler.post {
                    for (listener in onTelepresenceStatusChangedListeners) {
                        if (listener != null && callState.sessionId == listener.sessionId) {
                            listener.onTelepresenceStatusChanged(callState)
                        }
                    }
                }
                return true
            }
            return false
        }

        override fun onUserUpdated(user: UserInfo): Boolean {
            if (!onUsersUpdatedListeners.isEmpty()) {
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
            return false
        }

        /*****************************************/
        /*                 Utils                 */
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
            if (onPrivacyModeStateChangedListeners.size > 0) {
                uiHandler.post {
                    for (listener in onPrivacyModeStateChangedListeners) {
                        listener.onPrivacyModeChanged(state)
                    }
                }
                return true
            }
            return false
        }

        override fun onBatteryStatusChanged(batteryData: BatteryData): Boolean {
            if (onBatteryStatusChangedListeners.size > 0) {
                uiHandler.post {
                    for (listener in onBatteryStatusChangedListeners) {
                        listener.onBatteryStatusChanged(batteryData)
                    }
                }
                return true
            }
            return false
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
            if (onUserInteractionChangedListeners.size > 0) {
                uiHandler.post {
                    for (listener in onUserInteractionChangedListeners) {
                        listener.onUserInteraction(isInteracting);
                    }
                }
                return true
            }
            return false
        }

        override fun onDetectionStateChanged(state: Int): Boolean {
            if (onDetectionStateChangedListeners.size > 0) {
                uiHandler.post {
                    for (listener in onDetectionStateChangedListeners) {
                        listener.onDetectionStateChanged(state)
                    }
                }
                return true
            }
            return false
        }
    }

    /*****************************************/
    /*                  Init                 */
    /*****************************************/

    private val isReady: Boolean
        get() = sdkService != null

    @UiThread
    fun onStart(activityInfo: ActivityInfo) {
        sdkService?.let {
            try {
                it.onStart(activityInfo)
            } catch (e: RemoteException) {
                Log.e(TAG, "onStart(ActivityInfo) - Binder invocation exception.")
            }
        } ?: run {
            Log.w(TAG, "onStart(ActivityInfo) - sdkService=null")
        }
    }

    @RestrictTo(LIBRARY)
    @UiThread
    fun setSdkService(sdkService: ISdkService?) {
        this.sdkService = sdkService
        mediaBar = AidlMediaBarController(sdkService)
        registerCallback()
        onRobotReadyListeners.forEach { it.onRobotReady(sdkService != null) }
    }

    @UiThread
    private fun registerCallback() {
        sdkService?.let {
            try {
                it.register(applicationInfo, sdkServiceCallback)
            } catch (e: RemoteException) {
                Log.e(TAG, "Remote invocation error.")
            }

        } ?: run {
            Log.w(TAG, "sdkService=null")
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
        sdkService?.let {
            try {
                ttsRequest.packageName = applicationInfo.packageName
                it.speak(ttsRequest)
            } catch (e: RemoteException) {
                Log.e(TAG, "Failed to invoke remote call speak()")
            }
        }
    }

    /**
     * The wakeup word of the temi's assistant.
     */
    val wakeupWord: String
        get() {
            sdkService?.let {
                try {
                    return it.wakeupWord ?: ""
                } catch (e: RemoteException) {
                    Log.e(TAG, "getWakeupWord() error.")
                }
            }
            return ""
        }

    /**
     * Trigger temi's wakeup programmatically.
     */
    fun wakeup() {
        sdkService?.let {
            try {
                it.wakeup()
            } catch (e: RemoteException) {
                Log.e(TAG, "wakeup() error.")
            }
        }
    }

    /**
     * Stops currently processed TTS request and empty the queue.
     */
    fun cancelAllTtsRequests() {
        sdkService?.let {
            try {
                it.cancelAll()
            } catch (e: RemoteException) {
                Log.e(TAG, "Failed to invoke remote call cancelAllTtsRequest()")
            }
        }
    }

    /**
     * Request to lock contexts even if skill screen is dismissed.
     * Useful for services running in the background without UI.
     *
     * @param contextsToLock - List of contexts names to lock.
     */
    fun lockContexts(contextsToLock: List<String>) {
        if (sdkService != null) {
            try {
                sdkService!!.lockContexts(contextsToLock)
            } catch (e: RemoteException) {
                Log.e(TAG, "lockContexts(List<String>) error.")
            }

        }
    }

    /**
     * Release previously locked contexts. See [.lockContexts].
     *
     * @param contextsToRelease - List of contexts names to release.
     */
    fun releaseContexts(contextsToRelease: List<String>) {
        if (sdkService != null) {
            try {
                sdkService!!.releaseContexts(contextsToRelease)
            } catch (e: RemoteException) {
                Log.e(TAG, "releaseContexts(List<String>) error.")
            }

        }
    }

    @UiThread
    fun addConversationViewAttachesListenerListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        conversationViewAttachesListeners.add(conversationViewAttachesListener)
    }

    @UiThread
    fun removeConversationViewAttachesListenerListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
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

    /*****************************************/
    /*                Location               */
    /*****************************************/

    /**
     * Save location.
     *
     * @param - Location name.
     * @return Result of a successful or failed operation.
     */
    fun saveLocation(name: String): Boolean {
        sdkService?.let {
            try {
                return it.saveLocation(name)
            } catch (e: RemoteException) {
                Log.e(TAG, "saveLocation(String)")
            }
        }
        return false
    }

    /**
     * Delete location.
     *
     * @param name - Location name.
     * @return Result of a successful or failed operation.
     */
    fun deleteLocation(name: String): Boolean {
        sdkService?.let {
            try {
                return it.deleteLocation(name)
            } catch (e: RemoteException) {
                Log.e(TAG, "deleteLocation(String)")
            }
        }
        return false
    }

    /**
     * Retrieve list of previously saved locations.
     *
     * @return List of saved locations.
     */
    val locations: List<String>
        get() {
            sdkService?.let {
                try {
                    return it.locations ?: emptyList()
                } catch (e: RemoteException) {
                    Log.e(TAG, "getLocations()")
                }
            }
            return emptyList()
        }

    /**
     * Send robot to previously saved location.
     *
     * @param location - Saved location name.
     */
    fun goTo(location: String) {
        require(!TextUtils.isEmpty(location)) { "Location can not be null or empty." }
        sdkService?.let {
            try {
                it.goTo(location)
            } catch (e: RemoteException) {
                Log.e(TAG, "goTo(String) error.")
            }
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

    /*****************************************/
    /*            Movement & Follow          */
    /*****************************************/

    /**
     * Request robot to follow user around.
     * See [OnBeWithMeStatusChangedListener] to listen for status changes.
     */
    fun beWithMe() {
        sdkService?.let {
            try {
                it.beWithMe()
            } catch (e: RemoteException) {
                Log.e(TAG, "beWithMe()")
            }
        }
    }

    /**
     * Start constraint follow.
     */
    fun constraintBeWith() {
        sdkService?.let {
            try {
                it.constraintBeWith()
            } catch (e: RemoteException) {
                Log.e(TAG, "constraintBeWith() error.")
            }
        }
    }

    /**
     * Request robot to stop any movement.
     */
    fun stopMovement() {
        sdkService?.let {
            try {
                it.stopMovement()
            } catch (e: RemoteException) {
                Log.e(TAG, "stopMovement()")
            }

        }
    }

    /**
     * Joystick commands.
     *
     * @param x Move on the x axis from -1 to 1.
     * @param y Move on the y axis from -1 to 1.
     */
    fun skidJoy(x: Float, y: Float) {
        sdkService?.let {
            try {
                it.skidJoy(x, y)
            } catch (e: RemoteException) {
                Log.e(TAG, "skidJoy(float, float) (x=$x, y=$y)")
            }
        }
    }

    /**
     * To turn temi by a specific degree.
     *
     * @param degrees the degree amount you want the robot to turn
     * @param speed   deprecated
     */
    @Deprecated("See {{@link #turnBy(int)}}", ReplaceWith("turnBy(degrees)"))
    fun turnBy(degrees: Int, speed: Float) {
        turnBy(degrees)
    }

    /**
     * To turn temi by a specific degree.
     *
     * @param degrees the degree amount you want the robot to turn
     */
    fun turnBy(degrees: Int) {
        sdkService?.let {
            try {
                it.turnBy(degrees, 1.0f)
            } catch (e: RemoteException) {
                Log.e(TAG, "turnBy(int) (degrees=$degrees)")
            }

        }
    }

    /**
     * To tilt temi's head to a specific angle.
     *
     * @param degrees the degree which you want the robot to tilt to, between 55 and -25
     * @param speed   deprecated
     */
    @Deprecated("See {{@link #tiltAngle(int)}}", ReplaceWith("tiltAngle(degrees)"))
    fun tiltAngle(degrees: Int, speed: Float) {
        tiltAngle(degrees)
    }

    /**
     * To tilt temi's head to a specific angle.
     *
     * @param degrees the degree which you want the robot to tilt to, between 55 and -25
     */
    fun tiltAngle(degrees: Int) {
        sdkService?.let {
            try {
                it.tiltAngle(degrees, 1.0f)
            } catch (e: RemoteException) {
                Log.e(TAG, "turnBy(int) (degrees=$degrees)")
            }

        }
    }

    /**
     * To tilt temi's head to by a specific degree.
     *
     * @param degrees The degree amount you want the robot to tilt
     * @param speed
     */
    @Deprecated("See {{@link #tiltBy(int)}}", ReplaceWith("tiltBy(degrees)"))
    fun tiltBy(degrees: Int, speed: Float) {
        tiltBy(degrees)
    }

    /**
     * To tilt temi's head to by a specific degree.
     *
     * @param degrees The degree amount you want the robot to tilt
     */
    fun tiltBy(degrees: Int) {
        sdkService?.let {
            try {
                it.tiltBy(degrees, 1.0f)
            } catch (e: RemoteException) {
                Log.e(TAG, "tiltBy(int) (degrees=$degrees)")
            }

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
        onConstraintBeWithStatusChangedListeners.remove(listener);
    }

    /*****************************************/
    /*           Users & Telepresence        */
    /*****************************************/

    /**
     * Get the information of temi's admin.
     */
    val adminInfo: UserInfo?
        get() {
            sdkService?.let {
                try {
                    return it.adminInfo
                } catch (e: RemoteException) {
                    Log.e(TAG, "getAdminInfo() error.")
                }

            }
            return null
        }

    /**
     * Fetch all the temi contacts.
     */
    val allContact: List<UserInfo>
        get() {
            val contactList = ArrayList<UserInfo>()
            sdkService?.let {
                try {
                    contactList.addAll(it.allContacts)
                } catch (e: RemoteException) {
                    Log.e(TAG, "getAllContacts() error.")
                }

            }
            return contactList
        }

    /**
     * Fetch recent calls.
     */
    val recentCalls: List<RecentCallModel>
        get() {
            sdkService?.let {
                try {
                    return it.recentCalls ?: ArrayList()
                } catch (e: RemoteException) {
                    Log.e(TAG, "getRecentCalls() error.")
                }
            }
            return ArrayList()
        }

    /**
     * Start a video call to Admin.
     *
     * @param displayName Name of admin user info.
     * @param peerId ID of admin user info.
     * @return The sessionId of Telepresence call
     */
    fun startTelepresence(displayName: String, peerId: String): String {
        sdkService?.let {
            try {
                return it.startTelepresence(displayName, peerId) ?: ""
            } catch (e: RemoteException) {
                Log.e(TAG, "startTelepresence(String, String) (displayName=$displayName, peerId=$peerId)")
            }

        }
        return ""
    }

    /**
     * Start listening for Telepresence Status changes.
     *
     * @param listener The listener you want to add.
     */
    fun addOnTelepresenceStatusChangedListener(listener: OnTelepresenceStatusChangedListener) {
        onTelepresenceStatusChangedListeners.add(listener)
    }

    /**
     * Stop listening for Telepresence Status changes.
     *
     * @param listener The listener you added before.
     */
    fun removeOnTelepresenceStatusChangedListener(listener: OnTelepresenceStatusChangedListener) {
        onTelepresenceStatusChangedListeners.remove(listener)
    }

    /**
     * Start listening for user information updates.
     *
     * @param listener The listener you want to add.
     */
    fun addOnUsersUpdatedListener(listener: OnUsersUpdatedListener) {
        onUsersUpdatedListeners.add(listener)
    }

    /**
     * Stop listening for user information updates.
     *
     * @param listener The listener you added before.
     */
    fun removeOnUsersUpdatedListener(listener: OnUsersUpdatedListener) {
        onUsersUpdatedListeners.remove(listener)
    }

    /*****************************************/
    /*                 Utils                 */
    /*****************************************/

    /**
     * Request robot's serial number as a String.
     *
     * @return The serial number of the robot.
     */
    val serialNumber: String?
        get() {
            sdkService?.let {
                try {
                    return it.serialNumber
                } catch (e: RemoteException) {
                    Log.e(TAG, "getSerialNumber()")
                }

            }
            return null
        }

    /**
     * Request the robot to provide current battery status.
     *
     * @return The battery data the robot.
     */
    val batteryData: BatteryData?
        get() {
            sdkService?.let {
                try {
                    return it.batteryData
                } catch (e: RemoteException) {
                    Log.e(TAG, "getBatteryData() error.")
                }

            }
            return null
        }

    /**
     * Go to the App list of Launcher.
     */
    fun showAppList() {
        sdkService?.let {
            try {
                it.showAppList()
            } catch (e: RemoteException) {
                Log.e(TAG, "showAppList() error.")
            }

        }
    }

    /**
     * Show the top bar of Launcher.
     */
    fun showTopBar() {
        sdkService?.let {
            try {
                it.showTopBar()
            } catch (e: RemoteException) {
                Log.e(TAG, "showTopBar() error.")
            }

        }
    }

    /**
     * Hide the top bar of Launcher.
     */
    fun hideTopBar() {
        sdkService?.let {
            try {
                it.hideTopBar()
            } catch (e: RemoteException) {
                Log.e(TAG, "hideTopBar() error.")
            }
        }
    }

    /**
     * Toggle privacy mode on temi.
     */
    var privacyMode: Boolean
        set(on) {
            sdkService?.let {
                try {
                    it.togglePrivacyMode(on)
                } catch (e: RemoteException) {
                    Log.e(TAG, "togglePrivacyMode() error.")
                }
            }
        }
        get() {
            sdkService?.let {
                try {
                    return it.privacyModeState
                } catch (e: RemoteException) {
                    Log.e(TAG, "getPrivacyModeState() error.")
                }
            }
            return false
        }

    var isHardButtonsDisabled: Boolean
        set(disable) {
            sdkService?.let {
                try {
                    it.toggleHardButtons(disable)
                } catch (e: RemoteException) {
                    Log.e(TAG, "isHardButtonsEnabled() error")
                }
            }
        }
        get() {
            sdkService?.let {
                try {
                    return it.isHardButtonsDisabled
                } catch (e: RemoteException) {
                    Log.e(TAG, "setHardButtonsEnabled() error")
                }
            }
            return false
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

    /*****************************************/
    /*               Kiosk Mode              */
    /*****************************************/

    /**
     * Is current skill a Kiosk Mode skill.
     *
     * @return Value of the Metadata Kiosk.
     */
    private val isMetaDataKiosk: Boolean
        get() = applicationInfo.metaData != null && applicationInfo.metaData.getBoolean(
            SdkConstants.METADATA_KIOSK,
            false
        )

    /**
     * Toggle the wakeup trigger on and off
     *
     * @param disable set true to disable the wakeup or false to enable it
     */
    fun toggleWakeup(disable: Boolean) {
        sdkService?.let {
            if (isMetaDataKiosk) {
                try {
                    it.toggleWakeup(disable)
                } catch (e: RemoteException) {
                    Log.e(TAG, "toggleWakeup() error.")
                }

            } else {
                Log.e(TAG, "toggleWakeup() Wakeup can only be toggled in Kiosk Mode")
            }
        }
    }

    /**
     * Toggle the visibility of the navigation billboard when you perform goTo commands
     *
     * @param hide set true to hide the billboard or false to display it
     */
    fun toggleNavigationBillboard(hide: Boolean) {
        sdkService?.let {
            if (isMetaDataKiosk) {
                try {
                    it.toggleNavigationBillboard(hide)
                } catch (e: RemoteException) {
                    Log.e(TAG, "toggleNavigationBillboard() error.")
                }
            } else {
                Log.e(TAG, "toggleNavigationBillboard() Billboard can only be toggled in Kiosk Mode")
            }
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

    @Throws(RemoteException::class)
    fun updateMediaBar(mediaBarData: MediaBarData) {
        mediaBarData.packageName = applicationInfo.packageName
        mediaBar.updateMediaBar(mediaBarData)
    }

    @Throws(RemoteException::class)
    fun pauseMediaBar() {
        mediaBar.pauseMediaBar()
    }

    @Throws(RemoteException::class)
    fun setMediaPlaying(isPlaying: Boolean) {
        mediaBar.setMediaPlaying(isPlaying, applicationInfo.packageName)
    }

    /*****************************************/
    /*             Detection Mode            */
    /*****************************************/

    @UiThread
    fun addOnUserInteractionChangedListener(listener: OnUserInteractionChangedListener) {
        onUserInteractionChangedListeners.add(listener);
    }

    @UiThread
    fun removeOnUserInteractionChangedListener(listener: OnUserInteractionChangedListener) {
        onUserInteractionChangedListeners.remove(listener);
    }

    @UiThread
    fun addOnDetectionStateChangedListener(listener: OnDetectionStateChangedListener) {
        onDetectionStateChangedListeners.add(listener)
    }

    @UiThread
    fun removeDetectionStateChangedListener(listener: OnDetectionStateChangedListener) {
        onDetectionStateChangedListeners.remove(listener)
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

        val DEFAULT_ACTION = "skill.default"

        val PAUSE = "reserved.pauseMediaBar"

        val STOP = "reserved.stop"

        val RESUME = "reserved.resume"

        private val TAG = "Robot"

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
