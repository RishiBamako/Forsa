package com.bartering.forsa.NavigationDrawer;

/**
 *
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private String bitmap_Icon;


    public NavDrawerItem(boolean showNotify, String title, String bitmap_Icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.bitmap_Icon = bitmap_Icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBitmap_Icon() {
        return bitmap_Icon;
    }

    public void setBitmap_Icon(String bitmap_Icon) {
        this.bitmap_Icon = bitmap_Icon;
    }
}
