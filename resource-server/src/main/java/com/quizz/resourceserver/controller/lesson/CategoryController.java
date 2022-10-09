package com.quizz.resourceserver.controller.lesson;

import com.quizz.resourceserver.common.ResponseObject;
import com.quizz.resourceserver.model.lesson.Category;
import com.quizz.resourceserver.model.lesson.Lesson;
import com.quizz.resourceserver.service.lesson.CategoryService;
import com.quizz.resourceserver.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Log4j2
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final LessonService lessonService;

    @GetMapping("/{id}/lessons")
    public ResponseEntity<ResponseObject> getLessons(@PathVariable Long id) {
        List<Lesson> lessons = lessonService.getLessonList(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return list", lessons));
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> getCategories() {
        List<Category> categories = categoryService.getCategoryList();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return list", categories));
    }
}
