package com.quizz.testservice.repository;

import com.quizz.testservice.model.AnswerTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerTimeRepository extends JpaRepository<AnswerTime, Long> {
    void deleteAnswerTimeBySocketId(String socketId);
}
