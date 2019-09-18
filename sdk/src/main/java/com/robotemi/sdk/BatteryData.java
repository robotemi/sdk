package com.robotemi.sdk;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class BatteryData implements Parcelable {

    @NonNull
    private final int level;

    @NonNull
    private final boolean isCharging;

    public BatteryData(@NonNull final int level, @NonNull final boolean isCharging) {
        this.level = level;
        this.isCharging = isCharging;
    }

    protected BatteryData(Parcel in) {
        level = in.readInt();
        isCharging = in.readInt() == 1;
    }

    public static final Creator<BatteryData> CREATOR = new Creator<BatteryData>() {
        @Override
        public BatteryData createFromParcel(Parcel in) {
            return new BatteryData(in);
        }

        @Override
        public BatteryData[] newArray(int size) {
            return new BatteryData[size];
        }
    };

    @NonNull
    public int getBatteryPercentage() {
        return level;
    }

    @NonNull
    public boolean isCharging() {
        return isCharging;
    }

    @Override
    public String toString() {
        return "BatteryData{" +
                "level=" + level +
                ", isCharging=" + isCharging +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(level);
        dest.writeInt(isCharging ? 1 : 0);
    }
}