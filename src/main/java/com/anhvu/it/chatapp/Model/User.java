package com.anhvu.it.chatapp.Model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 20, nullable = false, unique = true)
    @Pattern(regexp = "[a-zA-Z0-9]{8,20}", message = "The username length must be between 8 and 20 char")
    private String username;
    @Column(length = 50, nullable = false)
    @Pattern(regexp = "\"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$\"", message = "The password length must be between 8 and 20 char, at least one letter, one number and one special character")
    private String password;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = true)
    private String email;
    @Column(length = 300, nullable = false)
    private String avatarURI;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<RoomDetail> roomDetails;

    //Constructors
    public User() {
    }

    public User(String username, String password, String name, String email, String avatarURI) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.avatarURI = avatarURI;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Getters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatarURI() {
        return avatarURI;
    }

    public Set<RoomDetail> getRoomDetails() {
        return roomDetails;
    }
    //Setters


    public void set_id(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
    }

    public void setRoomDetails(Set<RoomDetail> roomDetails) {
        this.roomDetails = roomDetails;
    }
}
