package com.anhvu.it.chatapp.dto;

import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.Message;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.utility.type.RoomStatus;
import com.anhvu.it.chatapp.utility.type.RoomType;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class RoomDTO implements Serializable {

    private Long id;

    private String name;

    private Date createdDate;

    private RoomType type;

    private RoomStatus status;

    private Set<Long> memberIds;

    private Set<Long> messageIds;

    private MessageDTO lastMessage;

    public RoomDTO(Room input) {
        this.id = input.getId();
        this.name = input.getName();
        this.createdDate = input.getCreatedDate();
        this.type = input.getType();
        this.status = input.getStatus();
        this.memberIds = new HashSet<Long>();
        for (Member i : input.getMembers()) {
            this.memberIds.add(i.getUser().getId());
        }
        this.messageIds = new HashSet<Long>();
        Iterator<Message> iterator = input.getMessages().stream()
                .sorted((a, b) -> a.getCreatedDate().compareTo(b.getCreatedDate()))
                .iterator();
        Message temp = new Message();
        while (iterator.hasNext()) {
            temp = iterator.next();
            this.messageIds.add(temp.getId());
        }
        this.lastMessage = new MessageDTO(temp);
    }

}
