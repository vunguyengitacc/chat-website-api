package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.dto.RoomDTO;
import com.anhvu.it.chatapp.service.room.RoomService;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.payload.request.RoomCreatorRequest;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import com.anhvu.it.chatapp.utility.type.RoleType;
import com.anhvu.it.chatapp.utility.type.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;
    @Autowired
    UserService userService;

    @GetMapping("")
    public ResponseEntity<MainResponse<List<RoomDTO>>> getAll() {
        MainResponse<List<RoomDTO>> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Room> data = roomService.getByUser(currentUser);
        List<RoomDTO> rs = new ArrayList<RoomDTO>();
        for (Room i : data) {
            if (i.getType() == RoomType.FRIEND) {
                for (Member j : i.getMembers()) {
                    if (j.getUser().getId() != currentUser.getId()) {
                        i.setName(j.getUser().getName());
                        i.setCoverImage(j.getUser().getAvatarURI());
                    }
                }
            }
            rs.add(new RoomDTO(i));
        }
        mainResponse = new MainResponse<List<RoomDTO>>(rs, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<MainResponse<List<RoomDTO>>> search(@RequestParam String term) {
        MainResponse<List<RoomDTO>> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Room> lstRoom = roomService.search(term);
        List<RoomDTO> rs = new ArrayList<RoomDTO>();
        for (Room i : lstRoom) {
            if (i.getType() == RoomType.FRIEND) {
                for (Member j : i.getMembers()) {
                    if (j.getUser().getId() != currentUser.getId()) {
                        i.setName(j.getUser().getName());
                        i.setCoverImage(j.getUser().getAvatarURI());
                    }
                }
            }
            rs.add(new RoomDTO(i));
        }
        mainResponse = new MainResponse<List<RoomDTO>>(rs, "SUCCESS");
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

        Member owner = new Member(room, currentUser, RoleType.ADMIN);
        room.addMember(owner);
        for (Long i : data.getMembers()) {
            try {
                User u = userService.getById(i);
                Member mem = new Member(room, u, RoleType.MEMBER);
                room.addMember(mem);
            } catch (Exception e) {
            }
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

        for (Member i : room.getMembers()) {
            if (i.getRole() == RoleType.ADMIN && i.getUser().getId() == currentUser.getId()) {
                roomService.deleteById(id);
                mainResponse = new MainResponse<Long>(id, "SUCCESS");
                return ResponseEntity.ok().body(mainResponse);
            }
        }
        throw new RuntimeException("Unauthorized");
    }


}
