package com.anhvu.it.chatapp.data.model;

import com.anhvu.it.chatapp.data.model.id.MemberID;
import com.anhvu.it.chatapp.utility.type.RoleType;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private User user;

    @Column(name = "type")
    private RoleType role;

    //Constructors

    public Member(Room room, User user, RoleType type) {
        this.id = new MemberID(user.getId(), room.getId());
        this.room = room;
        this.user = user;
        this.role = type;
    }


    public Member(Long roomId, Long userId, RoleType type) {
        this.id = new MemberID(userId, roomId);
        this.role = type;
    }

    public Member() {
    }


}
