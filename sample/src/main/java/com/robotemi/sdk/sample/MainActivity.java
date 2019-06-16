package com.robotemi.sdk.sample;

import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.robotemi.sdk.NlpResult;
import com.robotemi.sdk.Robot;
import com.robotemi.sdk.TtsRequest;
import com.robotemi.sdk.UserInfo;
import com.robotemi.sdk.activitystream.ActivityStreamPublishMessage;
import com.robotemi.sdk.listeners.OnBeWithMeStatusChangedListener;
import com.robotemi.sdk.listeners.OnGoToLocationStatusChangedListener;
import com.robotemi.sdk.listeners.OnLocationsUpdatedListener;
import com.robotemi.sdk.listeners.OnRobotReadyListener;
import com.robotemi.sdk.listeners.OnTelepresenceStatusChangedListener;
import com.robotemi.sdk.listeners.OnUsersUpdatedListener;
import com.robotemi.sdk.sample.R;
import com.robotemi.sdk.telepresence.CallState;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        Robot.NlpListener,
        OnRobotReadyListener,
        Robot.ConversationViewAttachesListener,
        Robot.WakeupWordListener,
        Robot.ActivityStreamPublishListener,
        Robot.MediaButtonListener,
        Robot.NotificationListener,
        Robot.TtsListener,
        OnBeWithMeStatusChangedListener,
        OnGoToLocationStatusChangedListener,
        OnLocationsUpdatedListener {

    private Robot robot;
    public EditText etSpeak, etSaveLocation, etGoTo;
    List<String> locations;

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
        initViews();
        robot = Robot.getInstance();
        //String userInfo = robot.getAdminInfo().getPicUrl();
      //  Toast.makeText(this, userInfo, Toast.LENGTH_SHORT).show();
    }

    public void initViews(){
        etSpeak = findViewById(R.id.etSpeak);
        etSaveLocation = findViewById(R.id.etSaveLocation);
        etGoTo = findViewById(R.id.etGoTo);
    }

    public void saveLocation(View view){
        String location = etSaveLocation.getText().toString().trim();
        robot.saveLocation(location);
        robot.speak(TtsRequest.create("I've successfully saved the " + location + " location.", true));
        hideKeyboard(MainActivity.this);
    }

    public void goTo(View view) {
        robot.goTo(etGoTo.getText().toString().trim());
        hideKeyboard(MainActivity.this);
    }

    public void speak(View view) {
        TtsRequest ttsRequest = TtsRequest.create(etSpeak.getText().toString().trim(),true);
        robot.speak(ttsRequest);
        hideKeyboard(MainActivity.this);
    }

    public void followMe(View view) {
        robot.beWithMe();
        hideKeyboard(MainActivity.this);
    }

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


    // TODO: 2019-06-16 WORK ON onNlpCompleted. At the moment I couldn't get it to load.
    @Override
    public void onNlpCompleted(NlpResult nlpResult) {
        //do something with nlp result. Base the action on what you get back
        Toast.makeText(MainActivity.this, nlpResult.action, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWakeupWord(String wakeupWord) {
        Toast.makeText(this, wakeupWord, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTtsStatusChanged(TtsRequest ttsRequest) {
        Toast.makeText(this, "package name: " + ttsRequest.getPackageName() + "\nspeech: " +
                       ttsRequest.getSpeech() + "\nstatus:" + ttsRequest.getStatus(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBeWithMeStatusChanged(String status) {
        Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGoToLocationStatusChanged(String location, String status) {
        Toast.makeText(MainActivity.this, status, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPublish(ActivityStreamPublishMessage message) {
        //Toast.makeText(this, message.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPlayButtonClicked(boolean play) {

    }

    @Override
    public void onNextButtonClicked() {

    }

    @Override
    public void onBackButtonClicked() {

    }

    @Override
    public void onTrackBarChanged(int position) {

    }

    @Override
    public void onNotificationBtnClicked(int btnNumber) {

    }

    @Override
    public void onConversationAttaches(boolean isAttached) {

    }

    @Override
    public void onLocationsUpdated(List<String> locations) {

    }
}
