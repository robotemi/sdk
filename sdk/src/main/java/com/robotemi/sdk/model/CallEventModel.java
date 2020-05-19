package com.robotemi.sdk.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CallEventModel implements Parcelable {

    public static final int TYPE_INCOMING = 0;

    public static final int TYPE_OUTGOING = 1;

    public static final int STATE_STARTED = 0;

    public static final int STATE_ENDED = 1;

    @IntDef({TYPE_INCOMING, TYPE_OUTGOING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CallType { }

    @IntDef({STATE_STARTED, STATE_ENDED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CallState { }

    private String sessionId;

    @CallType
    private int type;

    @CallState
    private int state;

    public CallEventModel() {
    }

    public CallEventModel(String sessionId, @CallType int type, @CallState int state) {
        this.sessionId = sessionId;
        this.type = type;
        this.state = state;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @CallType
    public int getType() {
        return type;
    }

    public void setType(@CallType int type) {
        this.type = type;
    }

    @CallState
    public int getState() {
        return state;
    }

    public void setState(@CallState int state) {
        this.state = state;
    }

    @NotNull
    @Override
    public String toString() {
        return "CallEventModel{" +
                "sessionId='" + sessionId + '\'' +
                ", type=" + type +
                ", state=" + state +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sessionId);
        dest.writeInt(this.type);
        dest.writeInt(this.state);
    }

    protected CallEventModel(Parcel in) {
        this.sessionId = in.readString();
        this.type = in.readInt();
        this.state = in.readInt();
    }

    public static final Parcelable.Creator<CallEventModel> CREATOR = new Parcelable.Creator<CallEventModel>() {
        @Override
        public CallEventModel createFromParcel(Parcel source) {
            return new CallEventModel(source);
        }

        @Override
        public CallEventModel[] newArray(int size) {
            return new CallEventModel[size];
        }
    };
}
