package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class ProductCategory_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : [{"id":16,"cat_name":"Womens fashion"},{"id":17,"cat_name":"Mens fashion"},{"id":18,"cat_name":"Kids and newborns fashion"},{"id":19,"cat_name":"Childrens products"},{"id":20,"cat_name":"Phones and tablets"},{"id":21,"cat_name":"Computer and game consoles"},{"id":22,"cat_name":"Electronics"},{"id":23,"cat_name":"Healthy and beauty"},{"id":24,"cat_name":"Appliances"},{"id":25,"cat_name":"Pets"}]
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
         * id : 16
         * cat_name : Womens fashion
         */

        private String id;
        private boolean isSelected;
        private String cat_name;
        private int selectedPosition;

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public void setSelectedPosition(int selectedPosition) {
            this.selectedPosition = selectedPosition;
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

        public String getCat_name() {
            return cat_name;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
        }
    }
}
