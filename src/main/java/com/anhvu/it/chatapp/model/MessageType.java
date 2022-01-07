package com.anhvu.it.chatapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "message_type")
@Getter
@Setter
public class MessageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "_id")
    private Integer id;

    private String name;

    private String description;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "message_type")
    @JsonIgnore
    private Set<Message> messages;
}
