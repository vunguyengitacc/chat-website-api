package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import com.anhvu.it.chatapp.utility.payload.request.PasswordUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder ;

    @GetMapping("/search")
    public ResponseEntity<MainResponse<List<User>>> searchUsers(@RequestParam String term) {
        MainResponse<List<User>> mainResponse;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        List<User> lstUsers = userService.search(term);
        lstUsers.remove(user);
        mainResponse = new MainResponse<List<User>>(lstUsers, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainResponse<User>> getOneUser(@PathVariable Long id) {
        MainResponse<User> mainResponse;
        User user = userService.getById(id);
        mainResponse = new MainResponse<User>(user, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);

    }

    @GetMapping("/me")
    public ResponseEntity<MainResponse<User>> getMe() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        System.out.println(user.getName());
        MainResponse<User> mainResponse = new MainResponse<User>(user, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<MainResponse<User>> updateMe(@RequestBody User userUpdateData) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.getByUsername(username);
        System.out.println(user.getUsername());
        user.update(userUpdateData);
        System.out.println(user.getName());
        System.out.println(user.getAddress());
        System.out.println(user.getEmail());
        System.out.println(user.getPhone());
        User updatedUser = userService.saveOne(user);
        MainResponse<User> mainResponse = new MainResponse<User>(updatedUser, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me/password")
    public ResponseEntity<MainResponse<User>> updateMe(@RequestBody PasswordUpdateRequest data) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        if(bCryptPasswordEncoder.matches(data.getCurrentPassword() ,user.getPassword())){
            user.setPassword(data.getNewPassword());
        }
        else throw new RuntimeException("Wrong current password");
        User updatedUser = userService.saveOne(user);
        MainResponse<User> mainResponse = new MainResponse<User>(updatedUser, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PostMapping("/me/request/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> sendRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        System.out.println(targetId);
        User user = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.sendRequest(target, user);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me/request/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> acceptRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.acceptRequest(user, target);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/me/request/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> denyRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.denyRequest(target, user);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/me/request/{targetId}/cancel")
    public ResponseEntity<MainResponse<Boolean>> cancelRequest(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.cancelRequest(target, user);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @DeleteMapping("/me/friend/{targetId}")
    public ResponseEntity<MainResponse<Boolean>> removeFriend(@PathVariable Long targetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        User target = userService.getById(targetId);
        userService.removeFriend(target, user);
        MainResponse<Boolean> mainResponse = new MainResponse<Boolean>(true, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

}
