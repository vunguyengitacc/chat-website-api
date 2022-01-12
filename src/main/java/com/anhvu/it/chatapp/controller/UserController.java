package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.dto.UserDTO;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import com.anhvu.it.chatapp.utility.payload.request.PasswordUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/search")
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

    @GetMapping("/{id}")
    public ResponseEntity<MainResponse<UserDTO>> getOneUser(@PathVariable Long id) {
        MainResponse<UserDTO> mainResponse;
        User user = userService.getById(id);
        UserDTO res = new UserDTO();
        res.convert(user);
        mainResponse = new MainResponse<UserDTO>(res, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);

    }

    @GetMapping("/me")
    public ResponseEntity<MainResponse<UserDTO>> getMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        UserDTO res = new UserDTO(user);
        MainResponse<UserDTO> mainResponse = new MainResponse<UserDTO>(res, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/me/friend")
    public ResponseEntity<MainResponse<Set<UserDTO>>> getMyFriend() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        Set<UserDTO> res = new HashSet<UserDTO>();
        for (User item : user.getFriends()) {
            res.add(new UserDTO(item));
        }
        MainResponse<Set<UserDTO>> mainResponse = new MainResponse<Set<UserDTO>>(res, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<MainResponse<UserDTO>> updateMe(@RequestBody User userUpdateData) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getByUsername(username);
        user.update(userUpdateData);
        User updatedUser = userService.saveOne(user);
        UserDTO res = new UserDTO(updatedUser);
        MainResponse<UserDTO> mainResponse = new MainResponse<UserDTO>(res, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me/password")
    public ResponseEntity<MainResponse<UserDTO>> updateMe(@RequestBody PasswordUpdateRequest data) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        if (bCryptPasswordEncoder.matches(data.getCurrentPassword(), user.getPassword())) {
            user.setPassword(data.getNewPassword());
        } else throw new RuntimeException("Wrong current password");
        User updatedUser = userService.saveOne(user);
        UserDTO res = new UserDTO(updatedUser);
        MainResponse<UserDTO> mainResponse = new MainResponse<UserDTO>(res, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PostMapping("/me/request/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> sendRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User me = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.sendRequest(target, me);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me/request/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> acceptRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.acceptRequest(target, me);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/me/request/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> denyRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.denyRequest(target, me);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/me/request/{targetId}/cancel")
    public ResponseEntity<MainResponse<Boolean>> cancelRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.cancelRequest(target, me);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/me/friend/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> removeFriend(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User me = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.removeFriend(target, me);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

}
