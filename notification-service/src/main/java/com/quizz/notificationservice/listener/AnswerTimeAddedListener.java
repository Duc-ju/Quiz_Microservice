package com.quizz.notificationservice.listener;

import com.quizz.notificationservice.event.AnswerTimeAddedEvent;
import com.quizz.notificationservice.service.AnswerTimeAddedNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class AnswerTimeAddedListener {

    private final AnswerTimeAddedNotificationService answerTimeAddedNotificationService;

    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(AnswerTimeAddedEvent answerTimeAddedEvent) {
        answerTimeAddedNotificationService.saveNotification(answerTimeAddedEvent);
    }

}
