package com.ffwatl.util;


public class Settings {

    private final String photoUrl;

    private final String photoDir;

    public Settings(String photoUrl, String photoDir){
        this.photoDir = photoDir;
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public  String getPhotoDir() {
        return this.photoDir;
    }
}