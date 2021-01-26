package com.bartering.forsa.retrofit.service_model;

import androidx.annotation.Nullable;

public class UploadProduct_ServiceModel {

    /**
     * status : true
     * message : Product has been added successfully!!
     * data : {"prd_id":28}
     */

    private String status;
    private String message;

    @Nullable
    private DataBean data;



    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public DataBean getData() {
        return data;
    }

    @Nullable
    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * prd_id : 28
         */

        private String prd_id;

        public String getPrd_id() {
            return prd_id;
        }

        public void setPrd_id(String prd_id) {
            this.prd_id = prd_id;
        }
    }
}
