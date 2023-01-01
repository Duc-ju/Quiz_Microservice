package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room/statistic")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/answer-times/{answerTimeId}/statistic")
    public ResponseEntity<ResponseObject> getAfterQuestionStatistic(
            @PathVariable Long answerTimeId
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return question statistic", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }
    }

    @GetMapping("/answer-times/{answerTimeId}/questions/{questionId}/chart")
    public ResponseEntity<ResponseObject> getAfterQuestionRank(
            @PathVariable Long answerTimeId,
            @PathVariable Long questionId
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return question chart", statisticService.getAfterQuestionChart(answerTimeId, questionId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }
    }

    @GetMapping("/answer-times/{answerTimeId}")
    public ResponseEntity<ResponseObject> getAnswerTimeStatistic(@PathVariable Long answerTimeId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return answer-time statistic", statisticService.getAnswerTimeStatistic(answerTimeId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ResponseObject> getRoomStatistic(@PathVariable Long roomId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return room statistic", statisticService.getRoomStatisticById(roomId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}
