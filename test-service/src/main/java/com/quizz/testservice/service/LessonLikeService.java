package com.quizz.testservice.service;

import com.quizz.testservice.model.LessonLike;
import com.quizz.testservice.repository.LessonLikeRepository;
import com.quizz.testservice.repository.LessonLikeRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonLikeService {

    private final LessonLikeRepository lessonLikeRepository;
    private final LessonLikeRepositoryCustomImpl lessonLikeRepositoryCustom;

    public Long countLessonLike(Long lessonId) {
        return lessonLikeRepositoryCustom.countLessonLike(lessonId);
    }

    public Long addLessonLike(LessonLike lessonLike) {
        lessonLikeRepository.save(lessonLike);
        return countLessonLike(lessonLike.getLessonId());
    }
}
