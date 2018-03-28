package com.android.limkokwingapp.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by gautam on 26/03/18.
 */
@SuppressWarnings("DefaultFileTemplate")
public class Photos implements Parcelable {

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int pages;

    @SerializedName("perpage")
    private int perPage;

    @SerializedName("total")
    private String total;

    @SerializedName("photo")
    private List<Photo> photo = null;

    protected Photos(Parcel in) {
        page = in.readInt();
        pages = in.readInt();
        perPage = in.readInt();
        total = in.readString();
        photo = in.createTypedArrayList(Photo.CREATOR);
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel in) {
            return new Photos(in);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(pages);
        parcel.writeInt(perPage);
        parcel.writeString(total);
        parcel.writeTypedList(photo);
    }
}
