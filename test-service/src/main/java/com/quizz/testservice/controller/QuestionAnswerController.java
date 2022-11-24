package com.quizz.testservice.controller;

import com.quizz.testservice.common.ResponseObject;
import com.quizz.testservice.model.QuestionAnswer;
import com.quizz.testservice.service.QuestionAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test/question-answers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QuestionAnswerController {

    private final QuestionAnswerService questionAnswerService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> addQuestionAnswer(@RequestBody QuestionAnswer questionAnswer) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("return answer of question added", questionAnswerService.addQuestionAnswer(questionAnswer))
        );
    }
}
