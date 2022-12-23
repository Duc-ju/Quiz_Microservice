package com.quizz.lessonservice.controller.question;

import com.quizz.lessonservice.common.ResponseObject;
import com.quizz.lessonservice.model.question.Answer;
import com.quizz.lessonservice.model.question.Question;
import com.quizz.lessonservice.service.question.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/lesson/questions")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @PostMapping("/{questionId}/answers/all")
    public ResponseEntity<ResponseObject> addAnswers(@PathVariable Long questionId, @RequestBody List<Answer> answers) {
        Question question = new Question();
        question.setId(questionId);
        answers.stream().forEach(answer -> answer.setQuestion(question));
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Added answers", answerService.addAnswers(answers))
        );
    }
}
