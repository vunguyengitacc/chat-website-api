package com.anhvu.it.chatapp.Model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    //Constructors

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Role() {
    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    //Setters

    public void set_id(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
