package com.quizz.testservice.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface LessonLikeRepositoryCustom {

    Long countLessonLike(Long lessonId);
}
