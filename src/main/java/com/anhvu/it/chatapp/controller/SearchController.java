package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.respository.model.Member;
import com.anhvu.it.chatapp.respository.model.Room;
import com.anhvu.it.chatapp.respository.model.User;
import com.anhvu.it.chatapp.utility.dto.RoomDTO;
import com.anhvu.it.chatapp.utility.dto.SearchDTO;
import com.anhvu.it.chatapp.utility.dto.UserDTO;
import com.anhvu.it.chatapp.service.room.RoomService;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.payload.response.MainResponse;
import com.anhvu.it.chatapp.utility.type.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @GetMapping("/users/suggest")
    public ResponseEntity<MainResponse<Set<UserDTO>>> suggest(){
        MainResponse<Set<UserDTO>> mainResponse;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<UserDTO> data = new HashSet<UserDTO>();
        User currentUser = userService.getByUsername(username);
        List<User> lstUsers = userService.suggest(currentUser);
        lstUsers.remove(currentUser);
        for (User i : lstUsers) {
            UserDTO temp = new UserDTO();
            temp.convert(i);
            data.add(temp);
        }
        mainResponse = new MainResponse<Set<UserDTO>>(data, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("")
    public ResponseEntity<MainResponse<SearchDTO>> search(@RequestParam String term) {
        MainResponse<SearchDTO> mainResponse;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SearchDTO data = new SearchDTO();
        User currentUser = userService.getByUsername(username);
        List<User> lstUsers = userService.search(term);
        lstUsers.remove(currentUser);
        data.setUsers(new HashSet<UserDTO>());
        for (User i : lstUsers) {
            UserDTO temp = new UserDTO();
            temp.convert(i);
            data.addUser(temp);
        }
        List<Room> lstRoom = roomService.search(term);
        data.setRooms(new HashSet<RoomDTO>());
        for (Room i : lstRoom) {
            if (i.getType() == RoomType.FRIEND) {
                for (Member j : i.getMembers()) {
                    if (j.getUser().getId() != currentUser.getId()) {
                        i.setName(j.getUser().getName());
                        i.setCoverImage(j.getUser().getAvatarURI());
                    }
                }
            }
            data.addRoom(new RoomDTO(i));
        }
        mainResponse = new MainResponse<SearchDTO>(data, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<MainResponse<Set<UserDTO>>> searchUsers(@RequestParam String term) {
        MainResponse<Set<UserDTO>> mainResponse;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        List<User> lstUsers = userService.search(term);
        lstUsers.remove(user);
        Set<UserDTO> lstRes = new HashSet<UserDTO>();
        for (User i : lstUsers) {
            UserDTO temp = new UserDTO();
            temp.convert(i);
            lstRes.add(temp);
        }
        mainResponse = new MainResponse<Set<UserDTO>>(lstRes, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<MainResponse<List<RoomDTO>>> searchRooms(@RequestParam String term) {
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
}
