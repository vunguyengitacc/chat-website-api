package com.anhvu.it.chatapp.data.model;

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
public class Message implements Serializable {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

//    @ManyToOne
//    @JoinColumn(name = "type_id", nullable = true)
//    private MessageType messageType;

//    @Column(name = "type_id")
//    private int typeId;

    //Constructors

    public Message() {
    }

    public Message(User owner, Room room, String content, int typeId) {
        this.owner = owner;
        this.room = room;
        this.content = content;
        //this.typeId = typeId;
    }

    public Message(User owner, Room room, String content, MessageType type) {
        this.owner = owner;
        this.room = room;
        this.content = content;
        //this.typeId = type.getId();
       //this.messageType = type;
    }

}
