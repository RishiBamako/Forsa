package com.bartering.forsa.retrofit.service_model;

public class Comman_ServiceModel {
    /**
     * status : true
     * message : Country have been updated successfully.
     */
    private String status;
    private String message;

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
}
