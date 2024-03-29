package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.respository.model.User;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.jwt.JWTProvider;
import com.anhvu.it.chatapp.utility.payload.request.RegisterRequest;
import com.anhvu.it.chatapp.utility.payload.response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<MainResponse> register(@RequestBody RegisterRequest user, HttpServletRequest request) {
        MainResponse<User> mainResponse;

        if (user.getAvatarURI().equals(""))
            user.generateURI();

        User temp = new User().getBuilder()
                .withName(user.getName()).withAvatar(user.getAvatarURI())
                .registerUsername(user.getUsername()).registerPassword(user.getPassword())
                .build();

        User rs = userService.createOne(temp);

        JWTProvider jwtProvider = new JWTProvider();
        jwtProvider.setData(rs.getUsername());
        jwtProvider.setNextExpired(Long.valueOf(1000000000));
        jwtProvider.setIssuer(request.getRequestURL().toString());
        jwtProvider.generate();

        Map<String, String> data = new HashMap<>();
        data.put("access_token", jwtProvider.getToken());

        mainResponse = new MainResponse(data, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }


}
