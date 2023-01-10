package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.AnswerTimeStatistic;
import com.quizz.roomservice.model.RoomStatistic;
import com.quizz.roomservice.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(path = "users/{userId}/answer-times")
    public ResponseEntity<ResponseObject> getListAnswerTimeByUserId(@PathVariable String userId) {
        try {
            List<AnswerTimeStatistic> answerTimeList = statisticService.getAllAnswerTimeStatisticByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("return list of answer time of user", answerTimeList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject(e.getMessage(), null));
        }

    }

    @GetMapping("/answer-times/{lessonId}/chart")
    public ResponseEntity<ResponseObject> getAnswerTimeChart(@PathVariable Long lessonId) {
        try {
            RoomStatistic roomStatistic = statisticService.getRoomStatisticByLessonId(lessonId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return answer-time chart", roomStatistic.getChartStatistic()));
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

    @GetMapping("/rooms/{roomId}/chart")
    public ResponseEntity<ResponseObject> getRoomChart(@PathVariable Long roomId) {
        try {
            RoomStatistic roomStatistic = statisticService.getRoomStatisticById(roomId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return room statistic", roomStatistic.getChartStatistic()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping("/rooms")
    public ResponseEntity<ResponseObject> getAllRoomStatistic() {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject("Return rooms statistic", statisticService.getReportList("1")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }
}
