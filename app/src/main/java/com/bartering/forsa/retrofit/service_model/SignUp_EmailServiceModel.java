package com.bartering.forsa.retrofit.service_model;

public class SignUp_EmailServiceModel {

    /**
     * status : true
     * message : you have successfully registered.
     * data : {"user_name":"varunarora-e","email":"varunsetia5@gmail.com","mobile":"9785229881","otp":1393,"active":1,"is_verified":1,"updated_at":"2020-09-24 19:57:12","created_at":"2020-09-24 19:57:12","id":33}
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9kZXYucmdsYWJzLm5ldFwvZm9yc2FcL2FwaVwvdjFcL3JlZ2lzdGVyIiwiaWF0IjoxNjAwOTU3NjMyLCJleHAiOjE2MDEwMjk2MzIsIm5iZiI6MTYwMDk1NzYzMiwianRpIjoiV1lQSVFTV0d0OTZOaVBpYSIsInN1YiI6MzMsInBydiI6ImY2YjcxNTQ5ZGI4YzJjNDJiNzU4MjdhYTQ0ZjAyYjdlZTUyOWQyNGQifQ.XcGbbzTiHhc66JsqQdAEP-ZuQnDCwGvOOcSLGuQkQRs
     */

    private String status;
    private String message;
    private DataBean data;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class DataBean {
        /**
         * user_name : varunarora-e
         * email : varunsetia5@gmail.com
         * mobile : 9785229881
         * otp : 1393
         * active : 1
         * is_verified : 1
         * updated_at : 2020-09-24 19:57:12
         * created_at : 2020-09-24 19:57:12
         * id : 33
         */

        private String user_name;
        private String email;
        private String mobile;
        private String otp;
        private String active;
        private String is_verified;
        private String updated_at;
        private String created_at;
        private String id;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
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

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
