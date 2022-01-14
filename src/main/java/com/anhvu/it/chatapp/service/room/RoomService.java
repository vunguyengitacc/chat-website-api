package com.anhvu.it.chatapp.service.room;

import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;

import java.util.List;

public interface RoomService {
    public List<Room> getAll();

    public Room getById(long id);

    public List<Room> search(String term);

    public void deleteById(Long id);

    public void deleteOne(Room room);

    public boolean restartRoom(User user1, User user2);

    public Room saveOne(Room room);

    public Room getFriendRoom(User user1, User user2);

}
