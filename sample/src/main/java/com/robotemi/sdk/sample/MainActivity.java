package com.robotemi.sdk.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.robotemi.sdk.BatteryData;
import com.robotemi.sdk.MediaObject;
import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamObject;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.face.ContactModel;
import com.robotemi.sdk.face.OnFaceRecognizedListener;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnConstraintBeWithStatusChangedListener;
import com.robotemi.sdk.listeners.OnDetectionDataChangedListener;
import com.robotemi.sdk.listeners.OnDetectionStateChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRequestPermissionResultListener;
import com.robotemi.sdk.listeners.OnRobotLiftedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnTelepresenceEventChangedListener;
import com.robotemi.sdk.listeners.OnUserInteractionChangedListener;
import com.robotemi.sdk.model.CallEventModel;
import com.robotemi.sdk.model.DetectionData;
import com.robotemi.sdk.navigation.listener.OnCurrentPositionChangedListener;
import com.robotemi.sdk.navigation.listener.OnDistanceToLocationChangedListener;
import com.robotemi.sdk.navigation.model.Position;
import com.robotemi.sdk.navigation.model.SafetyLevel;
import com.robotemi.sdk.navigation.model.SpeedLevel;
import com.robotemi.sdk.permission.Permission;
import com.robotemi.sdk.sequence.OnSequencePlayStatusChangedListener;
import com.robotemi.sdk.sequence.SequenceModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener,
        OnConstraintBeWithStatusChangedListener,
        OnDetectionStateChangedListener,
        Robot.AsrListener,
        OnTelepresenceEventChangedListener,
        OnRequestPermissionResultListener,
        OnDistanceToLocationChangedListener,
        OnCurrentPositionChangedListener,
        OnSequencePlayStatusChangedListener,
        OnRobotLiftedListener,
        OnDetectionDataChangedListener,
        OnUserInteractionChangedListener,
        OnFaceRecognizedListener {

    public static final String ACTION_HOME_WELCOME = "home.welcome", ACTION_HOME_DANCE = "home.dance", ACTION_HOME_SLEEP = "home.sleep";
    public static final String HOME_BASE_LOCATION = "home base";
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    private static final int REQUEST_CODE_NORMAL = 0;
    private static final int REQUEST_CODE_FACE_START = 1;
    private static final int REQUEST_CODE_FACE_STOP = 2;
    private static final int REQUEST_CODE_MAP = 3;
    private static final int REQUEST_CODE_SEQUENCE_FETCH_ALL = 4;
    private static final int REQUEST_CODE_SEQUENCE_PLAY = 5;
    private static final int REQUEST_CODE_START_DETECTION_WITH_DISTANCE = 6;

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            "com.robotemi.permission.map"
    };

    private EditText etSpeak, etSaveLocation, etGoTo, etDistance;

    private List<String> locations;

    private Robot robot;

    private CustomAdapter mAdapter;

    private TextView tvLog;

    /**
     * Hiding keyboard after every button press
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * Checks if the app has permission to write to device storage
     * If the app does not has permission then the user will be prompted to grant permissions
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    /**
     * Setting up all the event listeners
     */
    @Override
    protected void onStart() {
        super.onStart();
        robot.addOnRobotReadyListener(this);
        robot.addNlpListener(this);
        robot.addOnBeWithMeStatusChangedListener(this);
        robot.addOnGoToLocationStatusChangedListener(this);
        robot.addConversationViewAttachesListenerListener(this);
        robot.addWakeupWordListener(this);
        robot.addTtsListener(this);
        robot.addOnLocationsUpdatedListener(this);
        robot.addOnConstraintBeWithStatusChangedListener(this);
        robot.addOnDetectionStateChangedListener(this);
        robot.addAsrListener(this);
        robot.addOnDistanceToLocationChangedListener(this);
        robot.addOnCurrentPositionChangedListener(this);
        robot.addOnSequencePlayStatusChangedListener(this);
        robot.addOnRobotLiftedListener(this);
        robot.addOnDetectionDataChangedListener(this);
        robot.addOnUserInteractionChangedListener(this);
        robot.showTopBar();
    }

    /**
     * Removing the event listeners upon leaving the app.
     */
    @Override
    protected void onStop() {
        super.onStop();
        robot.removeOnRobotReadyListener(this);
        robot.removeNlpListener(this);
        robot.removeOnBeWithMeStatusChangedListener(this);
        robot.removeOnGoToLocationStatusChangedListener(this);
        robot.removeConversationViewAttachesListenerListener(this);
        robot.removeWakeupWordListener(this);
        robot.removeTtsListener(this);
        robot.removeOnLocationsUpdateListener(this);
        robot.removeOnDetectionStateChangedListener(this);
        robot.removeAsrListener(this);
        robot.removeOnDistanceToLocationChangedListener(this);
        robot.removeOnCurrentPositionChangedListener(this);
        robot.removeOnSequencePlayStatusChangedListener(this);
        robot.removeOnRobotLiftedListener(this);
        robot.removeOnDetectionDataChangedListener(this);
        robot.addOnUserInteractionChangedListener(this);
        robot.stopMovement();
    }

    /**
     * Places this application in the top bar for a quick access shortcut.
     */
    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
                // Robot.getInstance().onStart() method may change the visibility of top bar.
                robot.onStart(activityInfo);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initViews();
        verifyStoragePermissions(this);
        robot = Robot.getInstance(); // get an instance of the robot in order to begin using its features.
        robot.addOnRequestPermissionResultListener(this);
        robot.addOnTelepresenceEventChangedListener(this);
        robot.addOnFaceRecognizedListener(this);

    }

    @Override
    protected void onDestroy() {
        robot.removeOnRequestPermissionResultListener(this);
        robot.removeOnTelepresenceEventChangedListener(this);
        robot.removeOnFaceRecognizedListener(this);
        super.onDestroy();
    }

    public void initViews() {
        etSpeak = findViewById(R.id.etSpeak);
        etSaveLocation = findViewById(R.id.etSaveLocation);
        etGoTo = findViewById(R.id.etGoTo);
        etDistance = findViewById(R.id.etDistance);
        tvLog = findViewById(R.id.tvLog);
        tvLog.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * Have the robot speak while displaying what is being said.
     */
    public void speak(View view) {
        TtsRequest ttsRequest = TtsRequest.create(etSpeak.getText().toString().trim(), true);
        robot.speak(ttsRequest);
        hideKeyboard();
    }

    /**
     * This is an example of saving locations.
     */
    public void saveLocation(View view) {
        String location = etSaveLocation.getText().toString().toLowerCase().trim();
        boolean result = robot.saveLocation(location);
        if (result) {
            robot.speak(TtsRequest.create("I've successfully saved the " + location + " location.", true));
        } else {
            robot.speak(TtsRequest.create("Saved the " + location + " location failed.", true));
        }
        hideKeyboard();
    }

    /**
     * goTo checks that the location sent is saved then goes to that location.
     */
    public void goTo(View view) {
        for (String location : robot.getLocations()) {
            if (location.equals(etGoTo.getText().toString().toLowerCase().trim())) {
                robot.goTo(etGoTo.getText().toString().toLowerCase().trim());
                hideKeyboard();
            }
        }
    }

    /**
     * stopMovement() is used whenever you want the robot to stop any movement
     * it is currently doing.
     */
    public void stopMovement(View view) {
        robot.stopMovement();
        robot.speak(TtsRequest.create("And so I have stopped", true));
    }

    /**
     * Simple follow me example.
     */
    public void followMe(View view) {
        robot.beWithMe();
        hideKeyboard();
    }

    /**
     * Manually navigate the robot with skidJoy, tiltAngle, turnBy and tiltBy.
     * skidJoy moves the robot exactly forward for about a second. It controls both
     * the linear and angular velocity. Float numbers must be between -1.0 and 1.0
     */
    public void skidJoy(View view) {
        long t = System.currentTimeMillis();
        long end = t + 1000;
        while (System.currentTimeMillis() < end) {
            robot.skidJoy(1F, 0F);
        }
    }

    /**
     * tiltAngle controls temi's head by specifying which angle you want
     * to tilt to and at which speed.
     */
    public void tiltAngle(View view) {
        robot.tiltAngle(23);
    }

    /**
     * turnBy allows for turning the robot around in place. You can specify
     * the amount of degrees to turn by and at which speed.
     */
    public void turnBy(View view) {
        robot.turnBy(180);
    }

    /**
     * tiltBy is used to tilt temi's head from its current position.
     */
    public void tiltBy(View view) {
        robot.tiltBy(70);
    }

    /**
     * getBatteryData can be used to return the current battery status.
     */
    public void getBatteryData(View view) {
        BatteryData batteryData = robot.getBatteryData();
        if (batteryData == null) {
            printLog("getBatteryData()", "batteryData is null");
            return;
        }
        if (batteryData.isCharging()) {
            TtsRequest ttsRequest = TtsRequest.create(batteryData.getBatteryPercentage() + " percent battery and charging.", true);
            robot.speak(ttsRequest);
        } else {
            TtsRequest ttsRequest = TtsRequest.create(batteryData.getBatteryPercentage() + " percent battery and not charging.", true);
            robot.speak(ttsRequest);
        }
    }

    /**
     * Display the saved locations in a dialog
     */
    public void savedLocationsDialog(View view) {
        hideKeyboard();
        locations = robot.getLocations();
        mAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_selectable_list_item, locations);
        AlertDialog.Builder versionsDialog = new AlertDialog.Builder(MainActivity.this);
        versionsDialog.setTitle("Saved Locations: (Click to delete the location)");
        versionsDialog.setPositiveButton("OK", null);
        versionsDialog.setAdapter(mAdapter, null);
        AlertDialog dialog = versionsDialog.create();
        dialog.getListView().setOnItemClickListener((parent, view1, position, id) -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Delete location \"" + mAdapter.getItem(position) + "\" ?");
            builder.setPositiveButton("No thanks", (dialog1, which) -> {

            });
            builder.setNegativeButton("Yes", (dialog1, which) -> {
                String location = mAdapter.getItem(position);
                if (location == null) {
                    return;
                }
                boolean result = robot.deleteLocation(location);
                if (result) {
                    locations.remove(position);
                    robot.speak(TtsRequest.create(location + "delete successfully!", false));
                    mAdapter.notifyDataSetChanged();
                } else {
                    robot.speak(TtsRequest.create(location + "delete failed!", false));
                }
            });
            Dialog deleteDialog = builder.create();
            deleteDialog.show();
        });
        dialog.show();
    }

    /**
     * When adding the Nlp Listener to your project you need to implement this method
     * which will listen for specific intents and allow you to respond accordingly.
     * <p>
     * See AndroidManifest.xml for reference on adding each intent.
     */
    @Override
    public void onNlpCompleted(NlpResult nlpResult) {
        //do something with nlp result. Base the action specified in the AndroidManifest.xml
        Toast.makeText(MainActivity.this, nlpResult.action, Toast.LENGTH_SHORT).show();
        switch (nlpResult.action) {
            case ACTION_HOME_WELCOME:
                robot.tiltAngle(23);
                break;

            case ACTION_HOME_DANCE:
                long t = System.currentTimeMillis();
                long end = t + 5000;
                while (System.currentTimeMillis() < end) {
                    robot.skidJoy(0F, 1F);
                }
                break;

            case ACTION_HOME_SLEEP:
                robot.goTo(HOME_BASE_LOCATION);
                break;
        }
    }

    /**
     * callOwner is an example of how to use telepresence to call an individual.
     */
    public void callOwner(View view) {
        if (robot.getAdminInfo() == null) {
            printLog("callOwner()", "adminInfo is null.");
            return;
        }
        robot.startTelepresence(robot.getAdminInfo().getName(), robot.getAdminInfo().getUserId());
    }

    /**
     * publishToActivityStream takes an image stored in the resources folder
     * and uploads it to the mobile application under the Activities tab.
     */
    public void publishToActivityStream(View view) {
        ActivityStreamObject activityStreamObject;
        if (robot != null) {
            final String fileName = "puppy.png";
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.puppy);
            File puppiesFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName);
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(puppiesFile);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            activityStreamObject = ActivityStreamObject.builder()
                    .activityType(ActivityStreamObject.ActivityType.PHOTO)
                    .title("Puppy")
                    .media(MediaObject.create(MediaObject.MimeType.IMAGE, puppiesFile))
                    .build();

            try {
                robot.shareActivityObject(activityStreamObject);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            robot.speak(TtsRequest.create("Uploading Image", false));
        }
    }

    public void hideTopBar(View view) {
        robot.hideTopBar();
    }

    public void showTopBar(View view) {
        robot.showTopBar();
    }

    @Override
    public void onWakeupWord(@NotNull String wakeupWord, int direction) {
        // Do anything on wakeup. Follow, go to location, or even try creating dance moves.
        printLog("onWakeupWord", wakeupWord + ", " + direction);
    }

    @Override
    public void onTtsStatusChanged(@NotNull TtsRequest ttsRequest) {
        // Do whatever you like upon the status changing. after the robot finishes speaking
    }

    @Override
    public void onBeWithMeStatusChanged(String status) {
        //  When status changes to "lock" the robot recognizes the user and begin to follow.
        switch (status) {
            case OnBeWithMeStatusChangedListener.ABORT:
                // do something i.e. speak
                robot.speak(TtsRequest.create("Abort", false));
                break;

            case OnBeWithMeStatusChangedListener.CALCULATING:
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case OnBeWithMeStatusChangedListener.LOCK:
                robot.speak(TtsRequest.create("Lock", false));
                break;

            case OnBeWithMeStatusChangedListener.SEARCH:
                robot.speak(TtsRequest.create("search", false));
                break;

            case OnBeWithMeStatusChangedListener.START:
                robot.speak(TtsRequest.create("Start", false));
                break;

            case OnBeWithMeStatusChangedListener.TRACK:
                robot.speak(TtsRequest.create("Track", false));
                break;
        }
    }

    @Override
    public void onGoToLocationStatusChanged(@NotNull String location, String status, int descriptionId, @NotNull String description) {
        printLog("GoToStatusChanged", "status=" + status + ", descriptionId=" + descriptionId + ", description=" + description);
        robot.speak(TtsRequest.create(description, false));
        switch (status) {
            case OnGoToLocationStatusChangedListener.START:
                robot.speak(TtsRequest.create("Starting", false));
                break;

            case OnGoToLocationStatusChangedListener.CALCULATING:
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case OnGoToLocationStatusChangedListener.GOING:
                robot.speak(TtsRequest.create("Going", false));
                break;

            case OnGoToLocationStatusChangedListener.COMPLETE:
                robot.speak(TtsRequest.create("Completed", false));
                break;

            case OnGoToLocationStatusChangedListener.ABORT:
                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }

    @Override
    public void onConversationAttaches(boolean isAttached) {
        //Do something as soon as the conversation is displayed.
        printLog("onConversationAttaches", "isAttached:" + isAttached);
    }

    @Override
    public void onPublish(@NotNull ActivityStreamPublishMessage message) {
        //After the activity stream finished publishing (photo or otherwise).
        //Do what you want based on the message returned.
        robot.speak(TtsRequest.create("Uploaded.", false));
    }

    @Override
    public void onLocationsUpdated(@NotNull List<String> locations) {
        //Saving or deleting a location will update the list.
        Toast.makeText(this, "Locations updated :\n" + locations, Toast.LENGTH_LONG).show();
    }

    public void disableWakeup(View view) {
        robot.toggleWakeup(true);
    }

    public void enableWakeup(View view) {
        robot.toggleWakeup(false);
    }

    public void toggleNavBillboard(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.toggleNavigationBillboard(!robot.isNavigationBillboardDisabled());
    }

    @Override
    public void onConstraintBeWithStatusChanged(boolean isConstraint) {
        printLog("onConstraintBeWith", "status = " + isConstraint);
    }

    @Override
    public void onDetectionStateChanged(int state) {
        printLog("onDetectionStateChanged: state = " + state);
        if (state == OnDetectionStateChangedListener.DETECTED) {
            robot.constraintBeWith();
        } else if (state == OnDetectionStateChangedListener.IDLE) {
            robot.stopMovement();
        }
    }

    /**
     * If you want to cover the voice flow in Launcher OS,
     * please add following meta-data to AndroidManifest.xml.
     * <pre>
     * <meta-data
     *     android:name="com.robotemi.sdk.metadata.KIOSK"
     *     android:value="TRUE" />
     *
     * <meta-data
     *     android:name="com.robotemi.sdk.metadata.OVERRIDE_NLU"
     *     android:value="TRUE" />
     * <pre>
     * And also need to select this App as the Kiosk Mode App in Settings > Kiosk Mode.
     *
     * @param asrResult The result of the ASR after waking up temi.
     */
    @Override
    public void onAsrResult(final @NonNull String asrResult) {
        printLog("onAsrResult", "asrResult = " + asrResult);
        if (asrResult.equalsIgnoreCase("Hello")) {
            robot.askQuestion("Hello, I'm temi, what can I do for you?");
        } else if (asrResult.equalsIgnoreCase("Play music")) {
            robot.speak(TtsRequest.create("Okay, please enjoy.", false));
            robot.finishConversation();
            playMusic();
        } else if (asrResult.equalsIgnoreCase("Play movie")) {
            robot.speak(TtsRequest.create("Okay, please enjoy.", false));
            robot.finishConversation();
            playMovie();
        } else if (asrResult.toLowerCase().contains("follow me")) {
            robot.finishConversation();
            robot.beWithMe();
        } else if (asrResult.toLowerCase().contains("go to home base")) {
            robot.finishConversation();
            robot.goTo("home base");
        } else {
            robot.askQuestion("Sorry I can't understand you, could you please ask something else?");
        }
    }

    private void playMovie() {
        // Play movie...
        printLog("onAsrResult", "Play movie...");
    }

    private void playMusic() {
        // Play music...
        printLog("onAsrResult", "Play music...");
    }

    public void privacyModeOn(View view) {
        robot.setPrivacyMode(true);
        Toast.makeText(this, robot.getPrivacyMode() + "", Toast.LENGTH_SHORT).show();
    }

    public void privacyModeOff(View view) {
        robot.setPrivacyMode(false);
        Toast.makeText(this, robot.getPrivacyMode() + "", Toast.LENGTH_SHORT).show();
    }

    public void getPrivacyModeState(View view) {
        Toast.makeText(this, robot.getPrivacyMode() + "", Toast.LENGTH_SHORT).show();
    }

    public void isHardButtonsEnabled(View view) {
        Toast.makeText(this, robot.isHardButtonsDisabled() + "", Toast.LENGTH_SHORT).show();
    }

    public void disableHardButtons(View view) {
        robot.setHardButtonsDisabled(true);
        Toast.makeText(this, robot.isHardButtonsDisabled() + "", Toast.LENGTH_SHORT).show();
    }

    public void enableHardButtons(View view) {
        robot.setHardButtonsDisabled(false);
        Toast.makeText(this, robot.isHardButtonsDisabled() + "", Toast.LENGTH_SHORT).show();
    }

    public void getOSVersion(View view) {
        String osVersion = String.format("LauncherOs: %s, RoboxVersion: %s", robot.getLauncherVersion(), robot.getRoboxVersion());
        Toast.makeText(this, osVersion, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTelepresenceEventChanged(@NotNull CallEventModel callEventModel) {
        printLog("onTelepresenceEvent", callEventModel.toString());
        if (callEventModel.getType() == CallEventModel.TYPE_INCOMING) {
            Toast.makeText(this, "Incoming call", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Outgoing call", Toast.LENGTH_LONG).show();
        }
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onRequestPermissionResult(@NotNull Permission permission, int grantResult, int requestCode) {
        String log = String.format("Permission: %s, grantResult: %d", permission.getValue(), grantResult);
        Toast.makeText(this, log, Toast.LENGTH_SHORT).show();
        printLog("onRequestPermission", log);
        if (grantResult == Permission.DENIED) {
            return;
        }
        switch (permission) {
            case FACE_RECOGNITION:
                if (requestCode == REQUEST_CODE_FACE_START) {
                    robot.startFaceRecognition();
                } else if (requestCode == REQUEST_CODE_FACE_STOP) {
                    robot.stopFaceRecognition();
                }
                break;
            case SEQUENCE:
                if (requestCode == REQUEST_CODE_SEQUENCE_FETCH_ALL) {
                    getAllSequences();
                } else if (requestCode == REQUEST_CODE_SEQUENCE_PLAY) {
                    playFirstSequence();
                }
                break;
            case MAP:
                if (requestCode == REQUEST_CODE_MAP) {
                    getMap();
                }
                break;
            case SETTINGS:
                if (requestCode == REQUEST_CODE_START_DETECTION_WITH_DISTANCE) {
                    startDetectionWishDistance();
                }
                break;
        }
    }

    public void requestFace(View view) {
        if (robot.checkSelfPermission(Permission.FACE_RECOGNITION) == Permission.GRANTED) {
            Toast.makeText(this, "You already had FACE_RECOGNITION permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.FACE_RECOGNITION);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestMap(View view) {
        if (robot.checkSelfPermission(Permission.MAP) == Permission.GRANTED) {
            Toast.makeText(this, "You already had MAP permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.MAP);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestSettings(View view) {
        if (robot.checkSelfPermission(Permission.SETTINGS) == Permission.GRANTED) {
            Toast.makeText(this, "You already had SETTINGS permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.SETTINGS);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestSequence(View view) {
        if (robot.checkSelfPermission(Permission.SEQUENCE) == Permission.GRANTED) {
            Toast.makeText(this, "You already had SEQUENCE permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Permission> permissions = new ArrayList<>();
        permissions.add(Permission.SEQUENCE);
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void requestAll(View view) {
        List<Permission> permissions = new ArrayList<>();
        for (Permission permission : Permission.values()) {
            if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
                Toast.makeText(this, String.format("You already had '%s' permission.", permission.toString()), Toast.LENGTH_SHORT).show();
                continue;
            }
            permissions.add(permission);
        }
        robot.requestPermissions(permissions, REQUEST_CODE_NORMAL);
    }

    public void startFaceRecognition(View view) {
        if (requestPermissionIfNeeded(Permission.FACE_RECOGNITION, REQUEST_CODE_FACE_START)) {
            return;
        }
        robot.startFaceRecognition();
    }

    public void stopFaceRecognition(View view) {
        robot.stopFaceRecognition();
    }

    public void setGoToSpeed(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        List<String> speedLevels = new ArrayList<>();
        speedLevels.add(SpeedLevel.HIGH.getValue());
        speedLevels.add(SpeedLevel.MEDIUM.getValue());
        speedLevels.add(SpeedLevel.SLOW.getValue());
        final CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_selectable_list_item, speedLevels);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select Go To Speed Level")
                .setAdapter(adapter, null)
                .create();
        dialog.getListView().setOnItemClickListener((parent, view1, position, id) -> {
            robot.setGoToSpeed(SpeedLevel.valueToEnum(Objects.requireNonNull(adapter.getItem(position))));
            Toast.makeText(MainActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void setGoToSafety(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        List<String> safetyLevel = new ArrayList<>();
        safetyLevel.add(SafetyLevel.HIGH.getValue());
        safetyLevel.add(SafetyLevel.MEDIUM.getValue());
        final CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_selectable_list_item, safetyLevel);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Select Go To Safety Level")
                .setAdapter(adapter, null)
                .create();
        dialog.getListView().setOnItemClickListener((parent, view1, position, id) -> {
            robot.setNavigationSafety(SafetyLevel.valueToEnum(Objects.requireNonNull(adapter.getItem(position))));
            Toast.makeText(MainActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void toggleTopBadge(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setTopBadgeEnabled(!robot.isTopBadgeEnabled());
    }

    public void toggleDetectionMode(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setDetectionModeOn(!robot.isDetectionModeOn());
    }

    public void toggleAutoReturn(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setAutoReturnOn(!robot.isAutoReturnOn());
    }

    public void toggleTrackUser(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        robot.setTrackUserOn(!robot.isTrackUserOn());
    }

    public void getVolume(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL))
            Toast.makeText(this, robot.getVolume() + "", Toast.LENGTH_SHORT).show();
    }

    public void setVolume(View view) {
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_NORMAL)) {
            return;
        }
        List<String> volumeList = new ArrayList<>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        final CustomAdapter adapter = new CustomAdapter(this, android.R.layout.simple_selectable_list_item, volumeList);
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Set Volume")
                .setAdapter(adapter, null)
                .create();
        dialog.getListView().setOnItemClickListener((parent, view1, position, id) -> {
            robot.setVolume(Integer.parseInt(Objects.requireNonNull(adapter.getItem(position))));
            Toast.makeText(MainActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void requestToBeKioskApp(View view) {
        if (robot.isSelectedKioskApp()) {
            Toast.makeText(this, this.getString(R.string.app_name) + " was the selected Kiosk App.", Toast.LENGTH_SHORT).show();
            return;
        }
        robot.requestToBeKioskApp();
    }

    @SuppressLint("DefaultLocale")
    public void startDetectionModeWithDistance(View view) {
        hideKeyboard();
        if (requestPermissionIfNeeded(Permission.SETTINGS, REQUEST_CODE_START_DETECTION_WITH_DISTANCE)) {
            return;
        }
        startDetectionWishDistance();
    }

    private void startDetectionWishDistance() {
        String distanceStr = etDistance.getText().toString();
        if (distanceStr.isEmpty()) distanceStr = "0";
        try {
            float distance = Float.parseFloat(distanceStr);
            robot.setDetectionModeOn(true, distance);
            printLog("Start detection mode with distance: " + distance);
        } catch (Exception e) {
            printLog("startDetectionModeWithDistance", e.getMessage());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDistanceToLocationChanged(@NotNull Map<String, Float> distances) {
        for (String location : distances.keySet()) {
            printLog("onDistanceToLocation", "location:" + location + ", distance:" + distances.get(location));
        }
    }

    @Override
    public void onCurrentPositionChanged(@NotNull Position position) {
        printLog("onCurrentPosition", position.toString());
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onSequencePlayStatusChanged(int status) {
        printLog(String.format("onSequencePlayStatus status:%d", status));
        if (status == OnSequencePlayStatusChangedListener.ERROR
                || status == OnSequencePlayStatusChangedListener.IDLE) {
            robot.showTopBar();
        }
    }

    @Override
    public void onRobotLifted(boolean isRobotLifted) {
        printLog("onRobotLifted", "isRobotLifted: " + isRobotLifted);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        hideKeyboard();
        return super.dispatchTouchEvent(ev);
    }

    @CheckResult
    private boolean requestPermissionIfNeeded(Permission permission, int requestCode) {
        if (robot.checkSelfPermission(permission) == Permission.GRANTED) {
            return false;
        }
        robot.requestPermissions(Collections.singletonList(permission), requestCode);
        return true;
    }

    @Override
    public void onDetectionDataChanged(@NotNull DetectionData detectionData) {
        printLog("onDetectionDataChanged", detectionData.toString());
    }

    @Override
    public void onUserInteraction(boolean isInteracting) {
        printLog("onUserInteraction", "isInteracting:" + isInteracting);
    }

    public void getAllSequences(View view) {
        if (requestPermissionIfNeeded(Permission.SEQUENCE, REQUEST_CODE_SEQUENCE_FETCH_ALL)) {
            return;
        }
        getAllSequences();
    }

    private volatile List<SequenceModel> allSequences;

    private void getAllSequences() {
        new Thread(() -> {
            allSequences = robot.getAllSequences();
            runOnUiThread(() -> {
                for (SequenceModel sequenceModel : allSequences) {
                    if (sequenceModel == null) {
                        continue;
                    }
                    printLog(sequenceModel.toString());
                }
            });
        }).start();
    }

    public void playFirstSequence(View view) {
        if (requestPermissionIfNeeded(Permission.SEQUENCE, REQUEST_CODE_SEQUENCE_PLAY)) {
            return;
        }
        playFirstSequence();
    }

    private void playFirstSequence() {
        if (allSequences != null && !allSequences.isEmpty()) {
            robot.playSequence(allSequences.get(0).getId());
        }
    }

    public void getMap(View view) {
        if (requestPermissionIfNeeded(Permission.MAP, REQUEST_CODE_MAP)) {
            return;
        }
        getMap();
    }

    private void getMap() {
        startActivity(new Intent(this, MapActivity.class));
    }

    @Override
    public void onFaceRecognized(@NotNull List<ContactModel> contactModelList) {
        for (ContactModel contactModel : contactModelList) {
            printLog("onFaceRecognized", contactModel.toString());
        }
    }

    private void printLog(String msg) {
        printLog("", msg);
    }

    private void printLog(String tag, String msg) {
        Log.d(tag, msg);
        tvLog.setGravity(Gravity.BOTTOM);
        tvLog.append(String.format("%s %s\n", "· ", msg));
    }

    public void btnClearLog(View view) {
        tvLog.setText("");
    }

    public void startNlu(View view) {
        EditText editText = findViewById(R.id.etNlu);
        robot.startNlu(editText.getText().toString());
    }
}
