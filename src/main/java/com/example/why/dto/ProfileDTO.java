package com.example.why.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProfileDTO {

    private String id ;
    private String fname ;
    private String lname;
    private String password;
    private MultipartFile avatarImage;

    public ProfileDTO() {
    }

    public ProfileDTO(String id, String fname, String lname, String password, MultipartFile avatarImage) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.avatarImage = avatarImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MultipartFile getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(MultipartFile avatarImage) {
        this.avatarImage = avatarImage;
    }
}
