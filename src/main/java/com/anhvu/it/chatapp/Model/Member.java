package com.anhvu.it.chatapp.Model;

import com.anhvu.it.chatapp.Model.ID.MemberID;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Member implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
            @AttributeOverride(name = "roomId", column = @Column(name = "room_id", nullable = false))
    })
    private MemberID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false, insertable = false, updatable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    private Role role;

    //Constructors

    public Member(Room room, User user) {
        this.room = room;
        this.user = user;
        this.id = new MemberID(user.getId(), room.getId());
    }

    public Member() {
    }

    //Getters

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public MemberID getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    //Setters

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(MemberID id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //Another
}
