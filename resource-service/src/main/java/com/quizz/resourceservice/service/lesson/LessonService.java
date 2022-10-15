package com.quizz.resourceservice.service.lesson;

import com.quizz.resourceservice.common.LogType;
import com.quizz.resourceservice.common.ResponseObject;
import com.quizz.resourceservice.model.lesson.Lesson;
import com.quizz.resourceservice.repository.lesson.LessonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class LessonService {

    private final LessonRepository lessonRepository;
    private final WebClient.Builder webClientBuilder;

    /**
     * This method to save lesson
     *
     * @param lesson
     * @return saved lesson information
     * @throws Exception
     */
    public Lesson saveLesson(Lesson lesson) {
        log.info(LogType.PREPARE_ADD, lesson);
        Lesson lessonSaved = lessonRepository.save(lesson);
        return lessonSaved;
    }

    /**
     * This method to update lesson
     *
     * @param newLesson, id
     * @return updated lesson information
     * @throws Exception
     */
    public Lesson updateLesson(Lesson newLesson, Long id) throws Exception {
        log.info(LogType.PREPARE_UPDATE, newLesson, id);
        Optional<Lesson> lessonOptional = lessonRepository.findById(id).map(lesson -> {
            BeanUtils.copyProperties(newLesson, lesson, "createdAt");
            lesson.setUpdatedAt(Calendar.getInstance().getTime());
            return lessonRepository.save(lesson);
        });
        if (!lessonOptional.isPresent()) {
            throw new Exception("Can not find product with id " + id);
        }
        return lessonOptional.get();
    }

    /**
     * This method to get all lessons
     *
     * @param
     * @return lesson list
     * @throws Exception
     */
    public List<Lesson> getLessonList() {
        return lessonRepository.findAll();
    }

    /**
     * This method to get lesson by id
     *
     * @param id
     * @return lesson
     * @throws Exception
     */
    public Lesson getLessonById(Long id) throws Exception {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (!lessonOptional.isPresent()) {
            throw new Exception("Cannot found lesson with id " + id);
        }
        Lesson lesson = lessonOptional.get();
        ResponseObject likeCount = webClientBuilder.build().get()
                .uri("lb://test-service/api/v1/test/lesson-likes/count",
                        uriBuilder -> uriBuilder.queryParam("lessonId", id).build())
                .retrieve().bodyToMono(ResponseObject.class)
                .block();

        ResponseObject playCount = webClientBuilder.build().get()
                .uri("lb://test-service/api/v1/test/answer-times/count",
                        uriBuilder -> uriBuilder.queryParam("lessonId", id).build())
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        lesson.setNumberOfLiked(Long.valueOf(likeCount.getData().toString()));
        lesson.setNumberOfPlayed(Long.valueOf(playCount.getData().toString()));
        return lessonOptional.get();
    }

    /**
     * This method to get lesson list belong to category
     *
     * @param categoryId
     * @return list lesson
     * @throws Exception
     */
    public List<Lesson> getLessonList(Long categoryId) {
        return lessonRepository.findByCategoryId(categoryId);
    }
}
