package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class Languages_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : [{"id":1,"title":"English","lang_code":"en"},{"id":3,"title":"Hindi","lang_code":"hi"}]
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
         * id : 1
         * title : English
         * lang_code : en
         */

        private String id;
        private String title;
        private String lang_code;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLang_code() {
            return lang_code;
        }

        public void setLang_code(String lang_code) {
            this.lang_code = lang_code;
        }
    }
}
