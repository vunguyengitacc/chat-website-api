package com.anhvu.it.chatapp.service.message;

import com.anhvu.it.chatapp.data.model.Message;

import java.util.List;

public interface MessageService {
    public List<Message> getInRoom(Long id);

    public Message saveOne(Message message);
}
