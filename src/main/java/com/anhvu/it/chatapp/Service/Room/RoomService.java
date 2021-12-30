package com.anhvu.it.chatapp.Service.Room;

import com.anhvu.it.chatapp.Model.Room;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RoomService {
    public List<Room> getAll();

    public Room getById(long id);
}
