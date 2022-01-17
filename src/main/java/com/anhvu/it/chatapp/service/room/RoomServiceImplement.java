package com.anhvu.it.chatapp.service.room;

import com.anhvu.it.chatapp.data.access.MemberDAL;
import com.anhvu.it.chatapp.data.access.RoomDAL;
import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.data.model.id.MemberID;
import com.anhvu.it.chatapp.utility.type.RoomStatus;
import com.anhvu.it.chatapp.utility.type.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RoomServiceImplement implements RoomService {

    @Autowired
    RoomDAL roomDAL;


    @Override
    public List<Room> getAll() {
        return roomDAL.findAll();
    }

    @Override
    public List<Room> getByUser(User bean) {
        return roomDAL.findActiveRoomByUser(bean.getId());
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
        List<Room> rooms = roomDAL.findActiveRoomByUser(user1.getId());
        List<Room> afterFilter = rooms.stream().filter(i -> i.getType() == RoomType.FRIEND && i.getStatus() == RoomStatus.ON_ACTIVE && i.getMembers().stream().filter(e -> e.getUser().getId() == user2.getId()).collect(Collectors.toList()).size() > 0).collect(Collectors.toList());
        Room temp = afterFilter.get(0);
        return temp;
    }

    @Override
    public void deleteOne(Room room) {
        room.setStatus(RoomStatus.ON_DELETE);
        roomDAL.save(room);
    }

    @Override
    public boolean restartRoom(User user1, User user2) {
        try {
            List<Room> rooms = roomDAL.findDeletedRoomByUser(user1.getId());
            List<Room> afterFilter = rooms.stream().filter(i -> i.getType() == RoomType.FRIEND && i.getStatus() == RoomStatus.ON_DELETE && i.getMembers().stream().filter(e -> e.getUser().getId() == user2.getId()).collect(Collectors.toList()).size() > 0).collect(Collectors.toList());
            Room room = afterFilter.get(0);
            room.setStatus(RoomStatus.ON_ACTIVE);
            roomDAL.save(room);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
