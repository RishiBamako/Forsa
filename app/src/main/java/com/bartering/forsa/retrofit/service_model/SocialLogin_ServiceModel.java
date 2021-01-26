package com.bartering.forsa.retrofit.service_model;

public class SocialLogin_ServiceModel {

    /**
     * status : true
     * message : you have successfully registered.
     * data : {"email":null,"facebook_id":"123213123aasassassa","mobile":null,"is_verified":1,"active":1,"updated_at":"2020-12-30 10:29:37","created_at":"2020-12-30 10:29:37","id":98}
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9kZXYucmdsYWJzLm5ldFwvZm9yc2FcL2FwaVwvdjFcL3NvY2lhbC1sb2dpbiIsImlhdCI6MTYwOTMwNDM3NywiZXhwIjoxNjA5Mzc2Mzc3LCJuYmYiOjE2MDkzMDQzNzcsImp0aSI6InZraWN3cTR0eEFmMWZQRVEiLCJzdWIiOjk4LCJwcnYiOiJmNmI3MTU0OWRiOGMyYzQyYjc1ODI3YWE0NGYwMmI3ZWU1MjlkMjRkIn0.WcdX2X9Zix4USk6OPzD3R2X_SH32VOXSypCKqXB3iZg
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
         * email : null
         * facebook_id : 123213123aasassassa
         * mobile : null
         * is_verified : 1
         * active : 1
         * updated_at : 2020-12-30 10:29:37
         * created_at : 2020-12-30 10:29:37
         * id : 98
         */

        private String email;
        private String facebook_id;
        private String mobile;
        private String is_verified;
        private String active;
        private String updated_at;
        private String created_at;
        private String id;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFacebook_id() {
            return facebook_id;
        }

        public void setFacebook_id(String facebook_id) {
            this.facebook_id = facebook_id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
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
