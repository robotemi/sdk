package com.robotemi.sdk;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DisplayListRequest implements Parcelable {

    public static final String TYPE_BASIC = "BASIC";

    public static final String TYPE_VIDEO = "VIDEO";

    public static final Parcelable.Creator<DisplayListRequest> CREATOR = new Parcelable.Creator<DisplayListRequest>() {
        @Override
        public DisplayListRequest createFromParcel(Parcel source) {
            return new DisplayListRequest(source);
        }

        @Override
        public DisplayListRequest[] newArray(int size) {
            return new DisplayListRequest[size];
        }
    };

    @NonNull
    private final List<ListItem> items;

    @NonNull
    private final String listTitle;

    @Nullable
    private final Bitmap addOn;

    @NonNull
    private final String listType;

    @NonNull
    private String packageName;

    private DisplayListRequest(@NonNull final List<ListItem> items,
                               @NonNull final String listTitle,
                               @NonNull final Bitmap addOn,
                               @NonNull final String listShape) {
        this.packageName = "";
        this.items = items;
        this.listTitle = listTitle;
        this.addOn = addOn;
        this.listType = listShape;
    }

    protected DisplayListRequest(Parcel in) {
        this.items = in.createTypedArrayList(ListItem.CREATOR);
        this.listTitle = in.readString();
        this.addOn = in.readParcelable(Bitmap.class.getClassLoader());
        this.listType = in.readString();
        this.packageName = in.readString();
    }

    public static Builder builder() {
        return new Builder();
    }

    @NonNull
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(@NonNull final String packageName) {
        this.packageName = packageName;
    }

    @Nullable
    public Bitmap getAddOn() {
        return addOn;
    }

    @NonNull
    public String getListTitle() {
        return listTitle;
    }

    @NonNull
    public List<ListItem> getItems() {
        return items;
    }

    @NonNull
    public String getListType() {
        return listType;
    }

    @Override
    public String toString() {
        return "DisplayListRequest{" +
                "items=" + items +
                ", listTitle='" + listTitle + '\'' +
                ", addOn=" + addOn +
                ", listType='" + listType + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.items);
        dest.writeString(this.listTitle);
        dest.writeParcelable(this.addOn, flags);
        dest.writeString(this.listType);
        dest.writeString(this.packageName);
    }

    public static class Builder {

        @NonNull
        private String listTitle = "";

        @NonNull
        private String listType = TYPE_BASIC;

        @Nullable
        private Bitmap addOn;

        @NonNull
        private List<ListItem> items = new ArrayList<>();

        private Builder() {
            // Builder is exposed through static method.
        }


        public Builder setListTitle(@NonNull final String listTitle) {
            this.listTitle = listTitle;
            return this;
        }

        public Builder setAddOn(@Nullable final Bitmap addOn) {
            this.addOn = addOn;
            return this;
        }

        public Builder setListType(@NonNull final String itemShape) {
            this.listType = itemShape;
            return this;
        }

        public Builder addItem(@NonNull final ListItem listItem) {
            items.add(listItem);
            return this;
        }

        public Builder setList(@NonNull List<ListItem> list) {
            this.items = list;
            return this;
        }

        public DisplayListRequest build() {

            if (items.size() == 0) {
                throw new IllegalArgumentException("Add some items to the list.");
            }
            return new DisplayListRequest(items, listTitle, addOn, listType);
        }
    }
}
