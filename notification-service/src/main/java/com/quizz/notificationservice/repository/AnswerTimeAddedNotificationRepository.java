package com.quizz.notificationservice.repository;

import com.quizz.notificationservice.model.AnswerTimeAddedNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerTimeAddedNotificationRepository extends JpaRepository<AnswerTimeAddedNotification, Long> {
    List<AnswerTimeAddedNotification> findAllByUserId(String userId);
}
