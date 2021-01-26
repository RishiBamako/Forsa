package com.bartering.forsa.retrofit;

import androidx.lifecycle.MutableLiveData;

public class ResultData {
    private MutableLiveData<Object> rootData;
    private String tag;

    public ResultData(MutableLiveData<Object> rootData, String tag) {
        this.rootData = rootData;
        this.tag = tag;
    }

    public MutableLiveData<Object> getRootData() {
        return rootData;
    }

    public void setRootData(MutableLiveData<Object> rootData) {
        this.rootData = rootData;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
