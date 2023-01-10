package com.quizz.roomservice.repository;

import com.quizz.roomservice.model.AnswerTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerTimeRepository extends JpaRepository<AnswerTime, Long> {

    List<AnswerTime> findByRoomId(Long id);

    List<AnswerTime> getByLessonId(Long id);

    List<AnswerTime> findAllByUserId(String userId);
}
