package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class ProductLike_ServiceModel {

    /**
     * status : true
     * message : You have dislike this product successfully!
     * data : [{"id":29,"title":"Canon Camera ","slug":null,"category_id":13,"subcategory_id":22,"third_category_id":29,"price":null,"offer_price":null,"currency":null,"description":"Canon Camera Description 2","product_condition":null,"country_id":null,"state_id":null,"city_id":null,"address":null,"zip_code":null,"user_id":29,"status":2,"visibility":1,"rating":"0","hit":0,"total_like":52,"total_view":290,"quantity":1,"shipping_time":"2_3_business_days","shipping_cost_type":null,"shipping_cost":0,"is_sold":0,"is_deleted":0,"is_draft":0,"batering":"yes","trade":"no","created_at":"2020-09-28 11:24:28"}]
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
         * id : 29
         * title : Canon Camera 
         * slug : null
         * category_id : 13
         * subcategory_id : 22
         * third_category_id : 29
         * price : null
         * offer_price : null
         * currency : null
         * description : Canon Camera Description 2
         * product_condition : null
         * country_id : null
         * state_id : null
         * city_id : null
         * address : null
         * zip_code : null
         * user_id : 29
         * status : 2
         * visibility : 1
         * rating : 0
         * hit : 0
         * total_like : 52
         * total_view : 290
         * quantity : 1
         * shipping_time : 2_3_business_days
         * shipping_cost_type : null
         * shipping_cost : 0
         * is_sold : 0
         * is_deleted : 0
         * is_draft : 0
         * batering : yes
         * trade : no
         * created_at : 2020-09-28 11:24:28
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
        private String product_condition;
        private String country_id;
        private String state_id;
        private String city_id;
        private String address;
        private String zip_code;
        private String user_id;
        private String status;
        private String visibility;
        private String rating;
        private String hit;
        private String total_like;
        private String total_view;
        private String quantity;
        private String shipping_time;
        private String shipping_cost_type;
        private String shipping_cost;
        private String is_sold;
        private String is_deleted;
        private String is_draft;
        private String batering;
        private String trade;
        private String created_at;

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

        public String getProduct_condition() {
            return product_condition;
        }

        public void setProduct_condition(String product_condition) {
            this.product_condition = product_condition;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getState_id() {
            return state_id;
        }

        public void setState_id(String state_id) {
            this.state_id = state_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getZip_code() {
            return zip_code;
        }

        public void setZip_code(String zip_code) {
            this.zip_code = zip_code;
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

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getHit() {
            return hit;
        }

        public void setHit(String hit) {
            this.hit = hit;
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

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(String shipping_time) {
            this.shipping_time = shipping_time;
        }

        public String getShipping_cost_type() {
            return shipping_cost_type;
        }

        public void setShipping_cost_type(String shipping_cost_type) {
            this.shipping_cost_type = shipping_cost_type;
        }

        public String getShipping_cost() {
            return shipping_cost;
        }

        public void setShipping_cost(String shipping_cost) {
            this.shipping_cost = shipping_cost;
        }

        public String getIs_sold() {
            return is_sold;
        }

        public void setIs_sold(String is_sold) {
            this.is_sold = is_sold;
        }

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
            this.is_deleted = is_deleted;
        }

        public String getIs_draft() {
            return is_draft;
        }

        public void setIs_draft(String is_draft) {
            this.is_draft = is_draft;
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
    }
}
