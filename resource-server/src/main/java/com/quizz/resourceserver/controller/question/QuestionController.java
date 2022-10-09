package com.quizz.resourceserver.controller.question;

import com.quizz.resourceserver.common.ResponseObject;
import com.quizz.resourceserver.model.question.Question;
import com.quizz.resourceserver.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> addQuestion(@RequestBody Question question) {
        Question addedQuestion = questionService.addQuestion(question);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("added question", addedQuestion)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateQuestion(@RequestBody Question newQuestion, @PathVariable Long id) {
        try {
            Question updatedQuestion = questionService.updateQuestion(newQuestion, id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Updated question", updatedQuestion)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(e.getMessage(), null)
            );
        }
    }
}
