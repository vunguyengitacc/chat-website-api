package com.anhvu.it.chatapp.service.Message;

import com.anhvu.it.chatapp.model.Message;

import java.util.List;

public interface MessageService {
    public List<Message> getInRoom(Long id);

    public Message saveOne(Message message);
}
