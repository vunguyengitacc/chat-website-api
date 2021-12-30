package com.anhvu.it.chatapp.DataAccess;

import com.anhvu.it.chatapp.Model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAL extends CrudRepository<Message, Long> {

    List<Message> findByRoomId(Long id);


}
