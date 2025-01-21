
package com.example.why.entity;

import java.io.Serializable;
import jakarta.persistence.*;

@Entity
@Table(name = "chat_status")
public class ChatStaus implements Serializable{
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "name", length = 20 , nullable = false)
    private String name;

    public ChatStaus() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
