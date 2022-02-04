package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.respository.model.Member;
import com.anhvu.it.chatapp.respository.model.Room;
import com.anhvu.it.chatapp.respository.model.User;
import com.anhvu.it.chatapp.utility.dto.RoomDTO;
import com.anhvu.it.chatapp.service.room.RoomService;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.dto.UserDTO;
import com.anhvu.it.chatapp.utility.payload.request.RoomCreatorRequest;
import com.anhvu.it.chatapp.utility.payload.response.MainResponse;
import com.anhvu.it.chatapp.utility.type.RoleType;
import com.anhvu.it.chatapp.utility.type.RoomStatus;
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

    @GetMapping("/friend/{id}")
    public ResponseEntity<MainResponse<RoomDTO>> getFriendRoom(@PathVariable("id") Long id) {
        MainResponse<RoomDTO> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        User target = userService.getById(id);

        Room data = roomService.getFriendRoom(currentUser, target);
        data.setCoverImage(target.getAvatarURI());
        data.setName(target.getName());

        mainResponse = new MainResponse<RoomDTO>(new RoomDTO(data), "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainResponse<RoomDTO>> getById(@PathVariable Long id) {
        MainResponse<RoomDTO> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        for (Member i : room.getMembers()) {
            if (i.getUser().getId() == currentUser.getId()) {
                mainResponse = new MainResponse<RoomDTO>(new RoomDTO(room), "SUCCESS");
                return ResponseEntity.ok().body(mainResponse);
            }
        }
        throw new RuntimeException("Unauthorized");
    }

    @PostMapping("")
    public ResponseEntity<MainResponse<RoomDTO>> addOne(@RequestBody RoomCreatorRequest data) {
        MainResponse<RoomDTO> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room temp = new Room();
        temp.setName(data.getName());
        temp.setStatus(RoomStatus.ON_ACTIVE);
        temp.setType(data.getType());
        temp.setMembers(new HashSet<Member>());
        Room room = roomService.saveOne(temp);

        Member owner = new Member(room, currentUser, RoleType.ADMIN);
        room.addMember(owner);
        for (Long i : data.getMemberIds()) {
            try {
                User u = userService.getById(i);
                Member mem = new Member(room, u, RoleType.MEMBER);
                room.addMember(mem);
            } catch (Exception e) {
            }
        }
        room = roomService.saveOne(room);
        mainResponse = new MainResponse<RoomDTO>(new RoomDTO(room), "SUCCESS");
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

//    @GetMapping("/{id}/request")
//    public void getRequestToJoin(@PathVariable Long id) {
//        MainResponse<RoomDTO> mainResponse;
//        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
//        Room room = roomService.getById(id);
//
//    }

}
