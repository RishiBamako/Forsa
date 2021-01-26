package com.bartering.forsa.retrofit.service_model;

import java.util.List;

public class HomeFilter_NL_ServiceModel {


    /**
     * status : true
     * message : Success!!
     * data : {"sortbyrec":[{"id":549,"value":"nearestme","title":"Nearest Me"},{"id":550,"value":"recentlyadded","title":"Recently Added"},{"id":551,"value":"wishlist","title":"Wishlist"},{"id":552,"value":"mosttrending","title":"Most Trending"}],"categrec":[{"id":9,"slug":"fashion"},{"id":10,"slug":"accessories"},{"id":11,"slug":"car"},{"id":12,"slug":"furniture"},{"id":13,"slug":"electronics"},{"id":14,"slug":"home-decor"},{"id":15,"slug":"phone"}]}
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
             * slug : fashion
             */

            private String id;
            private String slug;
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

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }
        }
    }
}
