package com.robotemi.sdk.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener {

    private Robot robot;
    public EditText etSpeak, etSaveLocation, etGoTo;
    List<String> locations;

    /*
        Setting up all the event listeners
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
    }

    /*
        Removing the event listeners upon leaving the app.
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
    }

    /*
        Places this application in the top bar for a quick access shortcut.
     */

    @Override
    public void onRobotReady(boolean isReady) {
        if (isReady) {
            try {
                final ActivityInfo activityInfo = getPackageManager().getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
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
        robot = Robot.getInstance(); // get an instance of the robot in order to begin using its features.
    }

    public void initViews(){
        etSpeak = findViewById(R.id.etSpeak);
        etSaveLocation = findViewById(R.id.etSaveLocation);
        etGoTo = findViewById(R.id.etGoTo);
    }

    /*
        Have the robot speak while displaying what is being said.
     */

    public void speak(View view) {
        TtsRequest ttsRequest = TtsRequest.create(etSpeak.getText().toString().trim(),true);
        robot.speak(ttsRequest);
        hideKeyboard(MainActivity.this);
    }

    /*
        This is an example of saving locations.
     */

    public void saveLocation(View view){
        String location = etSaveLocation.getText().toString().toLowerCase().trim();
        robot.saveLocation(location);
        robot.speak(TtsRequest.create("I've successfully saved the " + location + " location.", true));
        hideKeyboard(MainActivity.this);
    }

    /*
        goTo checks that the location sent is saved then goes to that location.
     */

    public void goTo(View view) {
        for (String location : robot.getLocations()){
            if (location.equals(etGoTo.getText().toString().toLowerCase().trim())) {
                robot.goTo(etGoTo.getText().toString().toLowerCase().trim());
                hideKeyboard(MainActivity.this);
            }
        }
    }

    /*
        Simple follow me example.
     */

    public void followMe(View view) {
        robot.beWithMe();
        hideKeyboard(MainActivity.this);
    }

    /*
        Manually navigate the robot with skidJoy, tiltAngle, turnBy and tiltBy.
     */

    public void skidJoy(View view) {
        robot.skidJoy(-1.0F, 1.0F);
    }

    public void tiltAngle(View view) {
        robot.tiltAngle(23, 5.3F);
    }

    public void turnBy(View view) {
        robot.turnBy(90, 6.2F);
    }

    public void tiltBy(View view) {
        robot.tiltBy(70, 1.2F);
    }

    /*
        Hiding keyboard after every button press
     */

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /*
        Display the saved locations in a dialog
     */

    public void savedLocationsDialog(View view) {
        hideKeyboard(MainActivity.this);
        locations = robot.getLocations();
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, android.R.layout.simple_selectable_list_item, locations);
        AlertDialog.Builder versionsDialog = new AlertDialog.Builder(MainActivity.this);
        versionsDialog.setTitle("Saved Locations:");
        versionsDialog.setPositiveButton("OK", null);
        versionsDialog.setAdapter(customAdapter, null);
        AlertDialog dialog = versionsDialog.create();
        dialog.show();
    }

    @Override
    public void onNlpCompleted(NlpResult nlpResult) {
        //do something with nlp result. Base the action on what you get back
        Toast.makeText(MainActivity.this, nlpResult.action, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWakeupWord(String wakeupWord) {
       // Do anything on wakeup. Follow, go to location, or even try creating dance moves.
    }

    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {

        // Do whatever you like upon the status changing. after the robot finishes speaking
        // Toast.makeText(this, "speech: " + ttsRequest.getSpeech() + "\nstatus:" + ttsRequest.getStatus(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBeWithMeStatusChanged(String status) {
      //  When status changes to "lock" the robot recognizes the user and begin to follow.
        switch(status) {
            case "abort":
                // do something i.e. speak
                break;

            case "calculating":
                break;

            case "lock":
                break;

            case "search":
                break;

            case "start":
                break;

            case "track":
                break;
        }
    }


    @Override
    public void onGoToLocationStatusChanged(String location, String status) {
        switch (status) {
            case "start":
                robot.speak(TtsRequest.create("Starting", false));
                break;

            case "calculating":
                robot.speak(TtsRequest.create("Calculating", false));
                break;

            case "going":
                robot.speak(TtsRequest.create("Going", false));
                break;

            case "complete":
                robot.speak(TtsRequest.create("Completed", false));
                break;

            case "abort":
                robot.speak(TtsRequest.create("Cancelled", false));
                break;
        }
    }

    @Override
    public void onConversationAttaches(boolean isAttached) {
        if (isAttached) {
            //Do something as soon as the conversation is displayed.
        }
    }

    @Override
    public void onPublish(ActivityStreamPublishMessage message) {
        //After the activity stream finished publishing (photo or otherwise).
        //Do what you want based on the message returned.
    }

    @Override
    public void onLocationsUpdated(List<String> locations) {

        //Saving or deleting a location will update the list.

        Toast.makeText(this, "Locations updated :\n" + locations, Toast.LENGTH_LONG).show();
    }
}
