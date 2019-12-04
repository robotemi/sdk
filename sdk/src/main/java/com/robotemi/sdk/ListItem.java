package com.robotemi.sdk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.robotemi.sdk.constants.Utils;

public class ListItem implements Parcelable {

    public static final Creator<ListItem> CREATOR = new Creator<ListItem>() {
        @Override
        public ListItem createFromParcel(Parcel in) {
            return new ListItem(in);
        }

        @Override
        public ListItem[] newArray(int size) {
            return new ListItem[size];
        }
    };

    @NonNull
    private final String label;

    @Nullable
    private final Bitmap iconBitmap;

    @Nullable
    private final String iconUrl;

    @NonNull
    private final String action;

    @NonNull
    private final Bundle parameters;

    private boolean isIconBitmapProvided;

    private ListItem(@NonNull final String label,
                     @Nullable final Bitmap icon,
                     @NonNull final String action,
                     @NonNull final Bundle parameters,
                     @Nullable final String url) {
        this.label = label;
        this.iconBitmap = icon;
        this.action = action;
        this.parameters = parameters;
        this.iconUrl = url;
        if (iconBitmap != null) {
            isIconBitmapProvided = true;
        }
    }

    private ListItem(Parcel in) {
        isIconBitmapProvided = in.readByte() == 1;
        if (isIconBitmapProvided) {
            final byte[] iconData = new byte[in.readInt()];
            in.readByteArray(iconData);
            iconBitmap = BitmapFactory.decodeByteArray(iconData, 0, iconData.length);
        } else {
            iconBitmap = null;
        }
        label = in.readString();
        action = in.readString();
        iconUrl = in.readString();
        parameters = in.readBundle(getClass().getClassLoader());
    }

    public static Builder builder() {
        return new Builder();
    }

    @Nullable
    public Bitmap getIcon() {
        return iconBitmap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isIconBitmapProvided ? 1 : 0));
        if (isIconBitmapProvided) {
            final byte[] iconData = Utils.convertBitmapToByteArray(iconBitmap);
            dest.writeInt(iconData.length);
            dest.writeByteArray(iconData);
        }
        dest.writeString(label);
        dest.writeString(action);
        dest.writeString(iconUrl);
        dest.writeBundle(parameters);
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    @Nullable
    public String getIconUrl() {
        return iconUrl;
    }

    @NonNull
    public String getAction() {
        return action;
    }

    @NonNull
    public Bundle getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListItem listItem = (ListItem) o;

        if (!label.equals(listItem.label)) return false;
        return action.equals(listItem.action);
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "label='" + label + '\'' +
                ", iconBitmap=" + iconBitmap +
                ", action='" + action + '\'' +
                ", parameters=" + parameters +
                '}';
    }

    public static class Builder {

        @NonNull
        private final Bundle parameters = new Bundle();

        private String label;

        private Bitmap iconBitmap;

        private String action;

        private String iconUrl;

        private Builder() {
            // Builder is exposed through static builder() method
        }

        public Builder setLabel(@NonNull final String label) {
            this.label = label;
            return this;
        }

        public Builder setIcon(@NonNull final Bitmap iconBitmap) {
            this.iconBitmap = iconBitmap;
            return this;
        }

        public Builder setIconUrl(@NonNull final String url) {
            this.iconUrl = url;
            return this;
        }

        public Builder setAction(@NonNull final String action) {
            this.action = action;
            return this;
        }

        /**
         * If value with specific key already exists - it will be overridden.
         */
        public Builder addParameter(@NonNull final String key, @NonNull final String value) {
            parameters.putString(key, value);
            return this;
        }

        public ListItem build() {
            if (TextUtils.isEmpty(label)) {
                throw new IllegalStateException("Label can't be null or empty.");
            }
            if (iconBitmap == null && iconUrl == null) {
                throw new IllegalStateException("Icon or icon url is required.");
            }
            if (TextUtils.isEmpty(action)) {
                throw new IllegalStateException("Action can't be null or empty.");
            }
            return new ListItem(label, iconBitmap, action, parameters, iconUrl);
        }
    }
}
