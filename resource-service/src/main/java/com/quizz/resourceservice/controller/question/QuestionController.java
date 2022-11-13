package com.quizz.resourceservice.controller.question;

import com.quizz.resourceservice.common.ResponseObject;
import com.quizz.resourceservice.model.question.Question;
import com.quizz.resourceservice.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/resource/questions")
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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getQuestion(@PathVariable Long id) {
        try {
            Question question = questionService.getQuestion(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Return question with id: " + id, question)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(e.getMessage(), null)
            );
        }

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

    @GetMapping("/{id}/numberOfKeys")
    public ResponseEntity<ResponseObject> getNumberOfKeys(@PathVariable Long id) {
        int numberOfKeys = questionService.getNumberOfKeysByQuestionId(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Return numberOfKeys with questionId: " + id, numberOfKeys)
        );
    }
}
