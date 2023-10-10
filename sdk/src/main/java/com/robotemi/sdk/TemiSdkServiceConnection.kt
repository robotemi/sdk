package com.robotemi.sdk

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.Process
import android.util.Log

import androidx.annotation.NonNull
import androidx.annotation.UiThread

import com.robotemi.sdk.constants.SdkConstants

internal class TemiSdkServiceConnection {

    companion object {
        private const val TAG = "TemiSdkServiceConnection"

        @SuppressLint("LongLogTag")
        private fun getSdkServiceIntent(appId: String): Intent {
            Log.d(TAG, "getSdkServiceIntent()")
            val componentName = ComponentName(appId, BuildConfig.SDK_SERVICE_CLASS_NAME)
            return Intent().apply { component = componentName }
        }
    }

    @Volatile
    private var isLauncherExist = false

    @NonNull
    private val serviceConnection = object : ServiceConnection {

        @SuppressLint("LongLogTag")
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected(ComponentName, IBinder) (name=$name, service=$service)")
            val sdkService = ISdkService.Stub.asInterface(service)
            Robot.getInstance().setSdkService(sdkService)
        }

        @SuppressLint("LongLogTag")
        override fun onServiceDisconnected(name: ComponentName) {
            Log.d(TAG, "onServiceDisconnected(ComponentName) (name=$name)")
            forceStop()
        }
    }

    @SuppressLint("LongLogTag")
    @UiThread
    internal fun startConnection(context: Context) {
        Log.d(TAG, "startConnection(Context)")
        isLauncherExist = isPackageExist(context, SdkConstants.TEMI_USA) ||
                isPackageExist(context, SdkConstants.TEMI_CHINA)
        when {
            context.bindService(
                getSdkServiceIntent(SdkConstants.TEMI_USA),
                serviceConnection,
                Context.BIND_AUTO_CREATE
            ) -> {
                Log.d(TAG, "bindServiceUsa=true")
            }
            context.bindService(
                getSdkServiceIntent(SdkConstants.TEMI_CHINA),
                serviceConnection,
                Context.BIND_AUTO_CREATE
            ) -> {
                Log.d(TAG, "bindServiceChina=true")
            }
            else -> {
                Log.w(TAG, "bindService=false")
                forceStop()
            }
        }
    }

    @SuppressLint("LongLogTag")
    private fun forceStop() {
        Log.d(TAG, "forceStop()")
        if (isLauncherExist) {
            Process.killProcess(Process.myPid())
        }
    }

    private fun isPackageExist(context: Context, targetPackage: String): Boolean {
        val pm: PackageManager = context.packageManager
        try {
            pm.getPackageInfo(targetPackage, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            return false
        }
        return true
    }
}
