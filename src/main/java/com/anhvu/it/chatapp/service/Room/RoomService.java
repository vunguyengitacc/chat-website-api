package com.anhvu.it.chatapp.service.Room;

import com.anhvu.it.chatapp.data.model.Room;

import java.util.List;

public interface RoomService {
    public List<Room> getAll();

    public Room getById(long id);

    public List<Room> search(String term);

    public void deleteById(Long id);

    public Room saveOne(Room room);
}
