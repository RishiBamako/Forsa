package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class CartData_ServiceModel {

    /**
     * status : true
     * message : Success
     * data : {"total_prd":2,"userprofile":[{"user_name":"Adam","total_following":1,"total_followers":8,"userimage":"http://dev.rglabs.net/forsa/uploads/user/1604387319-Screenshot_20201008-003926_Instagram.jpg"}],"prdpecord":[{"id":20,"prd_id":42,"title":"Product Name","category_id":13,"subcategory_id":22,"third_category_id":29,"user_id":2,"status":2,"visibility":1,"total_like":14,"total_view":28,"description":"Product Description","created_at":"2020-10-21 10:29:26","prdimage":"http://dev.rglabs.net/forsa/uploads/products/1603276166-1-drone-camera-500x500.jpg","wishstatus":"Not Wished"},{"id":24,"prd_id":24,"title":"Product Name","category_id":10,"subcategory_id":21,"third_category_id":31,"user_id":2,"status":2,"visibility":1,"total_like":45,"total_view":106,"description":"Product Description","created_at":"2020-09-26 12:56:41","prdimage":"http://dev.rglabs.net/forsa/uploads/products/1601125001-1-drone-camera-500x500.jpg","wishstatus":"Not Wished"}],"product_total_price":600}
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
         * total_prd : 2
         * userprofile : [{"user_name":"Adam","total_following":1,"total_followers":8,"userimage":"http://dev.rglabs.net/forsa/uploads/user/1604387319-Screenshot_20201008-003926_Instagram.jpg"}]
         * prdpecord : [{"id":20,"prd_id":42,"title":"Product Name","category_id":13,"subcategory_id":22,"third_category_id":29,"user_id":2,"status":2,"visibility":1,"total_like":14,"total_view":28,"description":"Product Description","created_at":"2020-10-21 10:29:26","prdimage":"http://dev.rglabs.net/forsa/uploads/products/1603276166-1-drone-camera-500x500.jpg","wishstatus":"Not Wished"},{"id":24,"prd_id":24,"title":"Product Name","category_id":10,"subcategory_id":21,"third_category_id":31,"user_id":2,"status":2,"visibility":1,"total_like":45,"total_view":106,"description":"Product Description","created_at":"2020-09-26 12:56:41","prdimage":"http://dev.rglabs.net/forsa/uploads/products/1601125001-1-drone-camera-500x500.jpg","wishstatus":"Not Wished"}]
         * product_total_price : 600
         */

        private String total_prd;
        private String product_total_price;
        private List<UserprofileBean> userprofile;
        private List<PrdpecordBean> prdpecord;

        public String getTotal_prd() {
            return total_prd;
        }

        public void setTotal_prd(String total_prd) {
            this.total_prd = total_prd;
        }

        public String getProduct_total_price() {
            return product_total_price;
        }

        public void setProduct_total_price(String product_total_price) {
            this.product_total_price = product_total_price;
        }

        public List<UserprofileBean> getUserprofile() {
            return userprofile;
        }

        public void setUserprofile(List<UserprofileBean> userprofile) {
            this.userprofile = userprofile;
        }

        public List<PrdpecordBean> getPrdpecord() {
            return prdpecord;
        }

        public void setPrdpecord(List<PrdpecordBean> prdpecord) {
            this.prdpecord = prdpecord;
        }

        public static class UserprofileBean {
            /**
             * user_name : Adam
             * total_following : 1
             * total_followers : 8
             * userimage : http://dev.rglabs.net/forsa/uploads/user/1604387319-Screenshot_20201008-003926_Instagram.jpg
             */

            private String user_name;
            private String total_following;
            private String total_followers;
            private String userimage;

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

        public static class PrdpecordBean {
            /**
             * id : 20
             * prd_id : 42
             * title : Product Name
             * category_id : 13
             * subcategory_id : 22
             * third_category_id : 29
             * user_id : 2
             * status : 2
             * visibility : 1
             * total_like : 14
             * total_view : 28
             * description : Product Description
             * created_at : 2020-10-21 10:29:26
             * prdimage : http://dev.rglabs.net/forsa/uploads/products/1603276166-1-drone-camera-500x500.jpg
             * wishstatus : Not Wished
             */

            private String id;
            private String prd_id;
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
            private String wishstatus;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPrd_id() {
                return prd_id;
            }

            public void setPrd_id(String prd_id) {
                this.prd_id = prd_id;
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

            public String getWishstatus() {
                return wishstatus;
            }

            public void setWishstatus(String wishstatus) {
                this.wishstatus = wishstatus;
            }
        }
    }
}
