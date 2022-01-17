package com.anhvu.it.chatapp.data.access;

import com.anhvu.it.chatapp.data.model.Room;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository("room")
public interface RoomDAL extends CrudRepository<Room, Long> {
    public List<Room> findAll();

    @Query("SELECT p FROM Room p WHERE p.status = 1 AND p.id=:id")
    public Room findById(long id);

    @Query("SELECT p FROM Room p LEFT JOIN p.members s WHERE p.status = 0 AND s.user.id = :id")
    public List<Room> findDeletedRoomByUser(@Param("id") Long id);

    @Query("SELECT p FROM Room p LEFT JOIN p.members s WHERE p.status = 1 AND s.user.id = :id")
    public List<Room> findActiveRoomByUser(@Param("id") Long id);

    @Query("SELECT p FROM Room p WHERE p.name LIKE %?1%")
    public List<Room> search(String term);
}
