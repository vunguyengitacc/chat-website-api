package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.Member;
import com.anhvu.it.chatapp.Model.Role;
import com.anhvu.it.chatapp.Model.Room;
import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Service.Room.RoomService;
import com.anhvu.it.chatapp.Service.User.UserService;
import com.anhvu.it.chatapp.Util.WebPayload.Request.RoomCreatorRequest;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
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
