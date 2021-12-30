package com.anhvu.it.chat.app.DataAccess;

import com.anhvu.it.chat.app.Model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAL extends CrudRepository<Message, Long> {

    List<Message> findByRoomId(Long id);


}
