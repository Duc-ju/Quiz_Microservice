package com.quizz.lessonservice.repository.lesson;

import com.quizz.lessonservice.model.lesson.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
