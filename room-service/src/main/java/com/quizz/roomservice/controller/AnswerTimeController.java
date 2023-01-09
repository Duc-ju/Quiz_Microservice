package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.AnswerTime;
import com.quizz.roomservice.model.UserInfo;
import com.quizz.roomservice.service.AnswerTimeService;
import com.quizz.roomservice.util.TokenUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room/answer-times")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
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
        UserInfo userInfo = TokenUtility.getBearerTokenInfo();
        answerTime.setUserId(userInfo.getId());
        answerTime.setNickName(userInfo.getUsername());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("return answer time", answerTimeService.addAnswerTime(answerTime)));
    }
}
