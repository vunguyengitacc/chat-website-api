package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Service.User.UserService;
import com.anhvu.it.chatapp.Util.WebPayload.Request.RegisterRequest;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<MainResponse<User>> register(@RequestBody RegisterRequest user) {
        MainResponse<User> mainResponse;
        try {
            if (user.getAvatarURI().equals(""))
                user.generateURI();
            User temp = new User();
            temp.setUsername(user.getUsername());
            temp.setPassword(user.getPassword());
            temp.setName(user.getName());
            temp.setAvatarURI(user.getAvatarURI());

            User rs = userService.createOne(temp);

            mainResponse = new MainResponse<User>(rs, "SUCCESS");
            return ResponseEntity.ok().body(mainResponse);
        } catch (ConstraintViolationException ex) {
            String mess = ex.getConstraintViolations().stream().map(i -> i.getMessage()).collect(Collectors.joining("/", "", ""));
            ex.getConstraintViolations().stream().toArray();
            mainResponse = new MainResponse<User>(mess, "FAILED", true);
            return ResponseEntity.badRequest().body(mainResponse);
        } catch (Exception e) {
            mainResponse = new MainResponse<User>("Error: " + e.getMessage(), "FAILED", true);
            return ResponseEntity.badRequest().body(mainResponse);
        }
    }

    @PostMapping("/login")
    public void login(@RequestBody User user) {
        System.out.println("HELLo");
    }

}
