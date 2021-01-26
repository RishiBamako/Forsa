package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SubscriptionPackage_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : [{"name":"Bronze Package","total_ads":1,"price":"5","photo":2,"video":0,"visiblity_time":7,"day_month":"day","validation":"7 day","purchase_date":"15th 2020 October","expire_date":"22nd 2020 October"},{"name":"Platinum Package","total_ads":6,"price":"50","photo":4,"video":2,"visiblity_time":6,"day_month":"month","validation":"6 month","purchase_date":"15th 2020 October","expire_date":"15th 2021 April"}]
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
         * name : Bronze Package
         * total_ads : 1
         * price : 5
         * photo : 2
         * video : 0
         * visiblity_time : 7
         * day_month : day
         * validation : 7 day
         * purchase_date : 15th 2020 October
         * expire_date : 22nd 2020 October
         */

        private String name;
        private String total_ads;
        private String price;
        private String photo;
        private String video;
        private String visiblity_time;
        private String day_month;
        private String validation;
        private String purchase_date;
        private String active_deactive;
        private String expire_date;

        public String getActive_deactive() {
            return active_deactive;
        }

        public void setActive_deactive(String active_deactive) {
            this.active_deactive = active_deactive;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTotal_ads() {
            return total_ads;
        }

        public void setTotal_ads(String total_ads) {
            this.total_ads = total_ads;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getVisiblity_time() {
            return visiblity_time;
        }

        public void setVisiblity_time(String visiblity_time) {
            this.visiblity_time = visiblity_time;
        }

        public String getDay_month() {
            return day_month;
        }

        public void setDay_month(String day_month) {
            this.day_month = day_month;
        }

        public String getValidation() {
            return validation;
        }

        public void setValidation(String validation) {
            this.validation = validation;
        }

        public String getPurchase_date() {
            return purchase_date;
        }

        public void setPurchase_date(String purchase_date) {
            this.purchase_date = purchase_date;
        }

        public String getExpire_date() {
            return expire_date;
        }

        public void setExpire_date(String expire_date) {
            this.expire_date = expire_date;
        }
    }
}
