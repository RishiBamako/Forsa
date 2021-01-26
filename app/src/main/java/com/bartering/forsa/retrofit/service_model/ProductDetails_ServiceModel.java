package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class ProductDetails_ServiceModel implements Serializable {
    /**
     * status : true
     * message : Success
     * data : {"prdrec":[{"prd_id":29,"category_id":13,"subcategory_id":22,"third_category":29,"ads_id":"16094105612","title":"Canon Camera","description":"Canon Camera Description 2","selleremail":"varun@rglabs.net","sellermobile":"9785229873","seller_id":29,"sellername":"varunarora","batering":"yes","trade":"no","total_view":463,"price":null,"sellercountrycode":"91","sellerimage":null,"sellersince":"September 2020"}],"mediarec":[{"media_type":"image","product_image":"http://dev.rglabs.net/forsa/uploads/products/1601292268-1-camera1.jpg"},{"media_type":"image","product_image":"http://dev.rglabs.net/forsa/uploads/products/1601292268-2-camera2.jpg"}],"category":[{"cat_id":13,"cat_name":"Electronics"}],"subcategory":[{"subcat_id":22,"subcat_name":"Electronics"}],"thirdcategory":[{"subcat_id":29,"subcat_name":"Photo and Video Cameras"}]}
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

    public static class DataBean implements Serializable{
        private List<PrdrecBean> prdrec;
        private List<MediarecBean> mediarec;
        private List<CategoryBean> category;
        private List<SubcategoryBean> subcategory;
        private List<ThirdcategoryBean> thirdcategory;

        public List<PrdrecBean> getPrdrec() {
            return prdrec;
        }

        public void setPrdrec(List<PrdrecBean> prdrec) {
            this.prdrec = prdrec;
        }

        public List<MediarecBean> getMediarec() {
            return mediarec;
        }

        public void setMediarec(List<MediarecBean> mediarec) {
            this.mediarec = mediarec;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public List<SubcategoryBean> getSubcategory() {
            return subcategory;
        }

        public void setSubcategory(List<SubcategoryBean> subcategory) {
            this.subcategory = subcategory;
        }

        public List<ThirdcategoryBean> getThirdcategory() {
            return thirdcategory;
        }

        public void setThirdcategory(List<ThirdcategoryBean> thirdcategory) {
            this.thirdcategory = thirdcategory;
        }

        public static class PrdrecBean implements Serializable{
            /**
             * prd_id : 29
             * category_id : 13
             * subcategory_id : 22
             * third_category : 29
             * ads_id : 16094105612
             * title : Canon Camera
             * description : Canon Camera Description 2
             * selleremail : varun@rglabs.net
             * sellermobile : 9785229873
             * seller_id : 29
             * sellername : varunarora
             * batering : yes
             * trade : no
             * total_view : 463
             * price : null
             * sellercountrycode : 91
             * sellerimage : null
             * sellersince : September 2020
             */

            private String prd_id;
            private String category_id;
            private String subcategory_id;
            private String third_category;
            private String ads_id;
            private String title;
            private String description;
            private String selleremail;
            private String sellermobile;
            private String seller_id;
            private String sellername;
            private String batering;
            private String trade;
            private String total_view;
            private String price;
            private String sellercountrycode;
            private String sellerimage;
            private String sellersince;

            public String getPrd_id() {
                return prd_id;
            }

            public void setPrd_id(String prd_id) {
                this.prd_id = prd_id;
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

            public String getThird_category() {
                return third_category;
            }

            public void setThird_category(String third_category) {
                this.third_category = third_category;
            }

            public String getAds_id() {
                return ads_id;
            }

            public void setAds_id(String ads_id) {
                this.ads_id = ads_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSelleremail() {
                return selleremail;
            }

            public void setSelleremail(String selleremail) {
                this.selleremail = selleremail;
            }

            public String getSellermobile() {
                return sellermobile;
            }

            public void setSellermobile(String sellermobile) {
                this.sellermobile = sellermobile;
            }

            public String getSeller_id() {
                return seller_id;
            }

            public void setSeller_id(String seller_id) {
                this.seller_id = seller_id;
            }

            public String getSellername() {
                return sellername;
            }

            public void setSellername(String sellername) {
                this.sellername = sellername;
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

            public String getTotal_view() {
                return total_view;
            }

            public void setTotal_view(String total_view) {
                this.total_view = total_view;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSellercountrycode() {
                return sellercountrycode;
            }

            public void setSellercountrycode(String sellercountrycode) {
                this.sellercountrycode = sellercountrycode;
            }

            public String getSellerimage() {
                return sellerimage;
            }

            public void setSellerimage(String sellerimage) {
                this.sellerimage = sellerimage;
            }

            public String getSellersince() {
                return sellersince;
            }

            public void setSellersince(String sellersince) {
                this.sellersince = sellersince;
            }
        }

        public static class MediarecBean implements Serializable{
            /**
             * media_type : image
             * product_image : http://dev.rglabs.net/forsa/uploads/products/1601292268-1-camera1.jpg
             */

            private String media_type;
            private String media_id;
            private String product_image;

            public String getMedia_id() {
                return media_id;
            }

            public void setMedia_id(String media_id) {
                this.media_id = media_id;
            }

            public String getMedia_type() {
                return media_type;
            }

            public void setMedia_type(String media_type) {
                this.media_type = media_type;
            }

            public String getProduct_image() {
                return product_image;
            }

            public void setProduct_image(String product_image) {
                this.product_image = product_image;
            }
        }

        public static class CategoryBean implements Serializable{
            /**
             * cat_id : 13
             * cat_name : Electronics
             */

            private String cat_id;
            private String cat_name;

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }
        }

        public static class SubcategoryBean implements Serializable{
            /**
             * subcat_id : 22
             * subcat_name : Electronics
             */

            private String subcat_id;
            private String subcat_name;

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

        public static class ThirdcategoryBean implements Serializable{
            /**
             * subcat_id : 29
             * subcat_name : Photo and Video Cameras
             */

            private String subcat_id;
            private String subcat_name;

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


    /**
     * status : true
     * message : Success
     * data : {"prdrec":[{"prd_id":29,"title":"Canon Camera","description":"Canon Camera Description 2","selleremail":"varun@rglabs.net","sellermobile":"9785229873","sellername":"varunarora","batering":"yes","trade":"no","total_view":295,"price":null,"sellerimage":null,"sellersince":"September 2020"}],"mediarec":[{"media_type":"image","product_image":"http://dev.rglabs.net/forsa/uploads/products/1601292268-1-camera1.jpg"},{"media_type":"image","product_image":"http://dev.rglabs.net/forsa/uploads/products/1601292268-2-camera2.jpg"}]}
     */

  
}
