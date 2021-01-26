package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class ProductReviewsList_ServiceModel {


    /**
     * status : true
     * message : Success!
     * data : [{"user_name":"Adam_Black_Carliel","rating":"5","comment":"comment comment comment comment comment","ratingdate":"10th October 2020"},{"user_name":"jyoti","rating":"4","comment":"comment comment comment comment comment","ratingdate":"10th October 2020"}]
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
         * user_name : Adam_Black_Carliel
         * rating : 5
         * comment : comment comment comment comment comment
         * ratingdate : 10th October 2020
         */

        private String user_name;
        private String rating;
        private String comment;
        private String ratingdate;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getRatingdate() {
            return ratingdate;
        }

        public void setRatingdate(String ratingdate) {
            this.ratingdate = ratingdate;
        }
    }
}
