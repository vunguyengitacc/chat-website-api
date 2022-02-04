package com.anhvu.it.chatapp.respository.model;

import com.anhvu.it.chatapp.utility.builder.user.UserBuilder;
import com.anhvu.it.chatapp.utility.builder.user.UserBuilderImplement;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"password"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable, UserDetails {

    @Column(length = 20, nullable = false, unique = true)
    @Pattern(regexp = "[a-zA-Z0-9]{8,20}", message = "The username is only contain letter or number")
    @Size(min = 8, max = 20, message = "The username length must be between 8 and 20 characters")
    private String username;

    @Column(length = 200, nullable = false)
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "friend",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "friend_id")}
    )
    private Set<User> friends;

    /**
     * request from another user to become friend
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "friend_request",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_request_id")}
    )
    private Set<User> requests;

    /**
     * waiting another user accept request to become friend
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "friend_waits",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_wait_id")}
    )
    private Set<User> waits;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "room_request",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "room_id")}
    )
    private Set<Room> askToJoins = new HashSet<Room>();

    @Column(length = 10)
    private String phone;

    @Column(length = 200)
    private String address;

    @Column(length = 300)
    private String bio;

    @Transient
    private UserBuilder builder = new UserBuilderImplement(this);

    //Other
    public void update(User updateData) {
        if (updateData.getPassword() != null && !updateData.getPassword().equals(""))
            this.password = updateData.getPassword();
        if (updateData.getAvatarURI() != null && !updateData.getAvatarURI().equals(""))
            this.avatarURI = updateData.getAvatarURI();
        if (updateData.getName() != null && !updateData.getName().equals(""))
            this.name = updateData.getName();
        if (updateData.getEmail() != null && !updateData.getEmail().equals(""))
            this.email = updateData.getEmail();
        if (updateData.getPhone() != null && !updateData.getPhone().equals(""))
            this.phone = updateData.getPhone();
        if (updateData.getAddress() != null && !updateData.getAddress().equals(""))
            this.address = updateData.getAddress();
        if (updateData.getBio() != null && !updateData.getBio().equals(""))
            this.bio = updateData.getBio();
    }

    public void addFriend(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.friends == null) this.friends = new HashSet<>();
        this.friends.add(target);
    }

    public void addRequest(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.requests == null) this.requests = new HashSet<>();
        this.requests.add(target);
    }

    public void addWait(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.waits == null) this.waits = new HashSet<>();
        this.waits.add(target);
    }

    public void removeRequest(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.requests == null) {
            this.requests = new HashSet<>();
            throw new RuntimeException("Friend request is not exist");
        }
        for (User item : this.requests) {
            if (item.getUsername().equals(target.getUsername())) {
                this.requests.remove(target);
                return;
            }
        }
        throw new RuntimeException("Friend request is not exist");
    }

    public void removeFiend(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.friends == null) {
            this.friends = new HashSet<>();
            throw new RuntimeException("Friend is not exist");
        }
        for (User item : this.friends) {
            if (item.getUsername().equals(target.getUsername())) {
                this.friends.remove(target);
                return;
            }
        }
        throw new RuntimeException("Friend is not exist");
    }

    public void removeWait(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.waits == null) {
            this.waits = new HashSet<>();
            throw new RuntimeException("Friend waiting is not exist");
        }
        for (User item : this.waits) {
            if (item.getUsername().equals(target.getUsername())) {
                this.waits.remove(target);
                return;
            }
        }
        throw new RuntimeException("Friend waiting is not exist");
    }

    //Spring security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
