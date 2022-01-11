package com.anhvu.it.chatapp.dto;

import com.anhvu.it.chatapp.data.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String avatarURI;

    private Set<Long> friends = new HashSet<Long>();

    private Set<Long> requests = new HashSet<Long>();

    private Set<Long> waits = new HashSet<Long>();

    private String phone;

    private String address;

    private String bio;

    public UserDTO(User input) {
        this.id = input.getId();
        this.username = input.getUsername();
        this.email = input.getEmail();
        this.name = input.getName();
        this.address = input.getAddress();
        this.bio = input.getBio();
        this.phone = input.getPhone();
        this.avatarURI = input.getAvatarURI();
        if (input.getRequests() != null)
            for (User i : input.getRequests()) {
                this.requests.add(i.getId());
            }
        if (input.getFriends() != null) {
            for (User i : input.getFriends()) {
                this.friends.add(i.getId());
            }
        }
        if (input.getWaits() != null) {
            for (User i : input.getWaits()) {
                this.waits.add(i.getId());
            }
        }

    }

    //Other
    public void convert(User input) {
        this.id = input.getId();
        this.username = input.getUsername();
        this.email = input.getEmail();
        this.name = input.getName();
        this.address = input.getAddress();
        this.bio = input.getBio();
        this.phone = input.getPhone();
        this.avatarURI = input.getAvatarURI();
        if (input.getRequests() != null)
            for (User i : input.getRequests()) {
                this.requests.add(i.getId());
            }
        if (input.getFriends() != null) {
            for (User i : input.getFriends()) {
                this.friends.add(i.getId());
            }
        }
        if (input.getWaits() != null) {
            for (User i : input.getWaits()) {
                this.waits.add(i.getId());
            }
        }

    }
}
