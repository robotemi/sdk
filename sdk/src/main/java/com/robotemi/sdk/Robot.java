package com.robotemi.sdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.robotemi.sdk.activitystream.ActivityStreamObject;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.activitystream.ActivityStreamUtils;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnTelepresenceStatusChangedListener;
import com.robotemi.sdk.listeners.OnUsersUpdatedListener;
import com.robotemi.sdk.mediabar.AidlMediaBarController;
import com.robotemi.sdk.mediabar.MediaBarData;
import com.robotemi.sdk.model.RecentCallModel;
import com.robotemi.sdk.notification.AlertNotification;
import com.robotemi.sdk.notification.NormalNotification;
import com.robotemi.sdk.notification.NotificationCallback;
import com.robotemi.sdk.telepresence.CallState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.UiThread;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@SuppressLint("LogNotTimber")
public class Robot {

    public static final String DEFAULT_ACTION = "skill.default";

    public static final String PAUSE = "reserved.pauseMediaBar";

    public static final String STOP = "reserved.stop";

    public static final String RESUME = "reserved.resume";

    private static final String TAG = "Robot";

    private static Robot instance;

    @NonNull
    private final ApplicationInfo applicationInfo;

    @NonNull
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    @NonNull
    private final HashMap<String, NotificationListener> listenersMap = new HashMap<>();

    @NonNull
    private final Set<ConversationViewAttachesListener> conversationViewAttachesListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<TtsListener> ttsListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<NlpListener> nlpListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<WakeupWordListener> wakeUpWordListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<OnRobotReadyListener> onRobotReadyListeners = new HashSet<>();

    @NonNull
    private final Set<OnBeWithMeStatusChangedListener> onBeWithMeStatusChangeListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<OnGoToLocationStatusChangedListener> onGoToLocationStatusChangeListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<OnTelepresenceStatusChangedListener> onTelepresenceStatusChangedListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<OnLocationsUpdatedListener> onLocationsUpdatedListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private final Set<OnUsersUpdatedListener> onUsersUpdatedListeners = new CopyOnWriteArraySet<>();

    @NonNull
    private AidlMediaBarController mediaBar = new AidlMediaBarController(null);

    @Nullable
    private MediaButtonListener mediaButtonListener;

    @Nullable
    private ISdkService sdkService;

    @Nullable
    private ActivityStreamPublishListener activityStreamPublishListener;

