package com.robotemi.sdk.sample;

import android.app.Activity;

import com.robotemi.sdk.Robot;
import com.robotemi.sdk.navigation.model.SpeedLevel;
import com.robotemi.sdk.voice.WakeupRequest;

import java.util.ArrayList;

public class MainJavaActivity extends Activity {

    /**
     * This is sample code to test SDK Java compatibility
     */
    private void test() {
        Robot.getInstance().setVolume(10);
        Robot.getInstance().setVolume(11);
        Robot.getInstance().repose();
        int volume = Robot.getInstance().getVolume();
        Robot.getInstance().setKioskModeOn(false);

        Robot.getInstance().wakeup(new ArrayList<>(), new WakeupRequest());
        Robot.getInstance().goTo("a", false, false, SpeedLevel.Companion.customSpeed(1.5f));
    }
}
