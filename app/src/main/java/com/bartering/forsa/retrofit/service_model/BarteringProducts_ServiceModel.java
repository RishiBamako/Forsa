package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class BarteringProducts_ServiceModel implements Serializable {

    /**
     * status : true
     * message : Success
     * data : [{"title":"Canon Camera","id":29,"user_id":29,"prd_img":"http://dev.rglabs.net/forsa/uploads/products/1601292268-1-camera1.jpg"},{"title":"Product Name","id":43,"user_id":2,"prd_img":"http://dev.rglabs.net/forsa/uploads/products/1603276224-1-drone-camera-500x500.jpg"},{"title":"Product Name","id":44,"user_id":2,"prd_img":"http://dev.rglabs.net/forsa/uploads/products/1603276251-1-drone-camera-500x500.jpg"},{"title":"name","id":45,"user_id":2,"prd_img":"http://dev.rglabs.net/forsa/uploads/products/1604128147-1-file_example_MP4_480_1_5MG.mp4"}]
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

    public static class DataBean implements Serializable{
        /**
         * title : Canon Camera
         * id : 29
         * user_id : 29
         * prd_img : http://dev.rglabs.net/forsa/uploads/products/1601292268-1-camera1.jpg
         */

        private String title;
        private String id;
        private String user_id;
        private String prd_img;
        private String user_img;

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPrd_img() {
            return prd_img;
        }

        public void setPrd_img(String prd_img) {
            this.prd_img = prd_img;
        }
    }
}
