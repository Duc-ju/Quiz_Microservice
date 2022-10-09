package com.quizz.resourceserver.repository.lesson;

import com.quizz.resourceserver.model.lesson.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    public List<Lesson> findByCategoryId(Long categoryId);
}
