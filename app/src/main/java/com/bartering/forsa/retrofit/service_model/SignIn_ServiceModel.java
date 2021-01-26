package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SignIn_ServiceModel {


    /**
     * status : true
     * data : {"id":37,"email":"adam@yahoo.in","user_name":"Adam","image":"http://dev.rglabs.net/forsa/uploads/user/1604387319-Screenshot_20201008-003926_Instagram.jpg","live_status":0,"otp":9292,"permissions":null,"last_login":"2020-12-14 11:16:24","first_name":null,"last_name":null,"full_name":"Adam","mobile":"8946991002","dob":"1991-07-18","address":null,"latitude":"Chamanpura","longitude":" Baran","country":5,"photo":null,"language_id":1,"my_category":"9,10,13,14","total_following":1,"total_followers":8,"is_verified":"Verified","active":1,"created_at":[],"updated_at":[],"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9kZXYucmdsYWJzLm5ldFwvZm9yc2FcL2FwaVwvdjFcL2xvZ2luIiwiaWF0IjoxNjA4MTg3MTcwLCJleHAiOjE2MDgyNTkxNzAsIm5iZiI6MTYwODE4NzE3MCwianRpIjoiUW1Fa1h1em51MjkxOXF5cCIsInN1YiI6MzcsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.wyNjZ4n2Ie0otVZkWxTQJthsn5P3vNYCg8561tcf_Tg"}
     * message : Login Success.
     */

    private String status;
    private DataBean data;
    private String message;

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * id : 37
         * email : adam@yahoo.in
         * user_name : Adam
         * image : http://dev.rglabs.net/forsa/uploads/user/1604387319-Screenshot_20201008-003926_Instagram.jpg
         * live_status : 0
         * otp : 9292
         * permissions : null
         * last_login : 2020-12-14 11:16:24
         * first_name : null
         * last_name : null
         * full_name : Adam
         * mobile : 8946991002
         * dob : 1991-07-18
         * address : null
         * latitude : Chamanpura
         * longitude :  Baran
         * country : 5
         * photo : null
         * language_id : 1
         * my_category : 9,10,13,14
         * total_following : 1
         * total_followers : 8
         * is_verified : Verified
         * active : 1
         * created_at : []
         * updated_at : []
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9kZXYucmdsYWJzLm5ldFwvZm9yc2FcL2FwaVwvdjFcL2xvZ2luIiwiaWF0IjoxNjA4MTg3MTcwLCJleHAiOjE2MDgyNTkxNzAsIm5iZiI6MTYwODE4NzE3MCwianRpIjoiUW1Fa1h1em51MjkxOXF5cCIsInN1YiI6MzcsInBydiI6Ijg3ZTBhZjFlZjlmZDE1ODEyZmRlYzk3MTUzYTE0ZTBiMDQ3NTQ2YWEifQ.wyNjZ4n2Ie0otVZkWxTQJthsn5P3vNYCg8561tcf_Tg
         */

        private String id;
        private String email;
        private String user_name;
        private String image;
        private String live_status;
        private String otp;
        private String permissions;
        private String last_login;
        private String first_name;
        private String last_name;
        private String full_name;
        private String mobile;
        private String dob;
        private String address;
        private String latitude;
        private String longitude;
        private String country;
        private String photo;
        private String language_id;
        private String my_category;
        private String total_following;
        private String total_followers;
        private String is_verified;
        private String active;
        private String token;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLive_status() {
            return live_status;
        }

        public void setLive_status(String live_status) {
            this.live_status = live_status;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getPermissions() {
            return permissions;
        }

        public void setPermissions(String permissions) {
            this.permissions = permissions;
        }

        public String getLast_login() {
            return last_login;
        }

        public void setLast_login(String last_login) {
            this.last_login = last_login;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getMy_category() {
            return my_category;
        }

        public void setMy_category(String my_category) {
            this.my_category = my_category;
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

        public String getIs_verified() {
            return is_verified;
        }

        public void setIs_verified(String is_verified) {
            this.is_verified = is_verified;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}
