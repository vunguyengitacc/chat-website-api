package com.anhvu.it.chatapp.respository.model;

import com.anhvu.it.chatapp.utility.type.MessageType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "message")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
public class Message extends BaseEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(length = 500, nullable = true)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "type")
    private MessageType type;

    @PrePersist
    public void onCreate(){
        long currentTimeMillis = System.currentTimeMillis();
        this.createdDate = new Date(currentTimeMillis);
    }

    @PreUpdate
    public void onUpdate(){
        long currentTimeMillis = System.currentTimeMillis();
        this.updatedDate = new Date(currentTimeMillis);
    }

}
