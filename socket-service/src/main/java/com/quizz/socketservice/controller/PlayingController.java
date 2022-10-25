package com.quizz.socketservice.controller;

import com.quizz.socketservice.common.MessageStatus;
import com.quizz.socketservice.dto.http.ResponseObject;
import com.quizz.socketservice.dto.request.AnswerTime;
import com.quizz.socketservice.dto.request.QuestionAnswer;
import com.quizz.socketservice.dto.request.RequestMessage;
import com.quizz.socketservice.dto.request.RoomRequest;
import com.quizz.socketservice.dto.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PlayingController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebClient.Builder webClientBuilder;

    @MessageMapping("/create-answer-time")
    public ResponseMessage createAnswerTime(@Payload RequestMessage<AnswerTime> clientMessage) throws Exception {
        logClientMessage(clientMessage);
        AnswerTime answerTime = clientMessage.getMessage();
        answerTime.setPlayedDateTime(LocalDateTime.now());
        ResponseObject responseObject = webClientBuilder.build().post()
                .uri("lb://test-service/api/v1/test/answer-times/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(answerTime))
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        return new ResponseMessage()
                .builder()
                .message(responseObject.getData())
                .status(MessageStatus.SUCCESS)
                .build();
    }

    @MessageMapping("/create-room")
    public ResponseMessage createRoom(@Payload RequestMessage<RoomRequest> roomRequest) {
        logClientMessage(roomRequest);
        RoomRequest room = roomRequest.getMessage();
        room.setCreatedAt(LocalDateTime.now());
        ResponseObject responseObject = webClientBuilder.build().post()
                .uri("lb://test-service/api/v1/test/rooms/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(room))
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        return new ResponseMessage()
                .builder()
                .message(responseObject.getData())
                .status(MessageStatus.SUCCESS)
                .build();
    }

    @MessageMapping("/statistic/{id}")
    public ResponseMessage statistic(@DestinationVariable Long id, @Payload RequestMessage clientMessage) throws Exception {
        logClientMessage(clientMessage);
        return new ResponseMessage().builder().message("").status(MessageStatus.SUCCESS).build();
    }

    @MessageMapping("/join/{id}")
    public void userJoin(@DestinationVariable Long id, @Payload RequestMessage<String> clientMessage) throws Exception {
        logClientMessage(clientMessage);
        simpMessagingTemplate.convertAndSend("/topic/room-message/" + id,
                new ResponseMessage().builder().message(clientMessage.getMessage()).status(MessageStatus.SUCCESS).build());
    }

    @MessageMapping("/answer/{id}")
    public void userAnswer(@DestinationVariable Long id, @Payload RequestMessage<QuestionAnswer> clientMessage) throws Exception {
        logClientMessage(clientMessage);
        simpMessagingTemplate.convertAndSend("/topic/join/" + id, new ResponseMessage().builder().message("").status(MessageStatus.SUCCESS).build());
    }

    private void logClientMessage(RequestMessage clientMessage) {
        log.info("Receive message {}", clientMessage);
    }

}
