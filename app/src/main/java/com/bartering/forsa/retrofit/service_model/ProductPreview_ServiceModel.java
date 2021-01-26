package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class ProductPreview_ServiceModel implements Serializable {


    /**
     * status : true
     * message : Product Detail have been update successfully.
     * data : [{"detail":[{"id":25,"title":"Product Three","slug":null,"category_id":13,"subcategory_id":22,"third_category_id":31,"price":0,"offer_price":null,"currency":null,"description":"Product Description","user_id":2,"status":1,"visibility":1,"batering":"yes","trade":"no","created_at":"2020-09-26 12:57:24","category_one":"","category_two":"","category_three":""}],"media":[{"media_type":"video","media_file":"http://dev.rglabs.net/forsa/uploads/products/1604468527-1-file_example_MP4_480_1_5MG.mp4"},{"media_type":"image","media_file":"http://dev.rglabs.net/forsa/uploads/products/1604468558-1-drone-camera-500x500.jpg"},{"media_type":"image","media_file":"http://dev.rglabs.net/forsa/uploads/products/1604468576-1-guess_bag.jpg"}]}]
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

    public static class DataBean implements Serializable{
        private List<DetailBean> detail;
        private List<MediaBean> media;

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public List<MediaBean> getMedia() {
            return media;
        }

        public void setMedia(List<MediaBean> media) {
            this.media = media;
        }

        public static class DetailBean implements Serializable{
            /**
             * id : 25
             * title : Product Three
             * slug : null
             * category_id : 13
             * subcategory_id : 22
             * third_category_id : 31
             * price : 0
             * offer_price : null
             * currency : null
             * description : Product Description
             * user_id : 2
             * status : 1
             * visibility : 1
             * batering : yes
             * trade : no
             * created_at : 2020-09-26 12:57:24
             * category_one :
             * category_two :
             * category_three :
             */

            private String id;
            private String title;
            private String slug;
            private String category_id;
            private String subcategory_id;
            private String third_category_id;
            private String price;
            private String offer_price;
            private String currency;
            private String description;
            private String user_id;
            private String status;
            private String visibility;
            private String batering;
            private String trade;
            private String created_at;
            private String category_one;
            private String category_two;
            private String category_three;

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

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
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

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getOffer_price() {
                return offer_price;
            }

            public void setOffer_price(String offer_price) {
                this.offer_price = offer_price;
            }

            public String getCurrency() {
                return currency;
            }

            public void setCurrency(String currency) {
                this.currency = currency;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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

            public String getBatering() {
                return batering;
            }

            public void setBatering(String batering) {
                this.batering = batering;
            }

            public String getTrade() {
                return trade;
            }

            public void setTrade(String trade) {
                this.trade = trade;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getCategory_one() {
                return category_one;
            }

            public void setCategory_one(String category_one) {
                this.category_one = category_one;
            }

            public String getCategory_two() {
                return category_two;
            }

            public void setCategory_two(String category_two) {
                this.category_two = category_two;
            }

            public String getCategory_three() {
                return category_three;
            }

            public void setCategory_three(String category_three) {
                this.category_three = category_three;
            }
        }

        public static class MediaBean implements Serializable{
            /**
             * media_type : video
             * media_file : http://dev.rglabs.net/forsa/uploads/products/1604468527-1-file_example_MP4_480_1_5MG.mp4
             */

            private String media_type;
            private String media_file;

            public String getMedia_type() {
                return media_type;
            }

            public void setMedia_type(String media_type) {
                this.media_type = media_type;
            }

            public String getMedia_file() {
                return media_file;
            }

            public void setMedia_file(String media_file) {
                this.media_file = media_file;
            }
        }
    }
}
