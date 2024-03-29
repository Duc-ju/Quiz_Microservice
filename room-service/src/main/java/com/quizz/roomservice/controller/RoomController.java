package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.Room;
import com.quizz.roomservice.model.UserInfo;
import com.quizz.roomservice.service.RoomService;
import com.quizz.roomservice.util.TokenUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room/rooms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class RoomController {

    private final RoomService roomService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> addRoom(@RequestBody Room room) {
        UserInfo adminInfo = TokenUtility.getBearerTokenInfo();
        room.setUserId(adminInfo.getId());
        log.info("Create room {}", room);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("Return saved room", roomService.createRoom(room)));
    }

    @GetMapping("/{roomId}/lessons")
    public ResponseEntity<ResponseObject> getLessonByRoomId(@PathVariable Long roomId) {
        try {
            Object lesson = roomService.getLessonByRoomId(roomId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return lesson by room with id " + roomId, lesson));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ResponseObject> getRoom(@PathVariable Long roomId) {
        try {
            Room room = roomService.getRoomById(roomId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return room with id: " + roomId, room));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{roomId}/check-started")
    public ResponseEntity<ResponseObject> checkStarted(@PathVariable Long roomId) {
        try {
            boolean isStarted = roomService.checkStarted(roomId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(
                            "Return status of room, if true - room is started with roomId:" + roomId, isStarted));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
