package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class FollowerData_ServiceModel {


    /**
     * status : true
     * message : Follower List.
     * data : [{"userid":37,"following_id":2,"follower_id":37,"user_name":"kamlesh","email":"kamleshd1@rglabs.net","image":"http://dev.rglabs.net/forsa/uploads/user/1602671848-drone-camera-500x500.jpg","option":"Follower","iamfollowing":"no"},{"userid":37,"following_id":11,"follower_id":37,"user_name":"jyoti","email":"jyoti@rglabs.net","image":"http://dev.rglabs.net/forsa/uploads/user/profile.jpg","option":"Follower","iamfollowing":"no"},{"userid":37,"following_id":29,"follower_id":37,"user_name":"varunarora","email":"varun@rglabs.net","image":"http://dev.rglabs.net/forsa/uploads/user/profile.jpg","option":"Follower","iamfollowing":"yes"},{"userid":37,"following_id":30,"follower_id":37,"user_name":"Demo","email":"d@yopmail.com","image":"http://dev.rglabs.net/forsa/uploads/user/profile.jpg","option":"Follower","iamfollowing":"no"},{"userid":37,"following_id":32,"follower_id":37,"user_name":"Assad","email":"aaa@yopmail.com","image":"http://dev.rglabs.net/forsa/uploads/user/profile.jpg","option":"Follower","iamfollowing":"no"},{"userid":37,"following_id":33,"follower_id":37,"user_name":"varunarora-e","email":"varunsetia777@gmail.com","image":"http://dev.rglabs.net/forsa/uploads/user/profile.jpg","option":"Follower","iamfollowing":"no"},{"userid":37,"following_id":36,"follower_id":37,"user_name":"rewrite","email":"ww@yopmail.com","image":"http://dev.rglabs.net/forsa/uploads/user/user_1.jpg","option":"Follower","iamfollowing":"no"}]
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
         * following_id : 2
         * follower_id : 37
         * user_name : kamlesh
         * email : kamleshd1@rglabs.net
         * image : http://dev.rglabs.net/forsa/uploads/user/1602671848-drone-camera-500x500.jpg
         * option : Follower
         * iamfollowing : no
         */

        private String userid;
        private String following_id;
        private String follower_id;
        private String user_name;
        private String email;
        private String image;
        private String option;
        private String iamfollowing;

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

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }

        public String getIamfollowing() {
            return iamfollowing;
        }

        public void setIamfollowing(String iamfollowing) {
            this.iamfollowing = iamfollowing;
        }
    }
}
