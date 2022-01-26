package com.anhvu.it.chatapp.service.message;

import com.anhvu.it.chatapp.respository.model.Message;

import java.util.List;

public interface MessageService {
    List<Message> getInRoom(Long id);

    Message saveOne(Message message);
}
