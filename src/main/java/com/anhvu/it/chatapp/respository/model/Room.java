package com.anhvu.it.chatapp.respository.model;

import com.anhvu.it.chatapp.utility.type.RoomStatus;
import com.anhvu.it.chatapp.utility.type.RoomType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity implements Serializable {

    @Column(length = 50, nullable = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Member> members;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Message> messages;

    @Column(name = "type", nullable = false)
    private RoomType type;

    @Column(name = "status", nullable = false)
    private RoomStatus status;

    @Column(name = "cover_image", nullable = true)
    private String coverImage;

    @ManyToMany(mappedBy = "askToJoins")
    private Set<User> requests = new HashSet<User>();

    //Constructors

    public Room(String name) {
        this.name = name;
    }

    //Other

    public void addMember(Member member) {
        if (members == null) this.members = new HashSet<Member>();
        this.members.add(member);
    }

    public boolean isContainsUser(User user) {
        for (Member i : members) {
            if (user.getId() == i.getId().getUserId()) {
                return true;
            }
        }
        return false;
    }
}
