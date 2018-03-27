package com.android.limkokwingapp.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gautam on 26/03/18.
 */


public class FlickerPhotos implements Parcelable {

    @SerializedName("photos")
    private Photos photos;

    @SerializedName("stat")
    private String stat;

    protected FlickerPhotos(Parcel in) {
        photos = in.readParcelable(Photos.class.getClassLoader());
        stat = in.readString();
    }

    public static final Creator<FlickerPhotos> CREATOR = new Creator<FlickerPhotos>() {
        @Override
        public FlickerPhotos createFromParcel(Parcel in) {
            return new FlickerPhotos(in);
        }

        @Override
        public FlickerPhotos[] newArray(int size) {
            return new FlickerPhotos[size];
        }
    };

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(photos, i);
        parcel.writeString(stat);
    }
}
