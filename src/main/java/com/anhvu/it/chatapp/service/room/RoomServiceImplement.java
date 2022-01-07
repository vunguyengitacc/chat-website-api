package com.anhvu.it.chatapp.service.room;

import com.anhvu.it.chatapp.data.access.RoomDAL;
import com.anhvu.it.chatapp.data.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoomServiceImplement implements RoomService {

    @Autowired
    RoomDAL roomDAL;

    @Override
    public List<Room> getAll() {
        return roomDAL.findAll();
    }

    @Override
    public Room getById(long id) {
        return roomDAL.findById(id);
    }

    @Override
    public List<Room> search(String term) {
        return roomDAL.search(term);
    }

    @Override
    public void deleteById(Long id) {
        roomDAL.deleteById(id);
    }

    @Override
    public Room saveOne(Room room) {
        return roomDAL.save(room);
    }
}
