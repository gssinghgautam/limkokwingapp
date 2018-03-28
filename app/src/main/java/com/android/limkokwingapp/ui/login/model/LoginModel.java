package com.android.limkokwingapp.ui.login.model;

/**
 * Created by gautam on 26/03/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class LoginModel {

    private String mEmail;

    private String mPassword;

    public LoginModel(String mEmail, String mPassword) {
        this.mEmail = mEmail;
        this.mPassword = mPassword;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
