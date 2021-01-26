package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class FollowingUser_ServiceModel {

    /**
     * status : true
     * message : Following List
     * data : [{"userid":37,"following_id":37,"follower_id":37,"user_name":"Adam_Black_Carliel","email":"adam@yahoo.in","image":"http://dev.rglabs.net/forsa/uploads/user/user_4.jpg"}]
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
         * userid : 37
         * following_id : 37
         * follower_id : 37
         * user_name : Adam_Black_Carliel
         * email : adam@yahoo.in
         * image : http://dev.rglabs.net/forsa/uploads/user/user_4.jpg
         */

        private String userid;
        private String following_id;
        private String follower_id;
        private String user_name;
        private String email;
        private String image;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getFollowing_id() {
            return following_id;
        }

        public void setFollowing_id(String following_id) {
            this.following_id = following_id;
        }

        public String getFollower_id() {
            return follower_id;
        }

        public void setFollower_id(String follower_id) {
            this.follower_id = follower_id;
        }

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
