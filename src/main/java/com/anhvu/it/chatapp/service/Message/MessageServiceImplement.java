package com.anhvu.it.chatapp.service.Message;

import com.anhvu.it.chatapp.dataaccess.MessageDAL;
import com.anhvu.it.chatapp.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageServiceImplement implements MessageService {

    @Autowired
    MessageDAL messageDAL;

    @Override
    public List<Message> getInRoom(Long id) {
        return null;
    }

    @Override
    public Message saveOne(Message message) {
        return messageDAL.save(message);
    }
}
