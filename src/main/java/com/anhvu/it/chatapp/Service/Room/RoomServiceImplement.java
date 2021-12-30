package com.anhvu.it.chatapp.Service.Room;

import com.anhvu.it.chatapp.DataAccess.RoomDAL;
import com.anhvu.it.chatapp.Model.Room;
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
}
