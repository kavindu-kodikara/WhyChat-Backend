
package com.example.why.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
public class User implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "mobile",length = 10,nullable = false)
    private String mobile;
    
    @Column(name = "fname",length = 45,nullable = false)
    private String fname;
    
    @Column(name = "lname",length = 45,nullable = false)
    private String lname;
    
    @Column(name = "password",length = 45,nullable = false)
    private String password;
    
    @Column(name = "registered_datetime",nullable = false)
    private Date registerdDateTime;
    
    @ManyToOne
    @JoinColumn(name = "user_status_id")
    private UserStatus userStatus;

    @Column(name = "last_seen",length = 45,nullable = false)
    private Date last_seen;
    
    public User() {
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public Date getRegisterdDateTime() {
        return registerdDateTime;
    }

    public void setRegisterdDateTime(Date registerdDateTime) {
        this.registerdDateTime = registerdDateTime;
    }

    public Date getLast_seen() {
        return last_seen;
    }

    public void setLast_seen(Date last_seen) {
        this.last_seen = last_seen;
    }

   
}
