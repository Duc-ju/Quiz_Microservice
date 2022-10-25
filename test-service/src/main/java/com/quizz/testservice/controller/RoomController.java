package com.quizz.testservice.controller;

import com.quizz.testservice.common.ResponseObject;
import com.quizz.testservice.model.Room;
import com.quizz.testservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test/rooms")
@Log4j2
public class RoomController {

    private final RoomService roomService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> addRoom(@RequestBody Room room) {
        log.info("Create room {}", room);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("Return saved room", roomService.createRoom(room)));
    }
}
