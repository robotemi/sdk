// ISdkServiceCallback.aidl
package com.robotemi.sdk;

import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.notification.NotificationCallback;
import com.robotemi.sdk.UserInfo;
import com.robotemi.sdk.calls.CallState;
import com.robotemi.sdk.voice.NlpResult;
import com.robotemi.sdk.voice.TtsRequest;
import com.robotemi.sdk.BatteryData;

interface ISdkServiceCallback {

    boolean onWakeupWord(in String wakeupWord, in int direction);

    boolean onTtsStatusChanged(in TtsRequest ttsRequest);

    boolean onNlpCompleted(in NlpResult nlpResult);

    void onActivityStreamPublish(in ActivityStreamPublishMessage message);

    void onPlayButtonClicked(boolean play);

    void onNextButtonClicked();

    void onBackButtonClicked();

    void onTrackBarChanged(int position);

    void onNotificationBtnClicked(in NotificationCallback notificationCallback);

    boolean onConversationViewAttaches(boolean isAttached);

    boolean hasActiveNlpListeners();

    boolean onBeWithMeStatusChanged(in String status);

    boolean onGoToLocationStatusChanged(in String location, in String status, in int descriptionId, in String description);

    boolean onTelepresenceStatusChanged(in CallState callState);

    boolean onLocationsUpdated(in List<String> locations);

    boolean onUserUpdated(in UserInfo user);

    boolean onPrivacyModeStateChanged(in boolean state);

    boolean onBatteryStatusChanged(in BatteryData batteryData);

    boolean onConstraintBeWithStatusChanged(in boolean isContraint);

    boolean onUserInteractionStatusChanged(in boolean isInteracting);
}
