package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.data.model.Message;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.service.Message.MessageService;
import com.anhvu.it.chatapp.service.Room.RoomService;
import com.anhvu.it.chatapp.service.User.UserService;
import com.anhvu.it.chatapp.utility.payload.request.MessageCreatorRequest;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @GetMapping("/room/{id}")
    public ResponseEntity<MainResponse<List<Message>>> getInRoom(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getByUsername(username);
        Room room = roomService.getById(id);
        if (!room.isContainsUser(currentUser))
            throw new RuntimeException("Unauthorized");

        MainResponse<List<Message>> response;
        List<Message> lstMessage = messageService.getInRoom(id);
        response = new MainResponse<List<Message>>(lstMessage, "SUCCESS");
        return ResponseEntity.ok().body(response);
    }

    @PostMapping()
    public ResponseEntity<MainResponse<Message>> createOne(@RequestBody MessageCreatorRequest data) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getByUsername(username);
        Room room = roomService.getById(data.getRoomId());

        if (!room.isContainsUser(currentUser))
            throw new RuntimeException("Unauthorized");

        MainResponse<Message> response;

        Message message = new Message();
        message.setOwner(currentUser);
        message.setContent(data.getContent());
        message.setRoom(room);
        message.setTypeId(data.getTypeId());

        messageService.saveOne(message);

        response = new MainResponse<Message>(message, "SUCCESS");
        return ResponseEntity.ok().body(response);
    }

}
