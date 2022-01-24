package com.anhvu.it.chatapp.data.access;

import com.anhvu.it.chatapp.data.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("room")
public interface RoomDAL extends CrudRepository<Room, Long> {
    List<Room> findAll();

    @Query("SELECT p FROM Room p WHERE p.status = 1 AND p.id=:id")
    Room findById(long id);

    @Query("SELECT p FROM Room p LEFT JOIN p.members s WHERE p.status = 0 AND s.user.id = :id")
    List<Room> findDeletedRoomByUser(@Param("id") Long id);

    @Query("SELECT p FROM Room p WHERE p.status = 0 AND p.type = 2 AND NOT EXISTS (FROM Member s WHERE p.id = s.id.roomId AND s.id.userId NOT IN :ids)")
    Room findDeletedFriendRoomByUsers(@Param("ids") List<Long> id);

    @Query("SELECT p FROM Room p WHERE p.status = 1 AND p.type = 2 AND NOT EXISTS (FROM Member s WHERE p.id = s.id.roomId AND s.id.userId NOT IN :ids)")
    Room findActiveFriendRoomByUsers(@Param("ids") List<Long> id);

    @Query("SELECT p FROM Room p LEFT JOIN p.members s WHERE p.status = 1 AND s.user.id = :id")
    List<Room> findActiveRoomByUser(@Param("id") Long id);

    @Query("SELECT p FROM Room p WHERE p.name LIKE %?1%")
    List<Room> search(String term);
}
