package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class SubCategoriesData_ServiceModel {


    /**
     * status : true
     * message : Success!!
     * data : [{"subcat_id":26,"subcat_name":"Audio and video equipment"},{"subcat_id":27,"subcat_name":"Smart wathches and bracelets"},{"subcat_id":28,"subcat_name":"Monowheels and gyro scooters"},{"subcat_id":29,"subcat_name":"Photo and video cameras"},{"subcat_id":30,"subcat_name":"E books"},{"subcat_id":32,"subcat_name":"Gps navigation"},{"subcat_id":34,"subcat_name":"Quadcopters"},{"subcat_id":35,"subcat_name":"Headphone and bluetooth headsets"}]
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
         * subcat_id : 26
         * subcat_name : Audio and video equipment
         */

        private boolean isSelected;
        private int selectedPosition;
        private String subcat_id;
        private String subcat_name;

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

        public String getSubcat_id() {
            return subcat_id;
        }

        public void setSubcat_id(String subcat_id) {
            this.subcat_id = subcat_id;
        }

        public String getSubcat_name() {
            return subcat_name;
        }

        public void setSubcat_name(String subcat_name) {
            this.subcat_name = subcat_name;
        }
    }
}
