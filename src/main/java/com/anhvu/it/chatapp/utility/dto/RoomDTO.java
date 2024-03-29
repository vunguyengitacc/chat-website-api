package com.anhvu.it.chatapp.utility.dto;

import com.anhvu.it.chatapp.respository.model.Member;
import com.anhvu.it.chatapp.respository.model.Message;
import com.anhvu.it.chatapp.respository.model.Room;
import com.anhvu.it.chatapp.utility.type.RoomStatus;
import com.anhvu.it.chatapp.utility.type.RoomType;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class RoomDTO implements Serializable {

    private Long id;

    private String name;

    private ArrayList<String> coverImage = new ArrayList<String>();

    private Date createdDate;

    private RoomType type;

    private RoomStatus status;

    private Set<Long> memberIds;

    private Set<Long> messageIds;

    private MessageDTO lastMessage;

    public RoomDTO(Room input) {
        this.id = input.getId();
        this.name = input.getName();
        if(input.getType() == RoomType.FRIEND)
            this.coverImage.add(input.getCoverImage());
        else {
            input.getMembers().stream().limit(4).forEach((a)->{
                this.coverImage.add(a.getUser().getAvatarURI());
            });
        }
        this.createdDate = input.getCreatedDate();
        this.type = input.getType();
        this.status = input.getStatus();
        this.memberIds = new HashSet<Long>();
        for (Member i : input.getMembers()) {
            this.memberIds.add(i.getUser().getId());
        }
        this.messageIds = new HashSet<Long>();
        if (input.getMessages() == null)
            return;
        Iterator<Message> iterator = input.getMessages().stream()
                .sorted((a, b) -> a.getCreatedDate().compareTo(b.getCreatedDate()))
                .iterator();
        Message temp = new Message();
        boolean hasTemp = false;
        while (iterator.hasNext()) {
            hasTemp = true;
            temp = iterator.next();
            this.messageIds.add(temp.getId());
        }
        if (hasTemp) this.lastMessage = new MessageDTO(temp);
    }

}
