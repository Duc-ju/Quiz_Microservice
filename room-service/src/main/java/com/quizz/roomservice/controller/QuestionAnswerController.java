package com.quizz.roomservice.controller;

import com.quizz.roomservice.common.ResponseObject;
import com.quizz.roomservice.model.QuestionAnswer;
import com.quizz.roomservice.service.QuestionAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/room/question-answers")
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
