package com.anhvu.it.chatapp.controller;

import com.anhvu.it.chatapp.respository.model.Member;
import com.anhvu.it.chatapp.respository.model.Room;
import com.anhvu.it.chatapp.service.room.RoomService;
import com.anhvu.it.chatapp.utility.dto.MemberDTO;
import com.anhvu.it.chatapp.utility.dto.UserDTO;
import com.anhvu.it.chatapp.utility.payload.response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    RoomService roomService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<MainResponse<List<MemberDTO>>> getInRoom(@PathVariable("roomId") Long roomId){
        MainResponse<List<MemberDTO>> mainResponse;
        Room room = roomService.getById(roomId);
        List<MemberDTO> data = new ArrayList<MemberDTO>();
        for(Member item : room.getMembers()){
            data.add(new MemberDTO(item.getUser(), item.getRole()));
        }
        mainResponse = new MainResponse<List<MemberDTO>>(data, "SUCCESS");
        return ResponseEntity.ok().body(mainResponse);
    }
}
