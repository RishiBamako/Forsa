package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;

public class OfferBartering_ServiceModel implements Serializable {

    /**
     * status : true
     * message : Success
     * data : {"bartering_id":19,"status":"Deal Start"}
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
         * bartering_id : 19
         * status : Deal Start
         */

        private String bartering_id;
        private String conversation_id;
        private String status;


        public String getConversation_id() {
            return conversation_id;
        }

        public void setConversation_id(String conversation_id) {
            this.conversation_id = conversation_id;
        }

        public String getBartering_id() {
            return bartering_id;
        }

        public void setBartering_id(String bartering_id) {
            this.bartering_id = bartering_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
