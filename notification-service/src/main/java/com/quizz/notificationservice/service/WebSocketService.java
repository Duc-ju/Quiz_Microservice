package com.quizz.notificationservice.service;

import com.quizz.notificationservice.dto.ResponseMessage;
import com.quizz.notificationservice.dto.ResponseType;
import com.quizz.notificationservice.model.AnswerTimeAddedNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private AnswerTimeAddedNotificationService answerTimeAddedNotificationService;

    public void handleSubscribeRoom(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String userId = headers.getDestination().split("/")[3];
        List<AnswerTimeAddedNotification> answerTimeAddedNotifications = answerTimeAddedNotificationService.getAllByUserId(userId);
        ResponseMessage responseMessage = ResponseMessage.builder().type(ResponseType.LIST_INIT).body(answerTimeAddedNotifications).build();
        log.info(responseMessage);
        simpMessagingTemplate.convertAndSend(headers.getDestination(),
                new ResponseMessage().builder().type(ResponseType.LIST_INIT).body(answerTimeAddedNotifications));
    }
}
