package com.bartering.forsa.retrofit.service_model;

public class SignUpMobile_ServiceModel {


    /**
     * status : true
     * message : OTP sent on your mobile please check.
     * data : {"mobile":9785229877,"otp":8522,"updated_at":"2020-10-22 17:43:38","created_at":"2020-10-22 17:43:38","id":49}
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9kZXYucmdsYWJzLm5ldFwvZm9yc2FcL2FwaVwvdjFcL3JlZ2lzdGVyLW5ldyIsImlhdCI6MTYwMzM2ODgxOCwiZXhwIjoxNjAzNDQwODE4LCJuYmYiOjE2MDMzNjg4MTgsImp0aSI6ImtOcm1WZXVSa2NKV2hoUk8iLCJzdWIiOjQ5LCJwcnYiOiJmNmI3MTU0OWRiOGMyYzQyYjc1ODI3YWE0NGYwMmI3ZWU1MjlkMjRkIn0.otBgege7vldeF_CirPx_5rzLxvjv6eV02Q98icgUbfY
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
         * mobile : 9785229877
         * otp : 8522
         * updated_at : 2020-10-22 17:43:38
         * created_at : 2020-10-22 17:43:38
         * id : 49
         */

        private String mobile;
        private String otp;
        private String updated_at;
        private String created_at;
        private String id;

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
