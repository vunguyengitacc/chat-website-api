package com.anhvu.it.chatapp.utility.dto;

import com.anhvu.it.chatapp.respository.model.User;
import com.anhvu.it.chatapp.utility.type.RoleType;
import lombok.Data;

@Data
public class MemberDTO {
    public UserDTO user;
    public RoomDTO room;
    public RoleType role;

    public MemberDTO(User user, RoleType role) {
        this.user = new UserDTO(user);
        this.role = role;
    }
}
