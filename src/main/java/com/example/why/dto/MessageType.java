package com.example.why.dto;

public class MessageType {
    private String type;
    private String mobileNumber;
    private String logged_user_id;
    private String other_user_id;
    private String message;

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLogged_user_id() {
        return logged_user_id;
    }

    public void setLogged_user_id(String logged_user_id) {
        this.logged_user_id = logged_user_id;
    }

    public String getOther_user_id() {
        return other_user_id;
    }

    public void setOther_user_id(String other_user_id) {
        this.other_user_id = other_user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

