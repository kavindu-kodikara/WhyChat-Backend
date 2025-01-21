
package com.example.why.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class Chat implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;
    
    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;
    
    @Column(name = "message",nullable = false)
    private String message;
    
    @Column(name = "date_time",nullable = false)
    private Date dateTime;
    
    @ManyToOne
    @JoinColumn(name = "chat_status_id")
    private ChatStaus chatStaus;

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public ChatStaus getChatStaus() {
        return chatStaus;
    }

    public void setChatStaus(ChatStaus chatStaus) {
        this.chatStaus = chatStaus;
    }
    
    
    

}
