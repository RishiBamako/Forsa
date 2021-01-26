package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SellerProfile_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : {"prdpecord":[{"id":23,"title":"Product One","category_id":15,"subcategory_id":20,"third_category_id":33,"user_id":2,"status":2,"visibility":1,"total_like":20,"total_view":250,"description":"Product Description","created_at":"2020-09-26 12:51:44","prdimage":"http://dev.rglabs.net/forsa/uploads/media/1601124704-1-drone-camera-500x500.jpg"},{"id":24,"title":"Product Two","category_id":10,"subcategory_id":21,"third_category_id":31,"user_id":2,"status":2,"visibility":1,"total_like":11,"total_view":100,"description":"Product Description","created_at":"2020-09-26 12:56:41","prdimage":"http://dev.rglabs.net/forsa/uploads/media/1601125001-1-drone-camera-500x500.jpg"},{"id":25,"title":"Product Three","category_id":13,"subcategory_id":22,"third_category_id":31,"user_id":2,"status":2,"visibility":1,"total_like":102,"total_view":400,"description":"Product Description","created_at":"2020-09-26 12:57:24","prdimage":"http://dev.rglabs.net/forsa/uploads/media/1601125044-1-drone-camera-500x500.jpg"}],"userprofile":[{"user_name":"kamlesh","total_following":94,"total_followers":100,"userimage":"http://dev.rglabs.net/forsa/uploads/user/1601977511-drone-camera-500x500.jpg"}]}
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
        private String followerstatus;
        private List<PrdpecordBean> prdpecord;
        private List<UserprofileBean> userprofile;


        public String getFollowerstatus() {
            return followerstatus;
        }

        public void setFollowerstatus(String followerstatus) {
            this.followerstatus = followerstatus;
        }

        public List<PrdpecordBean> getPrdpecord() {
            return prdpecord;
        }

        public void setPrdpecord(List<PrdpecordBean> prdpecord) {
            this.prdpecord = prdpecord;
        }

        public List<UserprofileBean> getUserprofile() {
            return userprofile;
        }

        public void setUserprofile(List<UserprofileBean> userprofile) {
            this.userprofile = userprofile;
        }

        public static class PrdpecordBean {
            /**
             * id : 23
             * title : Product One
             * category_id : 15
             * subcategory_id : 20
             * third_category_id : 33
             * user_id : 2
             * status : 2
             * visibility : 1
             * total_like : 20
             * total_view : 250
             * description : Product Description
             * created_at : 2020-09-26 12:51:44
             * prdimage : http://dev.rglabs.net/forsa/uploads/media/1601124704-1-drone-camera-500x500.jpg
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
            private String prdimage;
            private String wish;
            private String like;


            public String getWish() {
                return wish;
            }

            public void setWish(String wish) {
                this.wish = wish;
            }

            public String getLike() {
                return like;
            }

            public void setLike(String like) {
                this.like = like;
            }

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

            public String getPrdimage() {
                return prdimage;
            }

            public void setPrdimage(String prdimage) {
                this.prdimage = prdimage;
            }
        }

        public static class UserprofileBean {
            /**
             * user_name : kamlesh
             * total_following : 94
             * total_followers : 100
             * userimage : http://dev.rglabs.net/forsa/uploads/user/1601977511-drone-camera-500x500.jpg
             */

            private String user_name;
            private String total_following;
            private String total_followers;
            private String userimage;
            private String is_verified;

            public String getIs_verified() {
                return is_verified;
            }

            public void setIs_verified(String is_verified) {
                this.is_verified = is_verified;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getTotal_following() {
                return total_following;
            }

            public void setTotal_following(String total_following) {
                this.total_following = total_following;
            }

            public String getTotal_followers() {
                return total_followers;
            }

            public void setTotal_followers(String total_followers) {
                this.total_followers = total_followers;
            }

            public String getUserimage() {
                return userimage;
            }

            public void setUserimage(String userimage) {
                this.userimage = userimage;
            }
        }
    }
}
