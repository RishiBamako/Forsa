package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SideMenu_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : [{"title":"Privacy Policy","icon":"http://dev.rglabs.net/forsa/uploads/icons/key.png"},{"title":"Terms and Condition","icon":"http://dev.rglabs.net/forsa/uploads/icons/google_docs.png"},{"title":"About Forsa","icon":"http://dev.rglabs.net/forsa/uploads/icons/4_f.png"}]
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
         * title : Privacy Policy
         * icon : http://dev.rglabs.net/forsa/uploads/icons/key.png
         */

        private String title;
        private String icon;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
