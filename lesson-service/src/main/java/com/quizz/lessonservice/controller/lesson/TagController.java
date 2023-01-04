package com.quizz.lessonservice.controller.lesson;

import com.quizz.lessonservice.common.ResponseObject;
import com.quizz.lessonservice.model.lesson.Tag;
import com.quizz.lessonservice.service.lesson.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lesson/tags")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Log4j2
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return all tags", tagService.getAllTag()));
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> addTag(@RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return added tag", tagService.addTag(tag)));
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> addTags(@RequestBody List<Tag> tags) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return added tags", tagService.addTags(tags)));
    }
}
