package com.anhvu.it.chatapp.data.model;

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

    @Column(length = 250, nullable = true)
    private String content;

    @Temporal(TemporalType.DATE)
    private Date createdDate;


    @Column(name = "type")
    private MessageType type;

}
