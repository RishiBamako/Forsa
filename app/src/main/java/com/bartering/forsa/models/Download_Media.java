package com.bartering.forsa.models;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.File;

public class Download_Media {
    String id;
    Bitmap bitmap;
    Uri videoUri;
    String mediaType;
    File file;

    public Download_Media(String id, Bitmap bitmap, Uri videoUri, String mediaType, File file) {
        this.id = id;
        this.bitmap = bitmap;
        this.videoUri = videoUri;
        this.mediaType = mediaType;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }



    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Uri getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(Uri videoUri) {
        this.videoUri = videoUri;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
