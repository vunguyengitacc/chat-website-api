package com.anhvu.it.chatapp.service.message;

import com.anhvu.it.chatapp.data.access.MessageDAL;
import com.anhvu.it.chatapp.data.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageServiceImplement implements MessageService {

    @Autowired
    MessageDAL messageDAL;

    @Override
    public List<Message> getInRoom(Long id) {
        return messageDAL.findByRoomId(id);
    }

    @Override
    public Message saveOne(Message message) {
        return messageDAL.save(message);
    }
}
