package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.QuestionAnswer;
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

    @GetMapping("/question")
    public ResponseEntity<ResponseObject> getQuestionStatistic(@RequestBody QuestionAnswer questionAnswer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("Return question statistic", statisticService.getQuestionStatistic(questionAnswer)));
    }
}
