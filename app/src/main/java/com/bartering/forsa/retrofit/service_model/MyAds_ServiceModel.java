package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class MyAds_ServiceModel implements Serializable {


    /**
     * status : true
     * message : Success
     * data : [{"id":66,"title":"products","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-26 11:18:44","expiry_date":"2021-04-26","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603711124-1-Screenshot_20201013-190644_Stately Academy.jpg","productstatus":"Pending","tradestatus":"Batering"},{"id":67,"title":"ghtgggg","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-26 12:34:02","expiry_date":"2021-04-26","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603715642-1-202010_26-180315cameraRecorder.png","productstatus":"Pending","tradestatus":"Batering"},{"id":68,"title":"hshahhaha","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-26 13:13:08","expiry_date":"2021-04-26","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603717988-1-202010_26-184130cameraRecorder.png","productstatus":"Pending","tradestatus":"Batering"},{"id":69,"title":"Mine","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 06:06:28","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603778788-1-202010_27-113418cameraRecorder.png","productstatus":"Pending","tradestatus":"Batering"},{"id":70,"title":"guggg","status":2,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 06:54:38","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603781678-1-202010_27-121653cameraRecorder.png","productstatus":"Active","tradestatus":"Batering"},{"id":71,"title":"dsdsd","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 06:54:41","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603781681-1-Screenshot_20201013-190644_Stately Academy.jpg","productstatus":"Pending","tradestatus":"Batering"},{"id":72,"title":"dsdsd","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 07:19:48","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603783188-1-20200930_103301.jpg","productstatus":"Pending","tradestatus":"Batering"},{"id":73,"title":"dsfsdfdsfsdfsdfsd","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 07:20:15","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603783215-1-logotwo.png","productstatus":"Pending","tradestatus":"Batering"},{"id":75,"title":"Produuu","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 12:26:53","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603801613-1-202010_27-175600cameraRecorder.png","productstatus":"Pending","tradestatus":"Batering"},{"id":76,"title":"ghfuggyg","status":0,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 12:38:39","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603802319-1-202010_27-180635cameraRecorder.png","productstatus":"Pending","tradestatus":"Batering"},{"id":77,"title":"fgftyggg","status":2,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 12:42:35","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603802555-1-202010_27-181029cameraRecorder.png","productstatus":"Active","tradestatus":"Batering"},{"id":78,"title":"ghgvggg","status":2,"visibility":1,"total_like":0,"total_view":0,"created_at":"2020-10-27 12:46:16","expiry_date":"2021-04-27","file_name":"http://dev.rglabs.net/forsa/uploads/products/1603802776-1-202010_27-181452cameraRecorder.png","productstatus":"Active","tradestatus":"Batering"}]
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
         * id : 66
         * title : products
         * status : 0
         * visibility : 1
         * total_like : 0
         * total_view : 0
         * created_at : 2020-10-26 11:18:44
         * expiry_date : 2021-04-26
         * file_name : http://dev.rglabs.net/forsa/uploads/products/1603711124-1-Screenshot_20201013-190644_Stately Academy.jpg
         * productstatus : Pending
         * tradestatus : Batering
         */

        private String id;
        private String title;
        private String status;
        private String visibility;
        private String total_like;
        private String total_view;
        private String created_at;
        private String expiry_date;
        private String file_name;
        private String productstatus;
        private String tradestatus;

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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public String getTotal_like() {
            return total_like;
        }

        public void setTotal_like(String total_like) {
            this.total_like = total_like;
        }

        public String getTotal_view() {
            return total_view;
        }

        public void setTotal_view(String total_view) {
            this.total_view = total_view;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getExpiry_date() {
            return expiry_date;
        }

        public void setExpiry_date(String expiry_date) {
            this.expiry_date = expiry_date;
        }

        public String getFile_name() {
            return file_name;
        }

        public void setFile_name(String file_name) {
            this.file_name = file_name;
        }

        public String getProductstatus() {
            return productstatus;
        }

        public void setProductstatus(String productstatus) {
            this.productstatus = productstatus;
        }

        public String getTradestatus() {
            return tradestatus;
        }

        public void setTradestatus(String tradestatus) {
            this.tradestatus = tradestatus;
        }
    }
}
