package com.robotemi.sdk;


import com.robotemi.sdk.MediaObject;

public interface MediaContainer {

    String getMediaUri();

    void setMediaUri(String uri);

    String getLocalPath();

    MediaObject.MimeType getMimeType();
}
