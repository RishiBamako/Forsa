package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class BoostPlans_ServiceModel {

    /**
     * status : true
     * message : Success
     * data : [{"id":1,"no_of_ads":1,"price":10,"name":"Boost to Top 1 Ads","validation":"7 day"},{"id":2,"no_of_ads":2,"price":20,"name":"Boost to Top 2 Ads","validation":"15 day"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * no_of_ads : 1
         * price : 10
         * name : Boost to Top 1 Ads
         * validation : 7 day
         */

        private String id;
        private boolean isSelected;

        private String no_of_ads;
        private String price;
        private String tax;

        private String name;
        private String validation;

        public String getTax() {
            return tax;
        }

        public void setTax(String tax) {
            this.tax = tax;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNo_of_ads() {
            return no_of_ads;
        }

        public void setNo_of_ads(String no_of_ads) {
            this.no_of_ads = no_of_ads;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValidation() {
            return validation;
        }

        public void setValidation(String validation) {
            this.validation = validation;
        }
    }
}
