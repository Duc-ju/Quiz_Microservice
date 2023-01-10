package com.quizz.notificationservice.controller;

import com.quizz.notificationservice.repository.AnswerTimeNotificationRepositoryCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class NotificationController {

    private final AnswerTimeNotificationRepositoryCustom notificationRepositoryCustom;

    @PutMapping(path = "/users/{userId}/make-read")
    public ResponseEntity makeAllNotificationRead(@PathVariable String userId) {
        notificationRepositoryCustom.updateReadByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
