package com.anhvu.it.chat.app.DataAccess;

import com.anhvu.it.chat.app.Model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("room")
public interface RoomDAL extends CrudRepository<Room, Long> {
    public List<Room> findAll();

    public Room findById(long id);
}
