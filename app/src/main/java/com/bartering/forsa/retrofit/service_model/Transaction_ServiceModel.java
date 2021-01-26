package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class Transaction_ServiceModel implements Serializable {


    /**
     * status : true
     * message : Success
     * data : [{"id":6,"order_id":"#58546585625","trans_id":"#4256254","trans_type":"trading","prd_title":"Product Name","offer_to_prd_title":null,"seller_id":37,"buyer_id":11,"seller_name":"Adam","buyer_name":"jyoti","transactiondate":"1st September 2020","prdimg":"http://dev.rglabs.net/forsa/uploads/products/1601124704-1-drone-camera-500x500.jpg","offer_to_prd_media_prdimg":null,"seller_photo":"http://dev.rglabs.net/forsa/uploads/user1609335593-20201230_091603.jpg","buyer_photo":null},{"id":7,"order_id":"#56533332127114","trans_id":null,"trans_type":"bartering","prd_title":"New Product Name","offer_to_prd_title":"twywyyw","seller_id":37,"buyer_id":2,"seller_name":"Adam","buyer_name":"kamlesh","transactiondate":"6th January 2020","prdimg":"http://dev.rglabs.net/forsa/uploads/products/1609218397-1-valve-2.jpg","offer_to_prd_media_prdimg":"http://dev.rglabs.net/forsa/uploads/products/1609228504-1-202012_29-132433cameraRecorder.png","seller_photo":"http://dev.rglabs.net/forsa/uploads/user1609335593-20201230_091603.jpg","buyer_photo":"http://dev.rglabs.net/forsa/uploads/user1602671848-drone-camera-500x500.jpg"}]
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
        /**
         * id : 6
         * order_id : #58546585625
         * trans_id : #4256254
         * trans_type : trading
         * prd_title : Product Name
         * offer_to_prd_title : null
         * seller_id : 37
         * buyer_id : 11
         * seller_name : Adam
         * buyer_name : jyoti
         * transactiondate : 1st September 2020
         * prdimg : http://dev.rglabs.net/forsa/uploads/products/1601124704-1-drone-camera-500x500.jpg
         * offer_to_prd_media_prdimg : null
         * seller_photo : http://dev.rglabs.net/forsa/uploads/user1609335593-20201230_091603.jpg
         * buyer_photo : null
         */

        private String id;
        private String order_id;
        private String trans_id;
        private String trans_type;
        private String prd_title;
        private String offer_to_prd_title;
        private String seller_id;
        private String buyer_id;
        private String seller_name;
        private String buyer_name;
        private String transactiondate;
        private String prdimg;
        private String offer_to_prd_media_prdimg;
        private String seller_photo;
        private String buyer_photo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getTrans_id() {
            return trans_id;
        }

        public void setTrans_id(String trans_id) {
            this.trans_id = trans_id;
        }

        public String getTrans_type() {
            return trans_type;
        }

        public void setTrans_type(String trans_type) {
            this.trans_type = trans_type;
        }

        public String getPrd_title() {
            return prd_title;
        }

        public void setPrd_title(String prd_title) {
            this.prd_title = prd_title;
        }

        public String getOffer_to_prd_title() {
            return offer_to_prd_title;
        }

        public void setOffer_to_prd_title(String offer_to_prd_title) {
            this.offer_to_prd_title = offer_to_prd_title;
        }

        public String getSeller_id() {
            return seller_id;
        }

        public void setSeller_id(String seller_id) {
            this.seller_id = seller_id;
        }

        public String getBuyer_id() {
            return buyer_id;
        }

        public void setBuyer_id(String buyer_id) {
            this.buyer_id = buyer_id;
        }

        public String getSeller_name() {
            return seller_name;
        }

        public void setSeller_name(String seller_name) {
            this.seller_name = seller_name;
        }

        public String getBuyer_name() {
            return buyer_name;
        }

        public void setBuyer_name(String buyer_name) {
            this.buyer_name = buyer_name;
        }

        public String getTransactiondate() {
            return transactiondate;
        }

        public void setTransactiondate(String transactiondate) {
            this.transactiondate = transactiondate;
        }

        public String getPrdimg() {
            return prdimg;
        }

        public void setPrdimg(String prdimg) {
            this.prdimg = prdimg;
        }

        public String getOffer_to_prd_media_prdimg() {
            return offer_to_prd_media_prdimg;
        }

        public void setOffer_to_prd_media_prdimg(String offer_to_prd_media_prdimg) {
            this.offer_to_prd_media_prdimg = offer_to_prd_media_prdimg;
        }

        public String getSeller_photo() {
            return seller_photo;
        }

        public void setSeller_photo(String seller_photo) {
            this.seller_photo = seller_photo;
        }

        public String getBuyer_photo() {
            return buyer_photo;
        }

        public void setBuyer_photo(String buyer_photo) {
            this.buyer_photo = buyer_photo;
        }
    }
}
