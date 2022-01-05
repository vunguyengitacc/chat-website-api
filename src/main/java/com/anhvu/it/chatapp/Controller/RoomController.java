package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.Room;
import com.anhvu.it.chatapp.Service.Room.RoomService;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

    @GetMapping("")
    public ResponseEntity<MainResponse<List<Room>>> getAll() {
        MainResponse<List<Room>> mainResponse;
        try {
            mainResponse = new MainResponse<List<Room>>(roomService.getAll(), "SUCCESS");
            return new ResponseEntity<MainResponse<List<Room>>>(mainResponse, HttpStatus.OK);
        } catch (Exception e) {
            mainResponse = new MainResponse<List<Room>>("Error: " + e.getMessage(), "FAILED", true);
            return new ResponseEntity<MainResponse<List<Room>>>(mainResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{term}")
    public ResponseEntity<MainResponse<List<Room>>> search(@PathVariable String term) {
        MainResponse<List<Room>> mainResponse;
        try {
            List<Room> lstRoom = roomService.search(term);

            mainResponse = new MainResponse<List<Room>>(lstRoom, "SUCCESS");
            return new ResponseEntity<MainResponse<List<Room>>>(mainResponse, HttpStatus.OK);
        } catch (Exception e) {
            mainResponse = new MainResponse<List<Room>>("Error: " + e.getMessage(), "FAILED", true);
            return new ResponseEntity<>(mainResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
