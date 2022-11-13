package com.quizz.testservice.controller;

import com.quizz.testservice.common.ResponseObject;
import com.quizz.testservice.model.QuestionAnswer;
import com.quizz.testservice.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test/statistic")
@Log4j2
public class StatisticController {

    private final StatisticService statisticService;

    @GetMapping("/question")
    public ResponseEntity<ResponseObject> getQuestionStatistic(@RequestBody QuestionAnswer questionAnswer) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseObject("Return question statistic", statisticService.getQuestionStatistic(questionAnswer)));
    }
}
