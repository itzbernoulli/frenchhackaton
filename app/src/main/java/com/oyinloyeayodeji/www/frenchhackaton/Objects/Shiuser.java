package com.oyinloyeayodeji.www.frenchhackaton.Objects;

/**
 * Created by Ayo on 10/05/2017.
 */

public class Shiuser {

    private String mLastname;
    private String mFirstname;
    private String mNetwork;
    private String mEmail;
    private String mImageUrl = "";
    private double mLatitude = 0;
    private double mLongitude = 0;

    public String getmEmail() {
        return mEmail;
    }

    private String mToken;

    public Shiuser() {
    }

    public Shiuser(String mLastname, String mFirstname, String mNetwork, String mEmail, String mImageUrl, double mLatitude, double mLongitude, String mToken) {
        this.mLastname = mLastname;
        this.mFirstname = mFirstname;
        this.mNetwork = mNetwork;
        this.mEmail = mEmail;
        this.mImageUrl = mImageUrl;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mToken = mToken;
    }

    public Shiuser(String mFirstname, String mLastname, String mNetwork, String mEmail, String mToken, String mImageUrl) {
        this.mLastname = mLastname;
        this.mNetwork = mNetwork;
        this.mFirstname = mFirstname;
        this.mEmail = mEmail;
        this.mToken = mToken;
        this.mImageUrl = mImageUrl;
    }

    public Shiuser(String mFirstname, String mLastname, String mNetwork, String mEmail, String mImageUrl) {
        this.mLastname = mLastname;
        this.mNetwork = mNetwork;
        this.mFirstname = mFirstname;
        this.mEmail = mEmail;
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmLastname() {
        return mLastname;
    }

    public void setmLastname(String mLastname) {
        this.mLastname = mLastname;
    }

    public String getmFirstname() {
        return mFirstname;
    }

    public void setmFirstname(String mFirstname) {
        this.mFirstname = mFirstname;
    }

    public String getmNetwork() {
        return mNetwork;
    }

    public void setmNetwork(String mNetwork) {
        this.mNetwork = mNetwork;
    }

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }
}
