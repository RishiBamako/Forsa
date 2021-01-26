package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class ProfileData_ServiceModel implements Serializable {

    /**
     * status : true
     * message : Success!
     * data : {"email":"adam@yahoo.in","gender":"male","user_name":"Adam_Black_Carliel","full_name":null,"mobile":"8946991002","dob":null,"latitude":null,"longitude":null,"live_status":0,"language_id":3,"last_login":"2020-10-09 11:13:38","address":null,"active":1,"is_verified":1,"total_following":1,"total_followers":6,"id_photo_url":"http://dev.rglabs.net/forsa/uploads/user/user_4.jpg","notifications":[]}
     */

    private String status;
    private String message;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * email : adam@yahoo.in
         * gender : male
         * user_name : Adam_Black_Carliel
         * full_name : null
         * mobile : 8946991002
         * dob : null
         * latitude : null
         * longitude : null
         * live_status : 0
         * language_id : 3
         * last_login : 2020-10-09 11:13:38
         * address : null
         * active : 1
         * is_verified : 1
         * total_following : 1
         * total_followers : 6
         * id_photo_url : http://dev.rglabs.net/forsa/uploads/user/user_4.jpg
         * notifications : []
         */

        private String email;
        private String gender;
        private String user_name;
        private String full_name;
        private String mobile;
        private String dob;
        private String latitude;
        private String longitude;
        private String live_status;
        private String language_id;
        private String last_login;
        private String address;
        private String active;
        private String is_verified;
        private String total_following;
        private String total_followers;
        private String id_photo_url;
        private List<?> notifications;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLive_status() {
            return live_status;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getIs_verified() {
            return is_verified;
        }

        public void setIs_verified(String is_verified) {
            this.is_verified = is_verified;
        }

        public String getTotal_following() {
            return total_following;
        }

        public void setTotal_following(String total_following) {
            this.total_following = total_following;
        }

        public String getTotal_followers() {
            return total_followers;
        }

        public void setTotal_followers(String total_followers) {
            this.total_followers = total_followers;
        }

        public String getId_photo_url() {
            return id_photo_url;
        }

        public void setId_photo_url(String id_photo_url) {
            this.id_photo_url = id_photo_url;
        }

        public List<?> getNotifications() {
            return notifications;
        }

        public void setNotifications(List<?> notifications) {
            this.notifications = notifications;
        }
    }
}
