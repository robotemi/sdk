package com.robotemi.sdk.activitystream;


public interface MediaContainer {

    String getMediaUri();

    void setMediaUri(String uri);

    String getLocalPath();

    MediaObject.MimeType getMimeType();
}
