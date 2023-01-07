package com.quizz.notificationservice.service;

import com.quizz.notificationservice.event.AnswerTimeAddedEvent;
import com.quizz.notificationservice.model.AnswerTimeAddedNotification;
import com.quizz.notificationservice.repository.AnswerTimeAddedNotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class AnswerTimeAddedNotificationService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AnswerTimeAddedNotificationRepository answerTimeAddedNotificationRepository;

    public List<AnswerTimeAddedNotification> getAllByUserId(String userId) {
        return answerTimeAddedNotificationRepository.findAllByUserId(userId);
    }

    public void saveNotification(AnswerTimeAddedEvent answerTimeAddedEvent) {
        AnswerTimeAddedNotification answerTimeAddedNotification = new AnswerTimeAddedNotification();
        answerTimeAddedNotification.setCreatedTime(answerTimeAddedEvent.getCreatedTime());
        answerTimeAddedNotification.setRedirectUrl("/join/practice/"
                + answerTimeAddedEvent.getLessonId() + "/scored-game/" + answerTimeAddedEvent.getLessonId());
        answerTimeAddedNotification.setMessage("Bạn vừa làm một bài kiểm tra, click để xem chi tiết.");
        AnswerTimeAddedNotification savedNotification = answerTimeAddedNotificationRepository.save(answerTimeAddedNotification);
        log.info(savedNotification);
        simpMessagingTemplate.convertAndSend("/topic/notification/" + savedNotification.getUserId(), savedNotification);
    }

}
