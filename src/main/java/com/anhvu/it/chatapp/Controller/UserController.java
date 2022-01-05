package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Service.User.UserService;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{term}")
    public ResponseEntity<MainResponse<List<User>>> searchUsers(@PathVariable String term){
        MainResponse<List<User>> mainResponse;
        try {
            List<User> lstUsers = userService.search(term);

            mainResponse = new MainResponse<List<User>>(lstUsers, "SUCCESS");
            return new ResponseEntity<>(mainResponse, HttpStatus.OK);
        } catch (Exception e){
            mainResponse = new MainResponse<List<User>>("Error: " + e.getMessage(), "SUCCESS", true);
            return new ResponseEntity<>(mainResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
