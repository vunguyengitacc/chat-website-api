package com.anhvu.it.chatapp.service.room;

import com.anhvu.it.chatapp.data.access.RoomDAL;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.utility.type.RoomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Room temp = new Room();
        temp.setId(id);
        temp.setStatus(RoomStatus.ON_DELETE);
        roomDAL.save(temp);
    }

    @Override
    public Room saveOne(Room room) {
        room.setStatus(RoomStatus.ON_ACTIVE);
        return roomDAL.save(room);
    }

    @Override
    public Room getFriendRoom(User user1, User user2) {
        Set<Long> value = new HashSet<>();
        value.add(user1.getId());
        value.add(user2.getId());
        return roomDAL.findFriend(value);
    }

    @Override
    public void deleteOne(Room room) {
        room.setStatus(RoomStatus.ON_DELETE);
        roomDAL.save(room);
    }

    @Override
    public boolean restartRoom(User user1, User user2) {
        try { Set<Long> value = new HashSet<>();
            value.add(user1.getId());
            value.add(user2.getId());
            Room room = roomDAL.findDeletedFriendRoom(value);
            room.setStatus(RoomStatus.ON_ACTIVE);
            roomDAL.save(room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
