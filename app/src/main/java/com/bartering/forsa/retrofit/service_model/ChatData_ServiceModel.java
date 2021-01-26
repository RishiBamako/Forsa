package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class ChatData_ServiceModel {


    /**
     * status : true
     * message : Success
     * data : [{"id":25,"msg_img":1,"sender_id":37,"receiver_id":2,"final_message":"Congratulation you have connected with each other","msg_rprt":"loginuser"},{"id":26,"msg_img":1,"sender_id":37,"receiver_id":2,"final_message":"Congratulation you have connected with each other","msg_rprt":"loginuser"},{"id":27,"msg_img":1,"sender_id":37,"receiver_id":2,"final_message":"Congratulation you have connected with each other","msg_rprt":"loginuser"}]
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
         * id : 25
         * msg_img : 1
         * sender_id : 37
         * receiver_id : 2
         * final_message : Congratulation you have connected with each other
         * msg_rprt : loginuser
         */

        private String id;
        private String msg_img;
        private String sender_id;
        private String receiver_id;
        private String final_message;
        private String msg_rprt;
        private boolean itShouldRemove;

        public boolean isItShouldRemove() {
            return itShouldRemove;
        }

        public void setItShouldRemove(boolean itShouldRemove) {
            this.itShouldRemove = itShouldRemove;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsg_img() {
            return msg_img;
        }

        public void setMsg_img(String msg_img) {
            this.msg_img = msg_img;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public String getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(String receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getFinal_message() {
            return final_message;
        }

        public void setFinal_message(String final_message) {
            this.final_message = final_message;
        }

        public String getMsg_rprt() {
            return msg_rprt;
        }

        public void setMsg_rprt(String msg_rprt) {
            this.msg_rprt = msg_rprt;
        }
    }
}
