package com.anhvu.it.chatapp.dto;

import com.anhvu.it.chatapp.data.model.Message;
import com.anhvu.it.chatapp.utility.type.MessageType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class MessageDTO {

    private Long id;

    private UserDTO owner;

    private Long roomId;

    private String content;

    private Date createdDate;

    private MessageType type;

    public MessageDTO(Message input){
        this.id = input.getId();
        this.owner = new UserDTO(input.getOwner());
        this.roomId = input.getRoom().getId();
        this.content = input.getContent();
        this.createdDate = input.getCreatedDate();
        this.type = input.getType();
    }
}
