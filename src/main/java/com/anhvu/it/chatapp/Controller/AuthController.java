package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Service.User.UserService;
import com.anhvu.it.chatapp.Util.JWTProvider.JWTProvider;
import com.anhvu.it.chatapp.Util.WebPayload.Request.RegisterRequest;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<MainResponse> register(@RequestBody RegisterRequest user, HttpServletRequest request) {
        MainResponse<User> mainResponse;
        if (user.getAvatarURI().equals(""))
            user.generateURI();
        User temp = new User();
        temp.setUsername(user.getUsername());
        temp.setPassword(user.getPassword());
        temp.setName(user.getName());
        temp.setAvatarURI(user.getAvatarURI());

        User rs = userService.saveOne(temp);

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
