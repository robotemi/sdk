package com.robotemi.sdk.mediabar;

import android.os.RemoteException;

public interface MediaBarController {

    /**
     * set media .
     *
     * @param mediaBarData the media bar data, including title, subtitle and icon.
     */
    void updateMediaBar(MediaBarData mediaBarData) throws RemoteException;

    /**
     * pauses the media
     */
    void pauseMediaBar() throws RemoteException;

    /**
     * Indicate that media is being played
     */
    void setMediaPlaying(boolean isPlaying, String packageName) throws RemoteException;
}