    @NonNull
    private final ISdkServiceCallback sdkServiceCallback = new ISdkServiceCallback.Stub() {

        @Override
        public boolean onWakeupWord(@NonNull final String wakeupWord) {
            Log.d(TAG, "onWakeupWord(String) (wakeupWord=" + wakeupWord + ", thread=" + Thread.currentThread().getName() + ")");
            if (wakeUpWordListeners.size() > 0) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (WakeupWordListener wakeupWordListener : wakeUpWordListeners) {
                            wakeupWordListener.onWakeupWord(wakeupWord);
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean onTtsStatusChanged(@NonNull final TtsRequest ttsRequest) {
            Log.d(TAG, "onTtsStatusChanged(TtsRequest) (ttsRequest=" + ttsRequest + ", thread=" + Thread.currentThread().getName() + ")");
            if (ttsListeners.size() > 0) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (TtsListener ttsListener : ttsListeners) {
                            ttsListener.onTtsStatusChanged(ttsRequest);
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean onNlpCompleted(@NonNull final NlpResult nlpResult) {
            Log.d(TAG, "onNlpCompleted(NlpResult) (nlpResult=" + nlpResult + ", thread=" + Thread.currentThread().getName() + ")");
            if (nlpListeners.size() > 0) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (NlpListener nlpListener : nlpListeners) {
                            nlpListener.onNlpCompleted(nlpResult);
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public void onActivityStreamPublish(ActivityStreamPublishMessage message) {
            Log.d(TAG, "onActivityStreamPublish: " + message.success());
            if (activityStreamPublishListener != null) {
                activityStreamPublishListener.onPublish(message);
            }
        }

        @Override
        public void onPlayButtonClicked(final boolean play) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mediaButtonListener != null) {
                        mediaButtonListener.onPlayButtonClicked(play);
                    } else {
                        Log.w(TAG, "mediaButtonListener=null");
                    }
                }
            });
        }

        @Override
        public void onNextButtonClicked() {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mediaButtonListener != null) {
                        mediaButtonListener.onNextButtonClicked();
                    } else {
                        Log.w(TAG, "mediaButtonListener=null");
                    }
                }
            });
        }

        @Override
        public void onBackButtonClicked() {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mediaButtonListener != null) {
                        mediaButtonListener.onBackButtonClicked();
                    } else {
                        Log.w(TAG, "mediaButtonListener=null");
                    }
                }
            });
        }

        @Override
        public void onTrackBarChanged(final int position) {
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mediaButtonListener != null) {
                        mediaButtonListener.onTrackBarChanged(position);
                    } else {
                        Log.w(TAG, "mediaButtonListener=null");
                    }
                }
            });
        }

        @Override
        public void onNotificationBtnClicked(
                final NotificationCallback notificationCallback) {
            Log.w(TAG, "onNotificationBtnClicked");
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    NotificationListener notificationListener = listenersMap
                            .get(notificationCallback.getNotificationId());
                    if (notificationListener != null) {
                        notificationListener.onNotificationBtnClicked(notificationCallback.getEvent());
                        listenersMap.remove(notificationListener);
                    }
                }
            });
        }

        @Override
        public boolean onConversationViewAttaches(final boolean isAttached) {
            Log.d(TAG, "onConversationViewAttaches(boolean) (isAttached=" + isAttached + ", thread=" + Thread.currentThread().getName() + ")");
            if (conversationViewAttachesListeners.size() > 0) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (ConversationViewAttachesListener conversationViewAttachesListener : conversationViewAttachesListeners) {
                            conversationViewAttachesListener.onConversationAttaches(isAttached);
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean hasActiveNlpListeners() {
            final boolean hasActiveNlpListener = !nlpListeners.isEmpty();
            Log.d(TAG, "hasActiveNlpListeners() (hasActiveNlpListeners=" + hasActiveNlpListener + ")");
            return hasActiveNlpListener;
        }

        @Override
        public boolean onBeWithMeStatusChanged(@NonNull final String status) {
            Log.d(TAG, "onBeWithMeStatusChanged(String) (status=" + status + ")");
            if (onBeWithMeStatusChangeListeners.size() > 0) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (OnBeWithMeStatusChangedListener listener : onBeWithMeStatusChangeListeners) {
                            listener.onBeWithMeStatusChanged(status);
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean onGoToLocationStatusChanged(@NonNull final String location, @NonNull final String status) {
            Log.d(TAG, "onGoToLocationStatusChanged(String, String) (location=" + location + ", status=" + status + ")");
            if (!onGoToLocationStatusChangeListeners.isEmpty()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (OnGoToLocationStatusChangedListener listener : onGoToLocationStatusChangeListeners) {
                            listener.onGoToLocationStatusChanged(location, status);
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean onTelepresenceStatusChanged(final CallState callState) {
            Log.d(TAG, "onTelepresenceStatusChanged(CallState) (callState= " + callState + ")");
            if (!onTelepresenceStatusChangedListeners.isEmpty()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (OnTelepresenceStatusChangedListener listener : onTelepresenceStatusChangedListeners) {
                            if (listener != null && callState.getSessionId().equals(listener.sessionId)) {
                                listener.onTelepresenceStatusChanged(callState);
                            }
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean onLocationsUpdated(final List<String> locations) {
            Log.d(TAG, "onLocationsUpdated(List<String>) (locations size= " + locations.size() + ")");
            if (!onLocationsUpdatedListeners.isEmpty()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (OnLocationsUpdatedListener listener : onLocationsUpdatedListeners) {
                            if (listener != null) {
                                listener.onLocationsUpdated(locations);
                            }
                        }
                    }
                });
                return true;
            }
            return false;
        }

        @Override
        public boolean onUserUpdated(final UserInfo user) {
            Log.d(TAG, "onUserUpdated(UserInfo) (user= " + user + ")");
            if (!onUsersUpdatedListeners.isEmpty()) {
                uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        for (OnUsersUpdatedListener listener : onUsersUpdatedListeners) {
                            final boolean isValidListener = listener != null
                                    && (listener.userIds == null
                                    || listener.userIds.isEmpty()
                                    || listener.userIds.contains(user.getUserId()));
                            if (isValidListener) {
                                listener.onUserUpdated(user);
                            }
                        }
                    }
                });
                return true;
            }
            return false;
        }
    };

    private Robot(@NonNull final Context context) {
        final Context appContext = context.getApplicationContext();
        final String packageName = appContext.getPackageName();
        final PackageManager packageManager = appContext.getPackageManager();
        try {
            this.applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public static Robot getInstance() {
        if (instance == null) {
            synchronized (Robot.class) {
                if (instance == null) {
                    final Context context = TemiSdkContentProvider.context;
                    if (context == null) {
                        throw new NullPointerException("context == null");
                    }
                    instance = new Robot(context);
                }
            }
        }
        return instance;
    }

    @UiThread
    public void onStart(@NonNull final ActivityInfo activityInfo) {
        Log.d(TAG, "onStart(ActivityInfo) (activityInfo=" + activityInfo + ")");
        if (sdkService != null) {
            try {
                sdkService.onStart(activityInfo);
            } catch (RemoteException e) {
                Log.e(TAG, "onStart(ActivityInfo) - Binder invocation exception.", e);
            }
        } else {
            Log.w(TAG, "onStart(ActivityInfo) - sdkService=null");
        }
    }

    /**
     * Stops currently processed TTS request and empty the queue.
     */
    public void cancelAllTtsRequests() {
        if (sdkService != null) {
            try {
                sdkService.cancelAll();
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to invoke remote call cancelAllTtsRequest()", e);
            }
        }
    }

    @RestrictTo(LIBRARY)
    @UiThread
    void setSdkService(@Nullable ISdkService sdkService) {
        Log.d(TAG, "setSdkService(ISdkService)");
        this.sdkService = sdkService;
        mediaBar = new AidlMediaBarController(sdkService);
        registerCallback();

        for (OnRobotReadyListener onRobotReadyListener : onRobotReadyListeners) {
            onRobotReadyListener.onRobotReady(sdkService != null);
        }
    }

    @UiThread
    private void registerCallback() {
        Log.d(TAG, "registerCallback()");
        if (sdkService != null) {
            try {
                sdkService.register(applicationInfo, sdkServiceCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Remote invocation error.", e);
            }
        } else {
            Log.w(TAG, "sdkService=null");
        }
    }

    public void speak(@NonNull final TtsRequest ttsRequest) {
        try {
            if (sdkService != null) {
                ttsRequest.setPackageName(applicationInfo.packageName);
                sdkService.speak(ttsRequest);
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to invoke remote call speak()", e);
        }
    }

    @UiThread
    public void addConversationViewAttachesListenerListener(@NonNull final ConversationViewAttachesListener conversationViewAttachesListener) {
        Log.d(TAG, "addConversationViewAttachesListenerListener(ConversationViewAttachesListener) (conversationViewAttachesListener=" + conversationViewAttachesListener + ")");
        conversationViewAttachesListeners.add(conversationViewAttachesListener);
    }

    @UiThread
    public void removeConversationViewAttachesListenerListener(@NonNull final ConversationViewAttachesListener conversationViewAttachesListener) {
        Log.d(TAG, "removeConversationViewAttachesListenerListener(ConversationViewAttachesListener) (conversationViewAttachesListener=" + conversationViewAttachesListener + ")");
        conversationViewAttachesListeners.remove(conversationViewAttachesListener);
    }

    @UiThread
    public void addNlpListener(@NonNull final NlpListener nlpListener) {
        Log.d(TAG, "addNlpListener(NlpListener) (nlpListener=" + nlpListener + ")");
        nlpListeners.add(nlpListener);
    }

    @UiThread
    public void removeNlpListener(@NonNull NlpListener nlpListener) {
        Log.d(TAG, "removeNlpListener(NlpListener) (nlpListener=" + nlpListener + ")");
        nlpListeners.remove(nlpListener);
    }

    @UiThread
    public void addTtsListener(@NonNull final TtsListener ttsListener) {
        Log.d(TAG, "addTtsListener(TtsListener) (ttsListener=" + ttsListener + ")");
        ttsListeners.add(ttsListener);
    }

    @UiThread
    public void removeTtsListener(@NonNull final TtsListener ttsListener) {
        Log.d(TAG, "removeTtsListener(TtsListener) (ttsListener=" + ttsListener + ")");
        ttsListeners.remove(ttsListener);
    }

    @UiThread
    public void addWakeupWordListener(@NonNull final WakeupWordListener wakeupWordListener) {
        Log.d(TAG, "addWakeupWordListener(WakeupWordListener) (wakeupWordListener=" + wakeupWordListener + ")");
        wakeUpWordListeners.add(wakeupWordListener);
    }

    @UiThread
    public void removeWakeupWordListener(@NonNull final WakeupWordListener wakeupWordListener) {
        Log.d(TAG, "removeWakeupWordListener(WakeupWordListener) (wakeupWordListener=" + wakeupWordListener + ")");
        wakeUpWordListeners.remove(wakeupWordListener);
    }

    @UiThread
    public void addOnBeWithMeStatusChangedListener(@NonNull final OnBeWithMeStatusChangedListener listener) {
        Log.d(TAG, "addOnBeWithMeStatusChangedListener(OnBeWithMeStatusChangedListener) (listener=" + listener + ")");
        onBeWithMeStatusChangeListeners.add(listener);
    }

    @UiThread
    public void removeOnBeWithMeStatusChangedListener(@NonNull final OnBeWithMeStatusChangedListener listener) {
        Log.d(TAG, "removeOnBeWithMeStatusChangedListener(OnBeWithMeStatusChangedListener) (listener=" + listener + ")");
        onBeWithMeStatusChangeListeners.remove(listener);
    }

    @UiThread
    public void addOnGoToLocationStatusChangedListener(@NonNull final OnGoToLocationStatusChangedListener listener) {
        Log.d(TAG, "addOnGoToLocationStatusChangedListener(OnGoToLocationStatusChangedListener) (listener=" + listener + ")");
        onGoToLocationStatusChangeListeners.add(listener);
    }

    @UiThread
    public void removeOnGoToLocationStatusChangedListener(@NonNull final OnGoToLocationStatusChangedListener listener) {
        Log.d(TAG, "removeOnGoToLocationStatusChangedListener(OnGoToLocationStatusChangedListener) (listener=" + listener + ")");
        onGoToLocationStatusChangeListeners.remove(listener);
    }

    @UiThread
    public void addOnLocationsUpdatedListener(@NonNull final OnLocationsUpdatedListener listener) {
        Log.d(TAG, "addOnLocationsUpdatedListener(OnLocationsUpdatedListener) (listener=" + listener + ")");
        onLocationsUpdatedListeners.add(listener);
    }

    @UiThread
    public void removeOnLocationsUpdateListener(@NonNull final OnLocationsUpdatedListener listener) {
        Log.d(TAG, "removeOnLocationsUpdateListener(OnLocationsUpdatedListener) (listener=" + listener + ")");
        onLocationsUpdatedListeners.remove(listener);
    }

    @UiThread
    public void addOnRobotReadyListener(@NonNull final OnRobotReadyListener onRobotReadyListener) {
        Log.d(TAG, "addOnRobotReadyListener(OnRobotReadyListener)");
        onRobotReadyListeners.add(onRobotReadyListener);

        onRobotReadyListener.onRobotReady(isReady());
    }

    public boolean isReady() {
        return sdkService != null;
    }

    @UiThread
    public void removeOnRobotReadyListener(@NonNull final OnRobotReadyListener onRobotReadyListener) {
        Log.d(TAG, "removeOnRobotReadyListener(OnRobotReadyListener)");
        onRobotReadyListeners.remove(onRobotReadyListener);
    }

    public void setActivityStreamPublishListener(@Nullable ActivityStreamPublishListener activityStreamPublishListener) {
        this.activityStreamPublishListener = activityStreamPublishListener;
    }

    public void shareActivityObject(final ActivityStreamObject activityStreamObject) throws RemoteException {
        if (sdkService != null) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    ActivityStreamUtils.handleActivityStreamObject(activityStreamObject);
                    try {
                        sdkService.shareActivityStreamObject(activityStreamObject);
                    } catch (RemoteException e) {
                        Log.e(TAG, "Sdk service is null");
                    }
                }
            });
        } else {
            throw new RemoteException("Sdk service is null.");
        }
    }

    public void setMediaButtonListener(@NonNull final MediaButtonListener mediaButtonListener) {
        this.mediaButtonListener = mediaButtonListener;
    }

    public void removeMediaButtonListener() {
        mediaButtonListener = null;
    }

    public void updateMediaBar(@NonNull MediaBarData mediaBarData) throws RemoteException {
        mediaBarData.setPackageName(applicationInfo.packageName);
        mediaBar.updateMediaBar(mediaBarData);
    }

    public void pauseMediaBar() throws RemoteException {
        mediaBar.pauseMediaBar();
    }

    public void setMediaPlaying(boolean isPlaying) throws RemoteException {
        mediaBar.setMediaPlaying(isPlaying, applicationInfo.packageName);
    }

    public void showNormalNotification(NormalNotification notification) throws RemoteException {
        if (sdkService != null) {
            sdkService.showNormalNotification(notification);
        } else {
            throw new RemoteException("Sdk service is null.");
        }
    }

    public void showAlertNotification(AlertNotification notification, NotificationListener notificationListener) throws RemoteException {
        if (sdkService != null) {
            sdkService.showAlertNotification(notification);
            listenersMap.put(notification.getNotificationId(), notificationListener);
        } else {
            throw new RemoteException("Sdk service is null.");
        }
    }

    public void removeAlertNotification(AlertNotification notification) throws RemoteException {
        if (sdkService != null) {
            sdkService.removeAlertNotification(notification);
        } else {
            throw new RemoteException("Sdk service is null.");
        }
    }

    /**
     * Request to lock contexts even if skill screen is dismissed.
     * Useful for services running in the background without UI.
     *
     * @param contextsToLock - List of contexts names to lock.
     */
    public void lockContexts(@NonNull final List<String> contextsToLock) {
        Log.d(TAG, "lockContexts(List<String>) (contextsToLock=" + contextsToLock + ")");
        if (sdkService != null) {
            try {
                sdkService.lockContexts(contextsToLock);
            } catch (RemoteException e) {
                Log.e(TAG, "lockContexts(List<String>) error.", e);
            }
        }
    }

    /**
     * Release previously locked contexts. See {@link #lockContexts(List)}.
     *
     * @param contextsToRelease - List of contexts names to release.
     */
    public void releaseContexts(@NonNull final List<String> contextsToRelease) {
        Log.d(TAG, "releaseContexts(List<String>) (contextsToRelease=" + contextsToRelease + ")");
        if (sdkService != null) {
            try {
                sdkService.releaseContexts(contextsToRelease);
            } catch (RemoteException e) {
                Log.e(TAG, "releaseContexts(List<String>) error.", e);
            }
        }
    }

    /**
     * Send robot to previously saved location.
     *
     * @param location - Saved location name.
     */
    public void goTo(@NonNull final String location) {
        Log.d(TAG, "goTo(String) (location=" + location + ")");
        if (sdkService != null) {
            try {
                sdkService.goTo(location);
            } catch (RemoteException e) {
                Log.e(TAG, "goTo(String) error.", e);
            }
        }
    }

    /**
     * Retrieve list of previously saved locations.
     *
     * @return List of saved locations.
     */
    public List<String> getLocations() {
        Log.d(TAG, "getLocations()");
        if (sdkService != null) {
            try {
                return sdkService.getLocations();
            } catch (RemoteException e) {
                Log.e(TAG, "getLocations()", e);
            }
        }
        return Collections.emptyList();
    }

    /**
     * Save location.
     *
     * @param - Location name.
     */
    public void saveLocation(@NonNull final String name) {
        Log.d(TAG, "saveLocation(String) (name=" + name + ")");
        if (sdkService != null) {
            try {
                sdkService.saveLocation(name);
            } catch (RemoteException e) {
                Log.e(TAG, "saveLocation(String)", e);
            }
        }
    }

    /**
     * Request robot to follow user around.
     * See {@link OnBeWithMeStatusChangedListener} to listen for status changes.
     */
    public void beWithMe() {
        Log.d(TAG, "beWithMe()");
        if (sdkService != null) {
            try {
                sdkService.beWithMe();
            } catch (RemoteException e) {
                Log.e(TAG, "beWithMe()", e);
            }
        }
    }

    /**
     * Joystick commands.
     *
     * @param x From -1 to 1.
     * @param y From -1 to 1.
     */
    public void skidJoy(final float x, final float y) {
        Log.d(TAG, "skidJoy(float, float) (x=" + x + ", y=" + y + ")");
        if (sdkService != null) {
            try {
                sdkService.skidJoy(x, y);
            } catch (RemoteException e) {
                Log.e(TAG, "skidJoy(float, float) (x=" + x + ", y=" + y + ")");
            }
        }
    }

    public void turnBy(final int azimuth, final float speed) {
        Log.d(TAG, "turnBy(int, float) (azimuth=" + azimuth + ", speed=" + speed + ")");
        if (sdkService != null) {
            try {
                sdkService.turnBy(azimuth, speed);
            } catch (RemoteException e) {
                Log.e(TAG, "turnBy(int, float) (azimuth=" + azimuth + ", speed=" + speed + ")");
            }
        }
    }

    public void tiltAngle(final int degrees, final float speed) {
        Log.d(TAG, "turnBy(int, float) (degrees=" + degrees + ", speed=" + speed + ")");
        if (sdkService != null) {
            try {
                sdkService.tiltAngle(degrees, speed);
            } catch (RemoteException e) {
                Log.e(TAG, "turnBy(int, float) (degrees=" + degrees + ", speed=" + speed + ")");
            }
        }
    }

    public void tiltBy(final int degrees, final float speed) {
        Log.d(TAG, "tiltBy(int, float) (degrees=" + degrees + ", speed=" + speed + ")");
        if (sdkService != null) {
            try {
                sdkService.tiltBy(degrees, speed);
            } catch (RemoteException e) {
                Log.e(TAG, "tiltBy(int, float) (degrees=" + degrees + ", speed=" + speed + ")");
            }
        }
    }

    /**
     * @return the sessionId of Telepresence call
     */
    public String startTelepresence(String displayName, String peerId) {
        Log.d(TAG, "startTelepresence(String, String) (displayName=" + displayName + ", peerId=" + peerId + ")");
        if (sdkService != null) {
            try {
                return sdkService.startTelepresence(displayName, peerId);
            } catch (RemoteException e) {
                Log.e(TAG, "startTelepresence(String, String) (displayName=" + displayName + ", peerId=" + peerId + ")");
            }
        }
        return "";
    }

    public void addOnTelepresenceStatusChangedListener(OnTelepresenceStatusChangedListener listener) {
        Log.d(TAG, "addOnTelepresenceStatusChangedListener(OnTelepresenceStatusChangedListener) (listener=" + listener + ")");
        onTelepresenceStatusChangedListeners.add(listener);
    }

    public void removeOnTelepresenceStatusChangedListener(OnTelepresenceStatusChangedListener listener) {
        Log.d(TAG, "removeOnTelepresenceStatusChangedListener(OnTelepresenceStatusChangedListener) (listener=" + listener + ")");
        onTelepresenceStatusChangedListeners.remove(listener);
    }

    public void addOnUsersUpdatedListener(OnUsersUpdatedListener listener) {
        Log.d(TAG, "addOnUsersUpdatedListener(OnUsersUpdatedListener) (listener=" + listener + ")");
        onUsersUpdatedListeners.add(listener);
    }

    public void removeOnUsersUpdatedListener(OnUsersUpdatedListener listener) {
        Log.d(TAG, "removeOnUsersUpdatedListener(OnUsersUpdatedListener) (listener=" + listener + ")");
        onUsersUpdatedListeners.remove(listener);
    }

    @Nullable
    public UserInfo getAdminInfo() {
        Log.d(TAG, "getAdminInfo()");
        if (sdkService != null) {
            try {
                return sdkService.getAdminInfo();
            } catch (RemoteException e) {
                Log.e(TAG, "getAdminInfo() error.", e);
            }
        }
        return null;
    }

    @NonNull
    public List<UserInfo> getAllContact() {
        Log.d(TAG, "getAllContact()");
        List<UserInfo> contactList = new ArrayList<>();
        if (sdkService != null) {
            try {
                contactList.addAll(sdkService.getAllContacts());
            } catch (RemoteException e) {
                Log.e(TAG, "getAllContacts() error.", e);
            }
        }
        return contactList;
    }

    public void showAppList() {
        Log.d(TAG, "showAppList()");
        if (sdkService != null) {
            try {
                sdkService.showAppList();
            } catch (RemoteException e) {
                Log.e(TAG, "showAppList() error.", e);
            }
        }
    }

    @NonNull
    public List<RecentCallModel> getRecentCalls() {
        Log.d(TAG, "getRecentCalls()");
        if (sdkService != null) {
            try {
                return sdkService.getRecentCalls();
            } catch (RemoteException e) {
                Log.e(TAG, "getRecentCalls() error.", e);
            }
        }
        return new ArrayList<>();
    }

    @NonNull
    public void showTopBar() {
        Log.d(TAG, "showTopBar()");
        if (sdkService != null) {
            try {
                sdkService.showTopBar();
            } catch (RemoteException e) {
                Log.e(TAG, "showTopBar() error.", e);
            }
        }
    }

    @NonNull
    public void hideTopBar() {
        Log.d(TAG, "hideTopBar()");
        if (sdkService != null) {
            try {
                sdkService.hideTopBar();
            } catch (RemoteException e) {
                Log.e(TAG, "hideTopBar() error.", e);
            }
        }
    }

    public interface WakeupWordListener {
        void onWakeupWord(String wakeupWord);
    }

    public interface TtsListener {
        void onTtsStatusChanged(TtsRequest ttsRequest);
    }

    public interface NlpListener {
        void onNlpCompleted(NlpResult nlpResult);
    }

    public interface ActivityStreamPublishListener {
        void onPublish(ActivityStreamPublishMessage message);
    }

    public interface MediaButtonListener {

        void onPlayButtonClicked(boolean play);

        void onNextButtonClicked();

        void onBackButtonClicked();

        void onTrackBarChanged(int position);
    }

    public interface NotificationListener {
        void onNotificationBtnClicked(int btnNumber);
    }

    public interface ConversationViewAttachesListener {
        void onConversationAttaches(boolean isAttached);
    }
}
