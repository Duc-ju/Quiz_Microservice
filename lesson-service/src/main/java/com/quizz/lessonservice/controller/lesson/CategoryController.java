package com.quizz.lessonservice.controller.lesson;

import com.quizz.lessonservice.common.ResponseObject;
import com.quizz.lessonservice.model.lesson.Category;
import com.quizz.lessonservice.model.lesson.Lesson;
import com.quizz.lessonservice.service.lesson.CategoryService;
import com.quizz.lessonservice.service.lesson.LessonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lesson/categories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @PostMapping("")
    public ResponseEntity<ResponseObject> addCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.addCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return saved category", savedCategory));
    }

    @PostMapping("/all")
    public ResponseEntity<ResponseObject> addCategories(@RequestBody List<Category> categories) {
        List<Category> savedCategories = categoryService.addCategories(categories);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("return saved category list", savedCategories));
    }
}
