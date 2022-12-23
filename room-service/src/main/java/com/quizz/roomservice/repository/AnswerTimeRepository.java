package com.quizz.roomservice.repository;

import com.quizz.roomservice.model.AnswerTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerTimeRepository extends JpaRepository<AnswerTime, Long> {
    void deleteAnswerTimeBySocketId(String socketId);
}
