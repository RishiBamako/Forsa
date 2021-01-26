package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class HomeFilter_ServiceModel {

    /**
     * status : true
     * message : Success!!
     * data : {"sortbyrec":[{"id":549,"value":"nearestme","title":"Nearest Me"},{"id":550,"value":"recentlyadded","title":"Recently Added"},{"id":551,"value":"wishlist","title":"Wishlist"},{"id":552,"value":"mosttrending","title":"Most Trending"}],"categrec":[{"id":9,"cat_name":"Fashion","is_select":"select"},{"id":10,"cat_name":"Accessories","is_select":"select"},{"id":11,"cat_name":"Car","is_select":"not_select"},{"id":12,"cat_name":"Furniture","is_select":"not_select"},{"id":13,"cat_name":"Electronics","is_select":"select"},{"id":14,"cat_name":"Home decor","is_select":"select"},{"id":15,"cat_name":"Phone","is_select":"select"}]}
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
        private List<SortbyrecBean> sortbyrec;
        private List<CategrecBean> categrec;

        public List<SortbyrecBean> getSortbyrec() {
            return sortbyrec;
        }

        public void setSortbyrec(List<SortbyrecBean> sortbyrec) {
            this.sortbyrec = sortbyrec;
        }

        public List<CategrecBean> getCategrec() {
            return categrec;
        }

        public void setCategrec(List<CategrecBean> categrec) {
            this.categrec = categrec;
        }

        public static class SortbyrecBean {
            /**
             * id : 549
             * value : nearestme
             * title : Nearest Me
             */

            private String id;
            private String value;
            private String title;
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

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class CategrecBean {
            /**
             * id : 9
             * cat_name : Fashion
             * is_select : select
             */

            private String id;
            private String cat_name;
            private String is_select;
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

            public String getIs_select() {
                return is_select;
            }

            public void setIs_select(String is_select) {
                this.is_select = is_select;
            }
        }
    }
}
