package com.anhvu.it.chat.app.Service.Room;

import com.anhvu.it.chat.app.Model.Room;
import org.springframework.stereotype.Component;

import java.util.List;

public interface RoomService {
    public List<Room> getAll();

    public Room getById(long id);
}
