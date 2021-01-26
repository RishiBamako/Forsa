package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SimilarProducts_ServiceModel {

    /**
     * status : true
     * message : Success!
     * data : [{"id":23,"title":"Product One","category_id":13,"subcategory_id":22,"third_category_id":31,"user_id":35,"status":2,"visibility":1,"total_like":20,"total_view":250,"description":"Product Description","created_at":"2020-09-26 12:51:44","prd_img":"http://dev.rglabs.net/forsa/uploads/media/1601124704-1-drone-camera-500x500.jpg","likeornot":"Not Liked","wishstatus":"Wished"},{"id":29,"title":"Canon Camera ","category_id":13,"subcategory_id":22,"third_category_id":29,"user_id":29,"status":2,"visibility":1,"total_like":52,"total_view":290,"description":"Canon Camera Description 2","created_at":"2020-09-28 11:24:28","prd_img":"http://dev.rglabs.net/forsa/uploads/media/1601292268-1-camera1.jpg","likeornot":"Like","wishstatus":"Wished"}]
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
         * id : 23
         * title : Product One
         * category_id : 13
         * subcategory_id : 22
         * third_category_id : 31
         * user_id : 35
         * status : 2
         * visibility : 1
         * total_like : 20
         * total_view : 250
         * description : Product Description
         * created_at : 2020-09-26 12:51:44
         * prd_img : http://dev.rglabs.net/forsa/uploads/media/1601124704-1-drone-camera-500x500.jpg
         * likeornot : Not Liked
         * wishstatus : Wished
         */

        private String id;
        private String title;
        private String category_id;
        private String subcategory_id;
        private String third_category_id;
        private String user_id;
        private String status;
        private String visibility;
        private String total_like;
        private String total_view;
        private String description;
        private String created_at;
        private String prd_img;
        private String likeornot;
        private String wishstatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getSubcategory_id() {
            return subcategory_id;
        }

        public void setSubcategory_id(String subcategory_id) {
            this.subcategory_id = subcategory_id;
        }

        public String getThird_category_id() {
            return third_category_id;
        }

        public void setThird_category_id(String third_category_id) {
            this.third_category_id = third_category_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVisibility() {
            return visibility;
        }

        public void setVisibility(String visibility) {
            this.visibility = visibility;
        }

        public String getTotal_like() {
            return total_like;
        }

        public void setTotal_like(String total_like) {
            this.total_like = total_like;
        }

        public String getTotal_view() {
            return total_view;
        }

        public void setTotal_view(String total_view) {
            this.total_view = total_view;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getPrd_img() {
            return prd_img;
        }

        public void setPrd_img(String prd_img) {
            this.prd_img = prd_img;
        }

        public String getLikeornot() {
            return likeornot;
        }

        public void setLikeornot(String likeornot) {
            this.likeornot = likeornot;
        }

        public String getWishstatus() {
            return wishstatus;
        }

        public void setWishstatus(String wishstatus) {
            this.wishstatus = wishstatus;
        }
    }
}
