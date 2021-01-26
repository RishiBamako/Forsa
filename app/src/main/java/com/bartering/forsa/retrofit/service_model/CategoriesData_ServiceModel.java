package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class CategoriesData_ServiceModel {


    /**
     * status : true
     * message : Success!!
     * data : [{"id":9,"cat_name":"Fashion","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/dress.png"},{"id":10,"cat_name":"Accessories","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/xmlid_1184.png"},{"id":11,"cat_name":"Car","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/layer_6.png"},{"id":12,"cat_name":"Furniture","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/armchair.png"},{"id":13,"cat_name":"Electronics","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/air_conditioner.png"},{"id":14,"cat_name":"Home decor","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/plant.png"},{"id":15,"cat_name":"Phone","cat_image":"http://dev.rglabs.net/forsa/uploads/icons/laptop.png"}]
     */

    private String status;
    private String message;
    private List<DataBean> data;

    public String getStatus() {
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
         * id : 9
         * cat_name : Fashion
         * cat_image : http://dev.rglabs.net/forsa/uploads/icons/dress.png
         */

        private String id;
        private String cat_name;
        private String cat_image;
        private boolean isSelected;

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

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }

        public String getCat_image() {
            return cat_image;
        }

        public void setCat_image(String cat_image) {
            this.cat_image = cat_image;
        }
    }
}
