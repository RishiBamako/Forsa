package com.bartering.forsa.activities.camera_section;

import android.graphics.Bitmap;

import java.io.Serializable;


public class MediaData_HolderModel implements Serializable {
    private String mediaType;
    private String uriPath;

    private Bitmap mediaImage;
    private boolean isInFocus;
    private String media_id;



    public MediaData_HolderModel(String mediaType, String uriPath, Bitmap mediaImage, boolean isInFocus) {
        this.mediaType = mediaType;
        this.uriPath = uriPath;
        this.mediaImage = mediaImage;
        this.isInFocus = isInFocus;
    }

    public MediaData_HolderModel(String mediaType, String uriPath, Bitmap mediaImage, boolean isInFocus, String media_id) {
        this.mediaType = mediaType;
        this.uriPath = uriPath;
        this.mediaImage = mediaImage;
        this.isInFocus = isInFocus;
        this.media_id = media_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getUriPath() {
        return uriPath;
    }

    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public Bitmap getMediaImage() {
        return mediaImage;
    }

    public void setMediaImage(Bitmap mediaImage) {
        this.mediaImage = mediaImage;
    }

    public boolean isInFocus() {
        return isInFocus;
    }

    public void setInFocus(boolean inFocus) {
        isInFocus = inFocus;
    }

}
