package com.robotemi.sdk.telepresence;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CallState implements Parcelable {

    @NonNull
    private final String sessionId;

    private final State state;

    public CallState(@NonNull String sessionId, State state) {
        this.sessionId = sessionId;
        this.state = state;
    }

    @NonNull
    public String getSessionId() {
        return sessionId;
    }

    public State getState() {
        return state;
    }

    public enum State {
        ENDED, DECLINED, STARTED
    }

    protected CallState(Parcel in) {
        sessionId = in.readString();
        final int tempState = in.readInt();
        state = tempState == -1 ? null : State.values()[tempState];

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sessionId);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "CallState{" +
                "sessionId=" + sessionId +
                ", state=" + state + '}';
    }

    public static final Creator<CallState> CREATOR = new Creator<CallState>() {
        @Override
        public CallState createFromParcel(Parcel in) {
            return new CallState(in);
        }

        @Override
        public CallState[] newArray(int size) {
            return new CallState[size];
        }
    };
}
