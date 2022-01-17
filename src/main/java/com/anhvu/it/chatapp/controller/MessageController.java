package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.data.model.Message;
import com.anhvu.it.chatapp.data.model.Room;
import com.anhvu.it.chatapp.data.model.User;
import com.anhvu.it.chatapp.dto.MessageDTO;
import com.anhvu.it.chatapp.service.message.MessageService;
import com.anhvu.it.chatapp.service.room.RoomService;
import com.anhvu.it.chatapp.service.user.UserService;
import com.anhvu.it.chatapp.utility.payload.request.MessageCreatorRequest;
import com.anhvu.it.chatapp.utility.payload.Rrsponse.MainResponse;
import com.anhvu.it.chatapp.utility.type.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/room/{id}")
    public ResponseEntity<MainResponse<List<MessageDTO>>> getInRoom(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getByUsername(username);
        Room room = roomService.getById(id);
        if (!room.isContainsUser(currentUser))
            throw new RuntimeException("Unauthorized");

        MainResponse<List<MessageDTO>> response;
        List<Message> lstMessage = messageService.getInRoom(id);

        List<MessageDTO> rs = new ArrayList<MessageDTO>();

        for (Message i : lstMessage) {
            rs.add(new MessageDTO(i));
        }

        response = new MainResponse<List<MessageDTO>>(rs, "SUCCESS");
        return ResponseEntity.ok().body(response);
    }


    @MessageMapping("/message.create")
    @PostMapping()
    public ResponseEntity<MainResponse<MessageDTO>> createOne(@RequestBody MessageCreatorRequest data) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.getByUsername(username);
        Room room = roomService.getById(data.getRoomId());

        if (!room.isContainsUser(currentUser))
            throw new RuntimeException("Unauthorized");

        MainResponse<MessageDTO> response;

        Message message = new Message();
        message.setOwner(currentUser);
        message.setContent(data.getContent());
        message.setRoom(room);
        message.setType(MessageType.MESSAGE);

        messageService.saveOne(message);

        MessageDTO rs = new MessageDTO(message);

        simpMessagingTemplate.convertAndSend("/room/"+room.getId(), rs);
        response = new MainResponse<MessageDTO>(rs, "SUCCESS");
        return ResponseEntity.ok().body(response);
    }

}
