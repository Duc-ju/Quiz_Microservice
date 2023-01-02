package com.quizz.asynchronoustestservice.controller;

import com.quizz.asynchronoustestservice.common.Constant;
import com.quizz.asynchronoustestservice.common.MessageStatus;
import com.quizz.asynchronoustestservice.common.MessageType;
import com.quizz.asynchronoustestservice.dto.question.Question;
import com.quizz.asynchronoustestservice.dto.request.RequestMessage;
import com.quizz.asynchronoustestservice.dto.response.ResponseMessage;
import com.quizz.asynchronoustestservice.service.ResourceConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PlayingController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebClient.Builder webClientBuilder;
    private final ResourceConnectService resourceConnectService;

    @MessageMapping("/start-room/{roomId}")
    public void startRoom(@DestinationVariable String roomId, @Payload RequestMessage<String> clientMessage) throws Exception {
        List<Question> questions = resourceConnectService.getQuestionList(Long.valueOf(clientMessage.getBody()));
        Question question;
        int questionSize = questions.size();
        simpMessagingTemplate.convertAndSend("/topic/room-message/" + roomId,
                new ResponseMessage().builder().type(MessageType.SEND_START_ROOM).status(MessageStatus.SUCCESS).message(Constant.PENDING_START_TIME).build());
        Thread.sleep(Constant.PENDING_START_TIME);
        for (int i = 0; i < questionSize; i++) {
            question = questions.get(i);
            String message;
            if (i == questionSize - 1) message = MessageType.SEND_LAST_QUESTION;
            else if (i == 0) message = MessageType.SEND_FIRST_QUESTION;
            else message = MessageType.SEND_QUESTION;
            simpMessagingTemplate.convertAndSend("/topic/room-message/" + roomId,
                    new ResponseMessage().builder().type(message).message(question).status(MessageStatus.SUCCESS).build());
            Thread.sleep(question.getDuration() * 1000);
            Object statistic = resourceConnectService.getRoomStatistic(Long.parseLong(roomId));
            ResponseMessage responseMessage = new ResponseMessage()
                    .builder()
                    .message(statistic)
                    .type(MessageType.SEND_STATISTIC)
                    .status(MessageStatus.SUCCESS)
                    .build();
            simpMessagingTemplate.convertAndSend("/topic/room-admin/" + roomId,
                    responseMessage);
            simpMessagingTemplate.convertAndSend("/topic/room-message/" + roomId,
                    responseMessage);
            Thread.sleep(Constant.PENDING_QUESTION_TIME);
        }
    }

    private void logClientMessage(RequestMessage clientMessage) {
        log.info("Receive message {}", clientMessage);
    }

}
