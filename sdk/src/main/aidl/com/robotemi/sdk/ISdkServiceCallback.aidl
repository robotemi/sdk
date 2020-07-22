// ISdkServiceCallback.aidl
package com.robotemi.sdk;

import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.notification.NotificationCallback;
import com.robotemi.sdk.UserInfo;
import com.robotemi.sdk.telepresence.CallState;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.model.CallEventModel;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.model.DetectionData;
import com.robotemi.sdk.face.ContactModel;
import com.robotemi.sdk.exception.SdkException;

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

    boolean onConstraintBeWithStatusChanged(in boolean isContraint);

    boolean onUserInteractionStatusChanged(in boolean isInteracting);

    boolean onBatteryStatusChanged(in BatteryData batteryData);

    boolean onPrivacyModeStateChanged(in boolean state);

    boolean onDetectionStateChanged(in int state);

    boolean onAsrResult(in String asrText);

    boolean onTelepresenceEventChanged(in CallEventModel callEventModel);

    boolean onRequestPermissionResult(in String permission, int grantResult, int requestCode);

    boolean onDistanceToLocationChanged(in Map distances);

    boolean onCurrentPositionChanged(in Position position);

    boolean onSequencePlayStatusChanged(int status);

    boolean onRobotLifted(boolean isRobotLifted, String reason);

    boolean onDetectionDataChanged(in DetectionData detectionData);

    boolean onFaceRecognized(in List<ContactModel> contactModelList);

    boolean onSdkError(in SdkException sdkException);
}
