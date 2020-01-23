package com.robotemi.sdk;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.robotemi.sdk.constants.SdkConstants;

class TemiSdkServiceConnection {

    private static final String TAG = "TemiSdkServiceConnection";

    @NonNull
    private final ServiceConnection serviceConnection = new ServiceConnection() {

        @SuppressLint({"LogNotTimber", "LongLogTag"})
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected(ComponentName, IBinder) (name=" + name + ", service=" + service + ", thread=" + Thread.currentThread().getName() + ")");
            final ISdkService sdkService = ISdkService.Stub.asInterface(service);
            Robot.Companion.getInstance().setSdkService(sdkService);
        }

        @SuppressLint({"LogNotTimber", "LongLogTag"})
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected(ComponentName) (name=" + name + ", thread=" + Thread.currentThread().getName() + ")");
            forceStop();
        }
    };

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private static Intent getSdkServiceIntent(String appId) {
        Log.d(TAG, "getSdkServiceIntent()");
        final ComponentName componentName = new ComponentName(appId, BuildConfig.SDK_SERVICE_CLASS_NAME);
        final Intent intent = new Intent();
        intent.setComponent(componentName);
        return intent;
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    @UiThread
    void startConnection(@NonNull final Context context) {
        Log.d(TAG, "startConnection(Context)");
        if (context.bindService(getSdkServiceIntent(SdkConstants.TEMI_USA), serviceConnection, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "bindServiceUsa=true");
        } else if (context.bindService(getSdkServiceIntent(SdkConstants.TEMI_CHINA), serviceConnection, Context.BIND_AUTO_CREATE)) {
            Log.d(TAG, "bindServiceChina=true");
        } else {
            Log.w(TAG, "bindService=false");
            forceStop();
        }
    }

    @SuppressLint({"LogNotTimber", "LongLogTag"})
    private void forceStop() {
        Log.d(TAG, "forceStop()");
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
