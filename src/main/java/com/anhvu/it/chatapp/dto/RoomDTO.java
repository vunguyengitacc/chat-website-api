package com.anhvu.it.chatapp.dto;

import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.utility.type.RoomStatus;
import com.anhvu.it.chatapp.utility.type.RoomType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoomDTO implements Serializable {

    private Long id;

    private String name;

    private Date createdDate;

    private RoomType type;

    private RoomStatus status;

    private Set<Long> members;

    public RoomDTO(Room input) {
        this.id = input.getId();
        this.name = input.getName();
        this.createdDate = input.getCreatedDate();
        this.type = input.getType();
        this.status = input.getStatus();
        this.members = new HashSet<Long>();
        for (Member i : input.getMembers()) {
            this.members.add(i.getUser().getId());
        }
    }

}
