package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.AnswerTime;
import com.quizz.roomservice.service.AnswerTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room/answer-times")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AnswerTimeController {

    private final AnswerTimeService answerTimeService;

    @GetMapping(path = "/count")
    public ResponseEntity<ResponseObject> getAnswerTimeCount(@RequestParam Long lessonId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("return count of answer time", answerTimeService
                        .getNumberOfAnswerTime(lessonId)));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ResponseObject> getAnswerTime(@PathVariable Long id) {
        try {
            AnswerTime answerTime = answerTimeService.getAnswerTimeById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("return answer time", answerTime));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(path = "")
    public ResponseEntity<ResponseObject> addAnswerTime(@RequestBody AnswerTime answerTime) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("return answer time", answerTimeService.addAnswerTime(answerTime)));
    }
}
