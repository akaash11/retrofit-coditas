package com.example.akaash.assignment.model;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by akaash on 12/1/18.
 */

public class Profile {

    public Profile(String profileName, String profileUrl, String dataOne, String dataTwo) {
        this.profileName = profileName;
        this.profileUrl = profileUrl;
        this.dataOne = dataOne;
        this.dataTwo = dataTwo;
        //this.profileImage = profileImage;
    }

    private String profileName;
    private String profileUrl;
    private String dataOne;
    private String dataTwo;
    private ImageView profileImage;

    public ImageView getProfileImage() {
        return profileImage;
    }



    public void setProfileImage(ImageView profileImage) {
        this.profileImage = profileImage;
    }



    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDataOne() {
        return dataOne;
    }

    public void setDataOne(String dataOne) {
        this.dataOne = dataOne;
    }

    public String getDataTwo() {
        return dataTwo;
    }

    public void setDataTwo(String dataTwo) {
        this.dataTwo = dataTwo;
    }



}
