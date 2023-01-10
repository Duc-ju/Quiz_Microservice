package com.quizz.notificationservice.service;

import com.quizz.notificationservice.dto.ResponseMessage;
import com.quizz.notificationservice.dto.ResponseType;
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
        if (answerTimeAddedEvent.getRoomId() == null) {
            answerTimeAddedNotification.setMessage("Bạn vừa làm một bài luyện tập, click để xem kết quả.");
            answerTimeAddedNotification.setRedirectUrl("/join/practice/"
                    + answerTimeAddedEvent.getLessonId() + "/scored-game/" + answerTimeAddedEvent.getAnswerTimeId());
        } else {
            answerTimeAddedNotification.setMessage("Bạn vừa làm một bài kiểm tra, click để xem kết quả.");
            answerTimeAddedNotification.setRedirectUrl("/join/asynchronous/"
                    + answerTimeAddedEvent.getRoomId() + "/scored-game/" + answerTimeAddedEvent.getAnswerTimeId());
        }
        answerTimeAddedNotification.setUserId(answerTimeAddedEvent.getUserId());
        AnswerTimeAddedNotification savedNotification = answerTimeAddedNotificationRepository.save(answerTimeAddedNotification);
        log.info(savedNotification);
        simpMessagingTemplate.convertAndSend("/topic/notification/"
                + savedNotification.getUserId(), ResponseMessage.builder()
                .type(ResponseType.ADDED_MESSAGE).body(savedNotification).build());
    }

}
