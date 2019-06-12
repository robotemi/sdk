// ISdkServiceCallback.aidl
package com.robotemi.sdk;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.notification.NotificationCallback;
import com.robotemi.sdk.telepresence.CallState;
import com.robotemi.sdk.UserInfo;

interface ISdkServiceCallback {

    boolean onWakeupWord(in String wakeupWord);

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

    boolean onGoToLocationStatusChanged(in String location, in String status);

    boolean onTelepresenceStatusChanged(in CallState callState);

    boolean onLocationsUpdated(in List<String> locations);

    boolean onUserUpdated(in UserInfo user);
}
