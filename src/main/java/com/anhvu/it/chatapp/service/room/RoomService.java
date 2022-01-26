package com.anhvu.it.chatapp.service.room;

import com.anhvu.it.chatapp.respository.model.Room;
import com.anhvu.it.chatapp.respository.model.User;

import java.util.List;

public interface RoomService {
    List<Room> getAll();

    List<Room> getByUser(User bean);

    Room getById(long id);

    List<Room> search(String term);

    void deleteById(Long id);

    void deleteOne(Room room);

    boolean restartRoom(User user1, User user2);

    Room saveOne(Room room);

    Room getFriendRoom(User user1, User user2);

}
