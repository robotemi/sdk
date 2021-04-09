package com.robotemi.sdk;


public interface MediaContainer {

    String getMediaUri();

    void setMediaUri(String uri);

    String getLocalPath();

    MediaObject.MimeType getMimeType();
}
