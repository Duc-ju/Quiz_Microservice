package com.quizz.resourceserver.repository.lesson;

import com.quizz.resourceserver.model.lesson.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
