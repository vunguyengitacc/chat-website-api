package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.service.Room.RoomService;
import com.anhvu.it.chatapp.service.User.UserService;
import com.anhvu.it.chatapp.utility.payload.request.RoomCreatorRequest;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<MainResponse<List<Room>>> getAll() {
        MainResponse<List<Room>> mainResponse;
        mainResponse = new MainResponse<List<Room>>(roomService.getAll(), "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<MainResponse<List<Room>>> search(@RequestParam String term) {
        MainResponse<List<Room>> mainResponse;
        List<Room> lstRoom = roomService.search(term);

        mainResponse = new MainResponse<List<Room>>(lstRoom, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainResponse<Room>> getById(@PathVariable Long id) {
        MainResponse<Room> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        for (Member i : room.getMembers()) {
            if (i.getUser().getId() == currentUser.getId()) {
                mainResponse = new MainResponse<Room>(room, "SUCCESS");
                return ResponseEntity.ok().body(mainResponse);
            }
        }
        throw new RuntimeException("Unauthorized");
    }

    @PostMapping("")
    public ResponseEntity<MainResponse<Room>> addOne(@RequestBody RoomCreatorRequest data) {
        MainResponse<Room> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room temp = data.getRoom();
        temp.setMembers(new HashSet<Member>());
        Room room = roomService.saveOne(temp);
        System.out.println(currentUser.getUsername());
        Member owner = new Member(room, currentUser, 1);
        room.addMember(owner);
        for (Long i : data.getMembers()) {
            try {
                User u = userService.getById(i);
                Member mem = new Member(room, u, 1);
                room.addMember(mem);
            } catch (Exception e) {}
        }
        room = roomService.saveOne(room);
        mainResponse = new MainResponse<Room>(room, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MainResponse<Long>> deleteOne(@PathVariable Long id) {
        MainResponse<Long> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        System.out.println(room.getId());
        for (Member i : room.getMembers()) {
            if (i.getRole().getId() == 1 && i.getUser().getId() == currentUser.getId()) {
                roomService.deleteById(id);
                mainResponse = new MainResponse<Long>(id, "SUCCESS");
                return ResponseEntity.ok().body(mainResponse);
            }
        }
        throw new RuntimeException("Unauthorized");
    }


}
