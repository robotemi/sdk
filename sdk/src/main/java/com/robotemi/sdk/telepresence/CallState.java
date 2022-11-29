package com.robotemi.sdk.telepresence;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CallState implements Parcelable {

    @NonNull
    private final String sessionId;

    private final State state;
    private final boolean lowLightMode;

    public CallState(@NonNull String sessionId, State state) {
        this.sessionId = sessionId;
        this.state = state;
        this.lowLightMode = false;
    }

    public CallState(@NonNull String sessionId, State state, boolean lowLightMode) {
        this.sessionId = sessionId;
        this.state = state;
        this.lowLightMode = lowLightMode;
    }

    @NonNull
    public String getSessionId() {
        return sessionId;
    }

    public State getState() {
        return state;
    }

    public boolean getLowLightMode() {
        return lowLightMode;
    }

    public enum State {

        // Call is ended
        ENDED,

        // Call is declined
        DECLINED,

        // Call is established
        STARTED,

        // Call is made but not answered yet.
        INITIALIZED,

        NOT_ANSWERED,

        // The other side is busy
        BUSY,

        // Cannot establish the call due to connection issue.
        POOR_CONNECTION,

        // Cannot join the call.
        CANT_JOIN,
    }

    protected CallState(Parcel in) {
        sessionId = in.readString();
        final int tempState = in.readInt();
        state = tempState == -1 ? null : State.values()[tempState];
        lowLightMode = in.readInt() == 1;

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sessionId);
        dest.writeInt(this.state == null ? -1 : this.state.ordinal());
        dest.writeInt(lowLightMode ? 1 : 0);
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
