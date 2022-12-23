package com.quizz.lessonservice.repository.lesson;

import com.quizz.lessonservice.model.lesson.LessonLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonLikeRepository extends JpaRepository<LessonLike, Long> {

    List<LessonLike> getLessonLikeByLessonId(Long lessonId);
}
