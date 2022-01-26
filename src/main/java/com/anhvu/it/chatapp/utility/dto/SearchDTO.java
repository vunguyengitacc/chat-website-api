package com.anhvu.it.chatapp.utility.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SearchDTO {
    private Set<UserDTO> users;

    private Set<RoomDTO> rooms;

    public void addUser(UserDTO data) {
        this.users.add(data);
    }

    public void addRoom(RoomDTO data) {
        this.rooms.add(data);
    }
}
