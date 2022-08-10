package com.robotemi.sdk;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;

public final class NlpResult implements Parcelable {

    public static final Parcelable.Creator<NlpResult> CREATOR = new Parcelable.Creator<NlpResult>() {

        @Override
        public NlpResult createFromParcel(Parcel in) {
            return new NlpResult(in);
        }

        @Override
        public NlpResult[] newArray(int size) {
            return new NlpResult[size];
        }
    };

    public String action;

    public String resolvedQuery;

    public Map<String, String> params;

    private NlpResult(Parcel in) {
        action = in.readString();
        resolvedQuery = in.readString();
        params = (Map) in.readValue(HashMap.class.getClassLoader());
    }

    public NlpResult() {
        // no-op
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(action);
        dest.writeString(resolvedQuery);
        dest.writeValue(params);
    }

    @NonNull
    @Override
    public String toString() {
        return "NlpResult{"
                + "action=" + action + ", "
                + "resolvedQuery=" + resolvedQuery + ", "
                + "params=" + params
                + "}";
    }
}