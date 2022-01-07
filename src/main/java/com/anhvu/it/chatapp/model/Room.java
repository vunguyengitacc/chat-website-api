package com.anhvu.it.chatapp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
public class Room implements Serializable {

    @Id
    @Column(name = "_id")
    @GeneratedValue
    private Long id;

    @Column(length = 100, nullable = false)
    @NotEmpty(message = "The room name is required")
    @Size(min = 8, max = 50, message = "The room name length must be between 8 and 50 characters")
    private String name;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Member> members;

    //Constructors

    public Room() {
    }

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
