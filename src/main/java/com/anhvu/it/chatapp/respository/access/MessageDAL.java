package com.anhvu.it.chatapp.respository.access;

import com.anhvu.it.chatapp.respository.model.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDAL extends CrudRepository<Message, Long> {

    @Query("SELECT p FROM Message p LEFT JOIN p.room s WHERE s.id = :id")
    List<Message> findByRoomId(@Param("id") Long id);


}
