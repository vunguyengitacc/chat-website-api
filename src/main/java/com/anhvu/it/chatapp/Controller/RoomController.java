package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.Room;
import com.anhvu.it.chatapp.Service.Room.RoomService;
import com.anhvu.it.chatapp.Util.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MainResponse<List<Room>>> getAll(){
        MainResponse<List<Room>> mainResponse;
        try {
            mainResponse = new MainResponse<List<Room>>(roomService.getAll(), "SUCCESS");
            return new ResponseEntity<MainResponse<List<Room>>>(mainResponse, HttpStatus.OK);
        } catch (Exception e){
            mainResponse = new MainResponse<List<Room>>("Error: " +e.getMessage(), "SUCCESS", true);
            return new ResponseEntity<MainResponse<List<Room>>>(mainResponse, HttpStatus.OK);
        }

    }
}
