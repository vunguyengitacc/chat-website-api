package com.anhvu.it.chatapp.Model;

import com.anhvu.it.chatapp.Model.ID.MemberID;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
public class Member implements Serializable {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false)),
            @AttributeOverride(name = "roomId", column = @Column(name = "room_id", nullable = false))
    })
    private MemberID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", nullable = false, insertable = false, updatable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "role_id")
    @JsonIgnore
    private int roleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    private Role role;

    //Constructors

    public Member(Room room, User user, int roleId) {
        this.id = new MemberID(user.getId(), room.getId());
        this.room = room;
        this.user = user;
        this.roleId = roleId;
    }


    public Member(Long roomId, Long userId, int roleId) {
        this.id = new MemberID(userId, roomId);
        this.roleId = roleId;
    }

    public Member() {
    }


}
