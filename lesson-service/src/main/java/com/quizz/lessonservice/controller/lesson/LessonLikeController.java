package com.quizz.lessonservice.controller.lesson;

import com.quizz.lessonservice.common.ResponseObject;
import com.quizz.lessonservice.model.lesson.LessonLike;
import com.quizz.lessonservice.service.lesson.LessonLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/lesson/lesson-likes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
@RequiredArgsConstructor
public class LessonLikeController {

    private final LessonLikeService lessonLikeService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> addLessonLike(@RequestBody LessonLike lessonLike) {
        List<LessonLike> lessonLikes = lessonLikeService.addLessonLike(lessonLike);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Added lesson like successfully!", lessonLikes));
    }

    @PostMapping("/remove")
    public ResponseEntity<ResponseObject> removeLessonLike(@RequestBody LessonLike lessonLike) {
        List<LessonLike> lessonLikes = lessonLikeService.removeLessonLike(lessonLike);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Removed lesson like successfully!", lessonLikes));
    }
}
