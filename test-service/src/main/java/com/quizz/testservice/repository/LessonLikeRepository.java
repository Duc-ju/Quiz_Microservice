package com.quizz.testservice.repository;

import com.quizz.testservice.model.LessonLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonLikeRepository extends JpaRepository<LessonLike, Long> {
}
