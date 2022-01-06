package com.anhvu.it.chatapp.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "message")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    //Constructors

    public Message() {
    }

    public Message(User owner, Room room, String content) {
        this.owner = owner;
        this.room = room;
        this.content = content;
    }

    //Getters

    public Long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public Room getRoom() {
        return room;
    }

    public String getContent() {
        return content;
    }

    //Setters

    public void set_id(Long _id) {
        this.id = id;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
