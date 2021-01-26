package com.bartering.forsa.retrofit.service_model;

public class Accept_Bartering_ServiceModel {

    /**
     * status : true
     * message : You have accept bartering deal, lets chat with offered user & complete the deal.
     * data : {"conversation_id":"127-114-37-2"}
     */

    private String status;
    private String message;

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


    public static class DataBean {
        /**
         * conversation_id : 127-114-37-2
         */

        private String conversation_id;

        public String getConversation_id() {
            return conversation_id;
        }

        public void setConversation_id(String conversation_id) {
            this.conversation_id = conversation_id;
        }
    }
}
