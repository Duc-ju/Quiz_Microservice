package com.quizz.testservice.controller;

import com.quizz.testservice.common.ResponseObject;
import com.quizz.testservice.dto.LessonRequest;
import com.quizz.testservice.model.AnswerTime;
import com.quizz.testservice.service.AnswerTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answer-times")
public class AnswerTimeController {

    private final AnswerTimeService answerTimeService;

    @GetMapping(path = "/count")
    public ResponseEntity<ResponseObject> getAnswerTime(@RequestBody LessonRequest lesson) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("return count of answer time", answerTimeService
                        .getAnswerTime(lesson.getLessonId())));
    }

    @PostMapping(path = "")
    public ResponseEntity<ResponseObject> addAnswerTime(@RequestBody AnswerTime answerTime) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("return answer time", answerTimeService.addAnswerTime(answerTime)));
    }
}
