package com.anhvu.it.chatapp.data.access;

import com.anhvu.it.chatapp.data.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAL extends CrudRepository<Message, Long> {

    List<Message> findByRoomId(Long id);


}
