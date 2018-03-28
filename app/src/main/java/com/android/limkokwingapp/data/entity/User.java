package com.android.limkokwingapp.data.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gautam on 25/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
@Entity(tableName = "user")
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "first_name")
    private String mFirstName;

    @ColumnInfo(name = "last_name")
    private String mLastName;

    @ColumnInfo(name = "mobile")
    private String mMobileNumber;

    @ColumnInfo(name = "email")
    private String mEmail;

    @ColumnInfo(name = "gender")
    private String mGender;

    @ColumnInfo(name = "password")
    private String mPassword;

    public User() {

    }
    @Ignore
    protected User(Parcel in) {
        mId = in.readInt();
        mFirstName = in.readString();
        mLastName = in.readString();
        mMobileNumber = in.readString();
        mEmail = in.readString();
        mGender = in.readString();
        mPassword = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Ignore
    public User(String email, String password) {
        this.mEmail = email;
        this.mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getMobileNumber() {
        return mMobileNumber;
    }

    public void setMobileNumber(String mMobileNumber) {
        this.mMobileNumber = mMobileNumber;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String mGender) {
        this.mGender = mGender;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mFirstName);
        parcel.writeString(mLastName);
        parcel.writeString(mMobileNumber);
        parcel.writeString(mEmail);
        parcel.writeString(mGender);
        parcel.writeString(mPassword);
    }
}
