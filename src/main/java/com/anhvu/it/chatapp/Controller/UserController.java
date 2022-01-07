package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Service.User.UserService;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/search")
    public ResponseEntity<MainResponse<List<User>>> searchUsers(@RequestParam String term) {
        MainResponse<List<User>> mainResponse;
        List<User> lstUsers = userService.search(term);

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
        MainResponse<User> mainResponse = new MainResponse<User>(user, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<MainResponse<User>> updateMe(@RequestBody User userUpdateData) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUsername(username);
        user.update(userUpdateData);
        User updatedUser = userService.saveOne(user);
        MainResponse<User> mainResponse = new MainResponse<User>(updatedUser, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }

}
