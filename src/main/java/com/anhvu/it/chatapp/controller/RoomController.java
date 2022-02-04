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
import com.sun.org.apache.xpath.internal.operations.Bool;
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

    @GetMapping("/{id}/requests")
    public ResponseEntity<MainResponse<List<UserDTO>>> getRequestToJoin(@PathVariable Long id) {
        MainResponse<List<UserDTO>> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        boolean flag = false;
        for (Member i : room.getMembers()) {
            if (i.getRole() == RoleType.ADMIN && i.getUser().getId() == currentUser.getId()) {
                flag = true;
            }
        }
        if (flag == false) throw new RuntimeException("Unauthorized");
        List<UserDTO> data = new ArrayList<UserDTO>();
        for (User item : room.getRequests()) {
            data.add(new UserDTO(item));
        }
        mainResponse = new MainResponse<List<UserDTO>>(data, "SUCCESS");
        return ResponseEntity.ok(mainResponse);
    }

    @PostMapping("/{id}/request")
    public ResponseEntity<MainResponse<Boolean>> askToJoin(@PathVariable Long id) {
        MainResponse<Boolean> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        for (Member i : room.getMembers()) {
            if (i.getUser().getId() == currentUser.getId()) {
                throw new RuntimeException("You have been already a member of this room");
            }
        }
        room.addRequest(currentUser);
        roomService.saveOne(room);
        mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok(mainResponse);
    }

    @DeleteMapping("/{id}/request/{userId}")
    public ResponseEntity<MainResponse<Boolean>> denyRequest(@PathVariable("id") Long id, @PathVariable("userId") Long userId) {
        MainResponse<Boolean> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        User target = userService.getById(userId);
        for (Member i : room.getMembers()) {
            if (i.getRole() == RoleType.ADMIN && i.getUser().getId() == currentUser.getId()) {
                throw new RuntimeException("Unauthorized");
            }
        }
        room.removeRequest(target);
        roomService.saveOne(room);
        mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok(mainResponse);
    }

    @DeleteMapping("/{id}/request/me")
    public ResponseEntity<MainResponse<Boolean>> cancelRequest(@PathVariable Long id) {
        MainResponse<Boolean> mainResponse;
        User currentUser = userService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Room room = roomService.getById(id);
        boolean flag = false;
        for (User i : room.getRequests()) {
            if (i.getId() == currentUser.getId()) {
                flag = true;
            }
        }
        if(flag == false) throw new RuntimeException("The request not found");
        room.removeRequest(currentUser);
        roomService.saveOne(room);
        mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok(mainResponse);
    }

}
