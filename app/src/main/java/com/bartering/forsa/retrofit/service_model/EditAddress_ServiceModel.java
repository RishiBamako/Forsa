package com.bartering.forsa.retrofit.service_model;

public class EditAddress_ServiceModel {

    /**
     * status : true
     * message : you have successfully updated new address.
     * data : {"id":13,"user_id":2,"house_no":"6655","address":"dhawas update","landmark":"near school update","city":"jaipur update","state":"rajasthan update","pin_code":"30202155","country":"india update","status":0,"created_at":"2020-12-12 18:26:02","updated_at":"2020-12-22 12:58:21"}
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

    public static class DataBean {
        /**
         * id : 13
         * user_id : 2
         * house_no : 6655
         * address : dhawas update
         * landmark : near school update
         * city : jaipur update
         * state : rajasthan update
         * pin_code : 30202155
         * country : india update
         * status : 0
         * created_at : 2020-12-12 18:26:02
         * updated_at : 2020-12-22 12:58:21
         */

        private String id;
        private String user_id;
        private String house_no;
        private String address;
        private String landmark;
        private String city;
        private String state;
        private String pin_code;
        private String country;
        private String status;
        private String created_at;
        private String updated_at;

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

        public String getHouse_no() {
            return house_no;
        }

        public void setHouse_no(String house_no) {
            this.house_no = house_no;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getPin_code() {
            return pin_code;
        }

        public void setPin_code(String pin_code) {
            this.pin_code = pin_code;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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
