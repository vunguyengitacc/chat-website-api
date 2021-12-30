package com.anhvu.it.chatapp.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room implements Serializable {

    @Id
    @Column(name = "_id")
    @GeneratedValue
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    private Date createdDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "room")
    private Set<RoomDetail> roomDetails;

    //Constructors

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Set<RoomDetail> getRoomDetails() {
        return roomDetails;
    }
    //Setters

    public void set_id(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setRoomDetails(Set<RoomDetail> roomDetails) {
        this.roomDetails = roomDetails;
    }
}
