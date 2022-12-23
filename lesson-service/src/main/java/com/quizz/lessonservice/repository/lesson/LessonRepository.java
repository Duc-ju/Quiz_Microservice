package com.quizz.lessonservice.repository.lesson;

import com.quizz.lessonservice.model.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCategoryId(Long categoryId);
}
