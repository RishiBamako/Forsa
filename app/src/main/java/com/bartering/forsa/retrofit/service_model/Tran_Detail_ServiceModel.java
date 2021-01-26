package com.bartering.forsa.retrofit.service_model;

import java.io.Serializable;
import java.util.List;

public class Tran_Detail_ServiceModel implements Serializable {

    /**
     * status : true
     * message : Success!!!
     * data : [{"id":6,"order_id":"#58546585625","trans_id":"#4256254","title":"Product One","buyer_user_id":11,"seller_user_id":37,"buyerusername":"jyoti","sellerusername":"Adam_Black_Carliel","transactiondate":"1st September 2020","productimg":"http://dev.rglabs.net/forsa/uploads/products/1601124704-1-drone-camera-500x500.jpg","buyeruserimg":null,"selleruserimg":"http://dev.rglabs.net/forsa/uploads/user/1602569898-IMG-20201011-WA0019.jpg"}]
     */

    public String status;
    public String message;
    public List<DataBean> data;

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
         * id : 6
         * order_id : #58546585625
         * trans_id : #4256254
         * title : Product One
         * buyer_user_id : 11
         * seller_user_id : 37
         * buyerusername : jyoti
         * sellerusername : Adam_Black_Carliel
         * transactiondate : 1st September 2020
         * productimg : http://dev.rglabs.net/forsa/uploads/products/1601124704-1-drone-camera-500x500.jpg
         * buyeruserimg : null
         * selleruserimg : http://dev.rglabs.net/forsa/uploads/user/1602569898-IMG-20201011-WA0019.jpg
         */

        public String id;
        public String order_id;
        public String trans_id;
        public String title;
        public String buyer_user_id;
        public String seller_user_id;
        public String buyerusername;
        public String sellerusername;
        public String transactiondate;
        public String productimg;
        public String buyeruserimg;
        public String selleruserimg;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBuyer_user_id() {
            return buyer_user_id;
        }

        public void setBuyer_user_id(String buyer_user_id) {
            this.buyer_user_id = buyer_user_id;
        }

        public String getSeller_user_id() {
            return seller_user_id;
        }

        public void setSeller_user_id(String seller_user_id) {
            this.seller_user_id = seller_user_id;
        }

        public String getBuyerusername() {
            return buyerusername;
        }

        public void setBuyerusername(String buyerusername) {
            this.buyerusername = buyerusername;
        }

        public String getSellerusername() {
            return sellerusername;
        }

        public void setSellerusername(String sellerusername) {
            this.sellerusername = sellerusername;
        }

        public String getTransactiondate() {
            return transactiondate;
        }

        public void setTransactiondate(String transactiondate) {
            this.transactiondate = transactiondate;
        }

        public String getProductimg() {
            return productimg;
        }

        public void setProductimg(String productimg) {
            this.productimg = productimg;
        }

        public String getBuyeruserimg() {
            return buyeruserimg;
        }

        public void setBuyeruserimg(String buyeruserimg) {
            this.buyeruserimg = buyeruserimg;
        }

        public String getSelleruserimg() {
            return selleruserimg;
        }

        public void setSelleruserimg(String selleruserimg) {
            this.selleruserimg = selleruserimg;
        }
    }
}
