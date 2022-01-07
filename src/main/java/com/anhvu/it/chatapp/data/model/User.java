package com.anhvu.it.chatapp.data.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"password"})
@Getter
@Setter
public class User implements Serializable {

    @Id
    @Column(name = "_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false, unique = true)
    @Pattern(regexp = "[a-zA-Z0-9]{8,20}", message = "The username is only contain letter or number")
    @Size(min = 8, max = 20, message = "The username length must be between 8 and 20 characters")
    private String username;

    @Column(length = 200, nullable = false)
//    @Pattern(regexp = "\"^(?=.*[A-Za-z])[A-Za-z\\d@$!%*#?&]$\"", message = "At least one letter")
//    @Pattern(regexp = "\"^(?=.*\\d)[A-Za-z\\d@$!%*#?&]$\"", message = "At least one number")
//    @Pattern(regexp = "\"^(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]$\"", message = "At least one special character")
 //   @Size(min = 8, max = 20, message = "The password length must be between 8 and 20 characters")
    @NotEmpty(message = "The password is required")
    private String password;

    @Column(length = 100, nullable = false)
    @NotEmpty(message = "The name is required")
    private String name;

    @Column(length = 100, nullable = true)
    private String email;

    @Column(length = 300, nullable = false)
    private String avatarURI;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Member> members;

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

    //Other
    public void update(User updateData){
        this.name = updateData.getName();
        this.email = updateData.getEmail();
        this.avatarURI = updateData.getAvatarURI();
    }

}
