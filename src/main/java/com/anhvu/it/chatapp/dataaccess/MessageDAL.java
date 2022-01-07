package com.anhvu.it.chatapp.dataaccess;

import com.anhvu.it.chatapp.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAL extends CrudRepository<Message, Long> {

    List<Message> findByRoomId(Long id);


}
