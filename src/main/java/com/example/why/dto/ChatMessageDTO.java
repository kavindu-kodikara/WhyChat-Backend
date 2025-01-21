package com.example.why.dto;

public class ChatMessageDTO {
    private String message;
    private String dateTime;
    private String side;
    private String status;
    private String loggedUserMobile;
    private String otherUserMobile;
    private String logged_user_id;
    private String other_user_id;

    public ChatMessageDTO() {
    }

    public ChatMessageDTO(String message, String dateTime, String status, String loggedUserMobile, String otherUserMobile, String logged_user_id, String other_user_id) {
        this.message = message;
        this.dateTime = dateTime;
        this.status = status;
        this.loggedUserMobile = loggedUserMobile;
        this.otherUserMobile = otherUserMobile;
        this.logged_user_id = logged_user_id;
        this.other_user_id = other_user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoggedUserMobile() {
        return loggedUserMobile;
    }

    public void setLoggedUserMobile(String loggedUserMobile) {
        this.loggedUserMobile = loggedUserMobile;
    }

    public String getOtherUserMobile() {
        return otherUserMobile;
    }

    public void setOtherUserMobile(String otherUserMobile) {
        this.otherUserMobile = otherUserMobile;
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
}
