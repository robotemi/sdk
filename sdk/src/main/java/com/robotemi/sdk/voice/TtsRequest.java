package com.robotemi.sdk.voice;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TtsRequest implements Parcelable {

    public static final Creator<TtsRequest> CREATOR = new Creator<TtsRequest>() {
        @Override
        public TtsRequest createFromParcel(Parcel source) {
            return new TtsRequest(source);
        }

        @Override
        public TtsRequest[] newArray(int size) {
            return new TtsRequest[size];
        }
    };

    @NonNull
    private final UUID id;

    @NonNull
    private final String speech;

    @NonNull
    private String packageName;

    @NonNull
    private Status status;

    @Nullable
    private Bitmap bitmap;

    private boolean isShowOnConversationLayer;

    private TtsRequest(@NonNull final String speech, @Nullable final Bitmap bitmap, boolean isShowOnConversationLayer) {
        id = UUID.randomUUID();
        this.packageName = "";
        this.speech = speech;
        status = Status.PENDING;
        this.bitmap = bitmap;
        this.isShowOnConversationLayer = isShowOnConversationLayer; // Currently this field is not streamed from skill
    }

    protected TtsRequest(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.speech = in.readString();
        this.packageName = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
        this.bitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.isShowOnConversationLayer = in.readByte() != 0;
    }

    public static TtsRequest create(@NonNull final String speech, boolean isShowOnConversationLayer) {
        return new TtsRequest(speech, null, isShowOnConversationLayer);
    }

    @NonNull
    public String getSpeech() {
        return speech;
    }

    @Nullable
    public Bitmap getDrawableBitmap() {
        return bitmap;
    }

    @NonNull
    public UUID getId() {
        return id;
    }

    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(@NonNull final String packageName) {
        this.packageName = packageName;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull final Status status) {
        this.status = status;
    }

    @NonNull
    public boolean isShowOnConversationLayer() {
        return isShowOnConversationLayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TtsRequest that = (TtsRequest) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "TtsRequest{" +
                "id=" + id +
                ", speech='" + speech + '\'' +
                ", packageName='" + packageName + '\'' +
                ", status=" + status +
                ", isShowOnConversationLayer=" + isShowOnConversationLayer +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.speech);
        dest.writeString(this.packageName);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeParcelable(this.bitmap, flags);
        dest.writeByte(this.isShowOnConversationLayer ? (byte) 1 : (byte) 0);
    }

    public enum Status {
        PENDING,
        PROCESSING,
        STARTED,
        COMPLETED,
        ERROR,
        NOT_ALLOWED
    }
}
