package com.quizz.testservice.controller;

import com.quizz.testservice.common.ResponseObject;
import com.quizz.testservice.dto.LessonRequest;
import com.quizz.testservice.model.LessonLike;
import com.quizz.testservice.service.LessonLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lesson-likes")
@RequiredArgsConstructor
public class LessonLikeController {

    private final LessonLikeService lessonLikeService;

    @GetMapping("/count")
    public ResponseEntity<ResponseObject> getCountLessonLike(@RequestParam LessonRequest lesson) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Return number likes count of lesson",
                        lessonLikeService.countLessonLike(lesson.getLessonId()))
        );
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addLessonLike(@RequestBody LessonLike lessonLike) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Add like and return likes count of lesson",
                        lessonLikeService.addLessonLike(lessonLike))
        );
    }
}
