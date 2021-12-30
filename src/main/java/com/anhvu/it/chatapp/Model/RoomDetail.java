package com.anhvu.it.chatapp.Model;

import com.anhvu.it.chat.app.Model.ID.RoomDetailID;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "room_detail")
public class RoomDetail implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
            @AttributeOverride(name = "roomId", column = @Column(name = "room_id", nullable = false))
    })
    private RoomDetailID id;
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false, insertable = false, updatable = false)
    private Room room;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    private Role role;

    //Constructors

    public RoomDetail(Room room, User user) {
        this.room = room;
        this.user = user;
        this.id = new RoomDetailID(user.getId(), room.getId());
    }

    public RoomDetail() {
    }

    //Getters

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public RoomDetailID getId() {
        return id;
    }
//Setters

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(RoomDetailID id) {
        this.id = id;
    }
}
