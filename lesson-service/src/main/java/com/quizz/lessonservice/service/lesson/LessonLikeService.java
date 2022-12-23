package com.quizz.lessonservice.service.lesson;

import com.quizz.lessonservice.model.lesson.LessonLike;
import com.quizz.lessonservice.repository.lesson.LessonLikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LessonLikeService {

    private final LessonLikeRepository lessonLikeRepository;

    public List<LessonLike> addLessonLike(LessonLike lessonLike) {
        lessonLikeRepository.save(lessonLike);
        return lessonLikeRepository.getLessonLikeByLessonId(lessonLike.getLesson().getId());
    }

    public List<LessonLike> removeLessonLike(LessonLike lessonLike) {
        lessonLikeRepository.delete(lessonLike);
        return lessonLikeRepository.getLessonLikeByLessonId(lessonLike.getLesson().getId());
    }
}
