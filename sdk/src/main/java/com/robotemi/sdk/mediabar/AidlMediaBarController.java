package com.robotemi.sdk.mediabar;

import android.os.RemoteException;
import android.util.Log;

import com.robotemi.sdk.ISdkService;

import androidx.annotation.Nullable;

public class AidlMediaBarController implements MediaBarController {

    @Nullable
    private final ISdkService sdkService;

    public AidlMediaBarController(@Nullable final ISdkService sdkService) {
        this.sdkService = sdkService;
    }

    @Override
    public void updateMediaBar(MediaBarData mediaBarData) throws RemoteException {
        Log.d("MediaBarData", "mediaBarData AidlButtonController: " + mediaBarData);
        if (sdkService != null) {
            sdkService.setMedia(mediaBarData);
        } else {
            throw new RemoteException();
        }
    }

    @Override
    public void pauseMediaBar() throws RemoteException {
        if (sdkService != null) {
            sdkService.pause();
        } else {
            throw new RemoteException();
        }
    }

    @Override
    public void setMediaPlaying(boolean isPlaying, String packageName) throws RemoteException {
        if (sdkService != null) {
            sdkService.setMediaPlaying(isPlaying, packageName);
        } else {
            throw new RemoteException();
        }
    }
}
