package com.quizz.resourceservice.repository.lesson;

import com.quizz.resourceservice.model.lesson.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
