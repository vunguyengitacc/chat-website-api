package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Service.User.UserService;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        try {
            List<User> lstUsers = userService.search(term);

            mainResponse = new MainResponse<List<User>>(lstUsers, "SUCCESS");
            return ResponseEntity.ok().body(mainResponse);
        } catch (Exception e) {
            mainResponse = new MainResponse<List<User>>("Error: " + e.getMessage(), "SUCCESS", true);
            return ResponseEntity.badRequest().body(mainResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainResponse<User>> getOneUser(@PathVariable Long id) {
        MainResponse<User> mainResponse;
        try {
            User user = userService.getById(id);

            mainResponse = new MainResponse<User>(user, "SUCCESS");
            return ResponseEntity.ok().body(mainResponse);
        } catch (Exception e) {
            mainResponse = new MainResponse<User>("Error: " + e.getMessage(), "SUCCESS", true);
            return ResponseEntity.badRequest().body(mainResponse);
        }
    }

}
