package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SettingProfile_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : [{"title":"English","notification_status":"ON"}]
     */

    private String status;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : English
         * notification_status : ON
         */

        private String title;
        private String notification_status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getNotification_status() {
            return notification_status;
        }

        public void setNotification_status(String notification_status) {
            this.notification_status = notification_status;
        }
    }
}
