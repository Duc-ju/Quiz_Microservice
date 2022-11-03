package com.quizz.resourceservice.controller.lesson;

import com.quizz.resourceservice.common.ResponseObject;
import com.quizz.resourceservice.model.lesson.Tag;
import com.quizz.resourceservice.service.lesson.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/resource/tags")
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
}
