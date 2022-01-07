package com.anhvu.it.chatapp.dataaccess;

import com.anhvu.it.chatapp.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("room")
public interface RoomDAL extends CrudRepository<Room, Long> {
    public List<Room> findAll();

    public Room findById(long id);

    @Query("SELECT p FROM Room p WHERE p.name LIKE %?1%")
    public List<Room> search(String term);
}
