package com.quizz.lessonservice.controller.lesson;

import com.quizz.lessonservice.common.ResponseObject;
import com.quizz.lessonservice.dto.UserInfo;
import com.quizz.lessonservice.model.lesson.Lesson;
import com.quizz.lessonservice.model.question.Question;
import com.quizz.lessonservice.service.lesson.LessonService;
import com.quizz.lessonservice.service.question.QuestionService;
import com.quizz.lessonservice.util.TokenUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/lesson/lessons")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;
    private final QuestionService questionService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getLessons() {
        List<Lesson> lessons = lessonService.getLessonList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return list", lessons));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getLesson(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.getLessonById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Return lesson", lesson)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/{id}/raw")
    public ResponseEntity<ResponseObject> getLessonRaw(@PathVariable Long id) {
        try {
            Lesson lesson = lessonService.getLessonRaw(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Return lesson", lesson)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(e.getMessage(), null)
            );
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addLesson(@RequestBody Lesson lesson) {
        UserInfo userInfo = TokenUtility.getBearerTokenInfo();
        if (userInfo != null) lesson.setOwnerId(userInfo.getId());
        lesson.setDisFlg(true);
        lesson.setCreatedAt(Calendar.getInstance().getTime());
        log.info(lesson);
        Lesson savedLesson = lessonService.saveLesson(lesson);
        for (Question question : lesson.getQuestions()) {
            question.setLesson(savedLesson);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("added lesson", lessonService.saveLesson(lesson))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateLesson(@RequestBody Lesson newLesson, @PathVariable Long id) {
        try {
            Lesson updatedLesson = lessonService.updateLesson(newLesson, id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Updated lesson", updatedLesson)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject(e.getMessage(), null)
            );
        }
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<ResponseObject> getQuestionsByLesson(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Return question list", questionService.getQuestionsByLessonId(id))
        );
    }
}
