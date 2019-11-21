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
import androidx.annotation.RestrictTo
import androidx.annotation.RestrictTo.Scope.LIBRARY
import androidx.annotation.UiThread
import com.robotemi.sdk.activitystream.ActivityStreamObject
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage
import com.robotemi.sdk.activitystream.ActivityStreamUtils
import com.robotemi.sdk.calls.CallState
import com.robotemi.sdk.calls.RecentCallModel
import com.robotemi.sdk.constants.SdkConstants
import com.robotemi.sdk.listeners.*
import com.robotemi.sdk.mediabar.AidlMediaBarController
import com.robotemi.sdk.mediabar.MediaBarData
import com.robotemi.sdk.notification.AlertNotification
import com.robotemi.sdk.notification.NormalNotification
import com.robotemi.sdk.notification.NotificationCallback
import com.robotemi.sdk.voice.NlpResult
import com.robotemi.sdk.voice.TtsRequest
import timber.log.Timber
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
            Timber.d("onTtsStatusChanged(TtsRequest) (ttsRequest=$ttsRequest , thread=${Thread.currentThread().name} )")
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
            Timber.d("onWakeupWord(String) (wakeupWord= $wakeupWord, direction = $direction, thread= ${Thread.currentThread().name})")
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
            Timber.d("onNlpCompleted(NlpResult) (nlpResult= $nlpResult , thread= ${Thread.currentThread().name} )")
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

        override fun onConversationViewAttaches(isAttached: Boolean): Boolean {
            Timber.d("onConversationViewAttaches(boolean) (isAttached= $isAttached , thread=  ${Thread.currentThread().name})")
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
            Timber.d("hasActiveNlpListeners() (hasActiveNlpListeners=$hasActiveNlpListener)")
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
            Timber.d("onGoToLocationStatusChanged(String, String, int, String) (location= location, status= $status, descriptionId= $descriptionId, description=$description)")
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
            Timber.d("onLocationsUpdated(List<String>) (locations size=  $locations.size)")
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
            Timber.d("onBeWithMeStatusChanged(String) (status=$status)")
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
            Timber.d("onConstraintBeWithStatusChanged(String) (isContraint=$isContraint)")
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
            Timber.d("onTelepresenceStatusChanged(CallState) (callState= $callState)")
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
            Timber.d("onUserUpdated(UserInfo) (user= $user)")
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

        override fun onNotificationBtnClicked(
            notificationCallback: NotificationCallback
        ) {
            Timber.w("onNotificationBtnClicked")
            uiHandler.post {
                val notificationListener = listenersMap[notificationCallback.notificationId]
                if (notificationListener != null) {
                    notificationListener.onNotificationBtnClicked(notificationCallback.event)
                    listenersMap.remove(notificationCallback.notificationId)
                }
            }
        }

        override fun onPrivacyModeStateChanged(state: Boolean): Boolean {
            Timber.d("onPrivacyModeStateChanged(Boolean) (state=$state)")
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
            Timber.d("onBatteryStatusChanged(BatteryData) (level=${batteryData.batteryPercentage})")
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
            Timber.d("onActivityStreamPublish: $message.success()")
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
                } else {
                    Timber.w("mediaButtonListener=null")
                }
            }
        }

        override fun onNextButtonClicked() {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onNextButtonClicked()
                } else {
                    Timber.w("mediaButtonListener=null")
                }
            }
        }

        override fun onBackButtonClicked() {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onBackButtonClicked()
                } else {
                    Timber.w("mediaButtonListener=null")
                }
            }
        }

        override fun onTrackBarChanged(position: Int) {
            uiHandler.post {
                if (mediaButtonListener != null) {
                    mediaButtonListener!!.onTrackBarChanged(position)
                } else {
                    Timber.w("mediaButtonListener=null")
                }
            }
        }

        /*****************************************/
        /*             Detection Mode            */
        /*****************************************/

        override fun onUserInteractionStatusChanged(isInteracting: Boolean): Boolean {
            Timber.d("onUserInteraction(boolean) (isDetected=$isInteracting)")
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
    }

    /*****************************************/
    /*                  Init                 */
    /*****************************************/

    private val isReady: Boolean
        get() = sdkService != null

    @UiThread
    fun onStart(activityInfo: ActivityInfo) {
        Timber.d("onStart(ActivityInfo) (activityInfo=$activityInfo)")
        sdkService?.let {
            try {
                it.onStart(activityInfo)
            } catch (e: RemoteException) {
                Timber.e(e, "onStart(ActivityInfo) - Binder invocation exception.")
            }
        } ?: run {
            Timber.w("onStart(ActivityInfo) - sdkService=null")
        }
    }

    @RestrictTo(LIBRARY)
    @UiThread
    fun setSdkService(sdkService: ISdkService?) {
        Timber.d("setSdkService(ISdkService)")
        this.sdkService = sdkService
        mediaBar = AidlMediaBarController(sdkService)
        registerCallback()
        onRobotReadyListeners.forEach { it.onRobotReady(sdkService != null) }
    }

    @UiThread
    private fun registerCallback() {
        Timber.d("registerCallback()")
        sdkService?.let {
            try {
                it.register(applicationInfo, sdkServiceCallback)
            } catch (e: RemoteException) {
                Timber.e(e, "Remote invocation error.")
            }

        } ?: run {
            Timber.w("sdkService=null")
        }
    }

    @UiThread
    fun addOnRobotReadyListener(onRobotReadyListener: OnRobotReadyListener) {
        Timber.d("addOnRobotReadyListener(OnRobotReadyListener)")
        onRobotReadyListeners.add(onRobotReadyListener)

        onRobotReadyListener.onRobotReady(isReady)
    }

    @UiThread
    fun removeOnRobotReadyListener(onRobotReadyListener: OnRobotReadyListener) {
        Timber.d("removeOnRobotReadyListener(OnRobotReadyListener)")
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
                Timber.e(e, "Failed to invoke remote call speak()")
            }
        }
    }

    /**
     * The wakeup word of the temi's assistant.
     */
    val wakeupWord: String
        get() {
            Timber.d("getWakeupWord()")
            sdkService?.let {
                try {
                    return it.wakeupWord
                } catch (e: RemoteException) {
                    Timber.e(e, "getWakeupWord() error.")
                }
            }
            return ""
        }

    /**
     * Trigger temi's wakeup programmatically.
     */
    fun wakeup() {
        Timber.d("wakeup()")
        sdkService?.let {
            try {
                it.wakeup()
            } catch (e: RemoteException) {
                Timber.e(e, "wakeup() error.")
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
                Timber.e(e, "Failed to invoke remote call cancelAllTtsRequest()")
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
        Timber.d("lockContexts(List<String>) (contextsToLock=$contextsToLock)")
        if (sdkService != null) {
            try {
                sdkService!!.lockContexts(contextsToLock)
            } catch (e: RemoteException) {
                Timber.e(e, "lockContexts(List<String>) error.")
            }

        }
    }

    /**
     * Release previously locked contexts. See [.lockContexts].
     *
     * @param contextsToRelease - List of contexts names to release.
     */
    fun releaseContexts(contextsToRelease: List<String>) {
        Timber.d("releaseContexts(List<String>) (contextsToRelease=$contextsToRelease)")
        if (sdkService != null) {
            try {
                sdkService!!.releaseContexts(contextsToRelease)
            } catch (e: RemoteException) {
                Timber.e(e, "releaseContexts(List<String>) error.")
            }

        }
    }

    @UiThread
    fun addConversationViewAttachesListenerListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        Timber.d("addConversationViewAttachesListenerListener(ConversationViewAttachesListener) (conversationViewAttachesListener=$conversationViewAttachesListener)")
        conversationViewAttachesListeners.add(conversationViewAttachesListener)
    }

    @UiThread
    fun removeConversationViewAttachesListenerListener(conversationViewAttachesListener: ConversationViewAttachesListener) {
        Timber.d("removeConversationViewAttachesListenerListener(ConversationViewAttachesListener) (conversationViewAttachesListener=$conversationViewAttachesListener)")
        conversationViewAttachesListeners.remove(conversationViewAttachesListener)
    }

    @UiThread
    fun addNlpListener(nlpListener: NlpListener) {
        Timber.d("addNlpListener(NlpListener) (nlpListener=$nlpListener)")
        nlpListeners.add(nlpListener)
    }

    @UiThread
    fun removeNlpListener(nlpListener: NlpListener) {
        Timber.d("removeNlpListener(NlpListener) (nlpListener=$nlpListener)")
        nlpListeners.remove(nlpListener)
    }

    @UiThread
    fun addTtsListener(ttsListener: TtsListener) {
        Timber.d("addTtsListener(TtsListener) (ttsListener=$ttsListener)")
        ttsListeners.add(ttsListener)
    }

    @UiThread
    fun removeTtsListener(ttsListener: TtsListener) {
        Timber.d("removeTtsListener(TtsListener) (ttsListener=$ttsListener)")
        ttsListeners.remove(ttsListener)
    }

    @UiThread
    fun addWakeupWordListener(wakeupWordListener: WakeupWordListener) {
        Timber.d("addWakeupWordListener(WakeupWordListener) (wakeupWordListener=$wakeupWordListener)")
        wakeUpWordListeners.add(wakeupWordListener)
    }

    @UiThread
    fun removeWakeupWordListener(wakeupWordListener: WakeupWordListener) {
        Timber.d("removeWakeupWordListener(WakeupWordListener) (wakeupWordListener=$wakeupWordListener)")
        wakeUpWordListeners.remove(wakeupWordListener)
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
        Timber.d("saveLocation(String) (name=$name)")
        sdkService?.let {
            try {
                return it.saveLocation(name)
            } catch (e: RemoteException) {
                Timber.e(e, "saveLocation(String)")
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
        Timber.d("deleteLocation(String) (name=$name)")
        sdkService?.let {
            try {
                return it.deleteLocation(name)
            } catch (e: RemoteException) {
                Timber.e(e, "deleteLocation(String)")
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
            Timber.d("getLocations()")
            sdkService?.let {
                try {
                    return it.locations
                } catch (e: RemoteException) {
                    Timber.e(e, "getLocations()")
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
        Timber.d("goTo(String) (location=$location)")
        require(!TextUtils.isEmpty(location)) { "Location can not be null or empty." }
        sdkService?.let {
            try {
                it.goTo(location)
            } catch (e: RemoteException) {
                Timber.e(e, "goTo(String) error.")
            }
        }
    }

    @UiThread
    fun addOnGoToLocationStatusChangedListener(listener: OnGoToLocationStatusChangedListener) {
        Timber.d("addOnGoToLocationStatusChangedListener(OnGoToLocationStatusChangedListener) (listener=$listener)")
        onGoToLocationStatusChangeListeners.add(listener)
    }

    @UiThread
    fun removeOnGoToLocationStatusChangedListener(listener: OnGoToLocationStatusChangedListener) {
        Timber.d("removeOnGoToLocationStatusChangedListener(OnGoToLocationStatusChangedListener) (listener=$listener)")
        onGoToLocationStatusChangeListeners.remove(listener)
    }

    @UiThread
    fun addOnLocationsUpdatedListener(listener: OnLocationsUpdatedListener) {
        Timber.d("addOnLocationsUpdatedListener(OnLocationsUpdatedListener) (listener=$listener)")
        onLocationsUpdatedListeners.add(listener)
    }

    @UiThread
    fun removeOnLocationsUpdateListener(listener: OnLocationsUpdatedListener) {
        Timber.d("removeOnLocationsUpdateListener(OnLocationsUpdatedListener) (listener=$listener)")
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
        Timber.d("beWithMe()")
        sdkService?.let {
            try {
                it.beWithMe()
            } catch (e: RemoteException) {
                Timber.e(e, "beWithMe()")
            }
        }
    }

    /**
     * Request robot to stop any movement.
     */
    fun stopMovement() {
        Timber.d("stopMovement()")
        sdkService?.let {
            try {
                it.stopMovement()
            } catch (e: RemoteException) {
                Timber.e(e, "stopMovement()")
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
        Timber.d("skidJoy(float, float) (x=$x, y=$y)")
        sdkService?.let {
            try {
                it.skidJoy(x, y)
            } catch (e: RemoteException) {
                Timber.e(e, "skidJoy(float, float) (x=$x, y=$y)")
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
        Timber.d("turnBy(int) (degrees=$degrees)")
        sdkService?.let {
            try {
                it.turnBy(degrees, 1.0f)
            } catch (e: RemoteException) {
                Timber.e(e, "turnBy(int) (degrees=$degrees)")
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
        Timber.d("turnBy(int) (degrees=$degrees)")
        sdkService?.let {
            try {
                it.tiltAngle(degrees, 1.0f)
            } catch (e: RemoteException) {
                Timber.e(e, "turnBy(int) (degrees=$degrees)")
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
        Timber.d("tiltBy(int) (degrees=$degrees)")
        sdkService?.let {
            try {
                it.tiltBy(degrees, 1.0f)
            } catch (e: RemoteException) {
                Timber.e(e, "tiltBy(int) (degrees=$degrees)")
            }

        }
    }

    @UiThread
    fun addOnBeWithMeStatusChangedListener(listener: OnBeWithMeStatusChangedListener) {
        Timber.d("addOnBeWithMeStatusChangedListener(OnBeWithMeStatusChangedListener) (listener=$listener)")
        onBeWithMeStatusChangeListeners.add(listener)
    }

    @UiThread
    fun removeOnBeWithMeStatusChangedListener(listener: OnBeWithMeStatusChangedListener) {
        Timber.d("removeOnBeWithMeStatusChangedListener(OnBeWithMeStatusChangedListener) (listener=$listener)")
        onBeWithMeStatusChangeListeners.remove(listener)
    }

    @UiThread
    fun addOnConstraintBeWithStatusChangedListener(listener: OnConstraintBeWithStatusChangedListener) {
        Timber.d("addOnConstraintBeWithStatusChangedListener(OnWelcomingModeStatusChangedListener) (listener=$listener)")
        onConstraintBeWithStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnConstraintBeWithStatusChangedListener(listener: OnConstraintBeWithStatusChangedListener) {
        Timber.d("removeOnConstraintBeWithStatusChangedListener(OnWelcomingModeStatusChangedListener) (listener=$listener)")
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
            Timber.d("getAdminInfo()")
            sdkService?.let {
                try {
                    return it.adminInfo
                } catch (e: RemoteException) {
                    Timber.e(e, "getAdminInfo() error.")
                }

            }
            return null
        }

    /**
     * Fetch all the temi contacts.
     */
    val allContact: List<UserInfo>
        get() {
            Timber.d("getAllContact()")
            val contactList = ArrayList<UserInfo>()
            sdkService?.let {
                try {
                    contactList.addAll(it.allContacts)
                } catch (e: RemoteException) {
                    Timber.e(e, "getAllContacts() error.")
                }

            }
            return contactList
        }

    /**
     * Fetch recent calls.
     */
    val recentCalls: List<RecentCallModel>
        get() {
            Timber.d("getRecentCalls()")
            sdkService?.let {
                try {
                    return it.recentCalls
                } catch (e: RemoteException) {
                    Timber.e(e, "getRecentCalls() error.")
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
        Timber.d("startTelepresence(String, String) (displayName=$displayName, peerId=$peerId)")
        sdkService?.let {
            try {
                return it.startTelepresence(displayName, peerId)
            } catch (e: RemoteException) {
                Timber.e("startTelepresence(String, String) (displayName=$displayName, peerId=$peerId)")
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
        Timber.d("addOnTelepresenceStatusChangedListener(OnTelepresenceStatusChangedListener) (listener=$listener)")
        onTelepresenceStatusChangedListeners.add(listener)
    }

    /**
     * Stop listening for Telepresence Status changes.
     *
     * @param listener The listener you added before.
     */
    fun removeOnTelepresenceStatusChangedListener(listener: OnTelepresenceStatusChangedListener) {
        Timber.d("removeOnTelepresenceStatusChangedListener(OnTelepresenceStatusChangedListener) (listener=$listener)")
        onTelepresenceStatusChangedListeners.remove(listener)
    }

    /**
     * Start listening for user information updates.
     *
     * @param listener The listener you want to add.
     */
    fun addOnUsersUpdatedListener(listener: OnUsersUpdatedListener) {
        Timber.d("addOnUsersUpdatedListener(OnUsersUpdatedListener) (listener=$listener)")
        onUsersUpdatedListeners.add(listener)
    }

    /**
     * Stop listening for user information updates.
     *
     * @param listener The listener you added before.
     */
    fun removeOnUsersUpdatedListener(listener: OnUsersUpdatedListener) {
        Timber.d("removeOnUsersUpdatedListener(OnUsersUpdatedListener) (listener=$listener)")
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
            Timber.d("serialNumber()")
            sdkService?.let {
                try {
                    return it.serialNumber
                } catch (e: RemoteException) {
                    Timber.e(e, "getSerialNumber()")
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
            Timber.d("getBatteryData()")
            sdkService?.let {
                try {
                    return it.batteryData
                } catch (e: RemoteException) {
                    Timber.e(e, "getBatteryData() error.")
                }

            }
            return null
        }

    /**
     * Go to the App list of Launcher.
     */
    fun showAppList() {
        Timber.d("showAppList()")
        sdkService?.let {
            try {
                it.showAppList()
            } catch (e: RemoteException) {
                Timber.e(e, "showAppList() error.")
            }

        }
    }

    /**
     * Show the top bar of Launcher.
     */
    fun showTopBar() {
        Timber.d("showTopBar()")
        sdkService?.let {
            try {
                it.showTopBar()
            } catch (e: RemoteException) {
                Timber.e(e, "showTopBar() error.")
            }

        }
    }

    /**
     * Hide the top bar of Launcher.
     */
    fun hideTopBar() {
        Timber.d("hideTopBar()")
        sdkService?.let {
            try {
                it.hideTopBar()
            } catch (e: RemoteException) {
                Timber.e(e, "hideTopBar() error.")
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
                    Timber.e(e, "togglePrivacyMode() error.")
                }
            }
        }
        get() {
            Timber.d("getPrivacyModeState()")
            sdkService?.let {
                try {
                    return it.privacyModeState
                } catch (e: RemoteException) {
                    Timber.e(e, "getPrivacyModeState() error.")
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
        Timber.d("addOnPrivacyModeStateChangedListener(OnPrivacyModeChangedListener) (listener=$listener)")
        onPrivacyModeStateChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnPrivacyModeStateChangedListener(listener: OnPrivacyModeChangedListener) {
        Timber.d("removeOnPrivacyModeStateChangedListener(OnPrivacyModeChangedListener) (listener=$listener)")
        onPrivacyModeStateChangedListeners.remove(listener)
    }

    @UiThread
    fun addOnBatteryStatusChangedListener(listener: OnBatteryStatusChangedListener) {
        Timber.d("addOnBatteryStatusChangedListener(OnBatteryStatusChangedListener) (listener=$listener)")
        onBatteryStatusChangedListeners.add(listener)
    }

    @UiThread
    fun removeOnBatteryStatusChangedListener(listener: OnBatteryStatusChangedListener) {
        Timber.d("removeOnBatteryStatusChangedListener(OnBatteryStatusChangedListener) (listener=$listener)")
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
        Timber.d("toggleWakeup() - disable = $disable")
        sdkService?.let {
            if (isMetaDataKiosk) {
                try {
                    it.toggleWakeup(disable)
                } catch (e: RemoteException) {
                    Timber.e(e, "toggleWakeup() error.")
                }

            } else {
                Timber.e("toggleWakeup() Wakeup can only be toggled in Kiosk Mode")
            }
        }
    }

    /**
     * Toggle the visibility of the navigation billboard when you perform goTo commands
     *
     * @param hide set true to hide the billboard or false to display it
     */
    fun toggleNavigationBillboard(hide: Boolean) {
        Timber.d("toggleNavigationBillboard() - $hide")
        sdkService?.let {
            if (isMetaDataKiosk) {
                try {
                    it.toggleNavigationBillboard(hide)
                } catch (e: RemoteException) {
                    Timber.e(e, "toggleNavigationBillboard() error.")
                }
            } else {
                Timber.e("toggleNavigationBillboard() Billboard can only be toggled in Kiosk Mode")
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
                    Timber.e(e, "Sdk service is null")
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
        Timber.d("addOnUserInteractionChangedListener(OnUserInteractionChangedListener) (listener=$listener)")
        onUserInteractionChangedListeners.add(listener);
    }

    @UiThread
    fun removeOnUserInteractionChangedListener(listener: OnUserInteractionChangedListener) {
        Timber.d("removeOnUserInteractionChangedListener(OnUserInteractionChangedListener) (listener=$listener)")
        onUserInteractionChangedListeners.remove(listener);
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
