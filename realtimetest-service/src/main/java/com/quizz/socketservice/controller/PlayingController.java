package com.quizz.socketservice.controller;

import com.quizz.socketservice.common.MessageStatus;
import com.quizz.socketservice.common.ResponseType;
import com.quizz.socketservice.dto.question.Question;
import com.quizz.socketservice.dto.request.AnswerTime;
import com.quizz.socketservice.dto.request.QuestionAnswer;
import com.quizz.socketservice.dto.request.RequestMessage;
import com.quizz.socketservice.dto.request.RoomRequest;
import com.quizz.socketservice.dto.response.ResponseMessage;
import com.quizz.socketservice.dto.response.ResponseObject;
import com.quizz.socketservice.service.ResourceConnectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PlayingController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final WebClient.Builder webClientBuilder;
    private final ResourceConnectService resourceConnectService;

    @MessageMapping("/create-answer-time/{userId}")
    public void createAnswerTime(@DestinationVariable Long userId, @Payload RequestMessage<AnswerTime> clientMessage, @Header("simpSessionId") String sessionId) throws Exception {
        logClientMessage(clientMessage);
        AnswerTime answerTime = clientMessage.getBody();
        answerTime.setPlayedDateTime(LocalDateTime.now());
        answerTime.setSocketId(sessionId);
        ResponseObject responseObject = webClientBuilder.build().post()
                .uri("lb://test-service/api/v1/test/answer-times/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(answerTime))
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        simpMessagingTemplate.convertAndSend("/topic/pre-room/" + userId,
                new ResponseMessage()
                        .builder()
                        .message(responseObject.getData())
                        .status(MessageStatus.SUCCESS)
                        .build());
    }

    @MessageMapping("/create-room/{userId}")
    public void createRoom(@DestinationVariable Long userId, @Payload RequestMessage<RoomRequest> roomRequest) {
        logClientMessage(roomRequest);
        RoomRequest room = roomRequest.getBody();
        room.setCreatedAt(LocalDateTime.now());
        ResponseObject responseObject = webClientBuilder.build().post()
                .uri("lb://test-service/api/v1/test/rooms/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(room))
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        simpMessagingTemplate.convertAndSend("/topic/pre-room/" + userId,
                new ResponseMessage()
                        .builder()
                        .message(responseObject.getData())
                        .status(MessageStatus.SUCCESS)
                        .build());
    }

    @MessageMapping("/statistic/{id}")
    public ResponseMessage statistic(@DestinationVariable Long id, @Payload RequestMessage clientMessage) throws Exception {
        logClientMessage(clientMessage);
        return new ResponseMessage().builder().message("").status(MessageStatus.SUCCESS).build();
    }

    @MessageMapping("/start-room/{id}")
    public void startRoom(@DestinationVariable Long id, @Payload RequestMessage<String> clientMessage) throws Exception {
        List<Question> questions = resourceConnectService.getQuestionList(Long.valueOf(clientMessage.getBody()));
        Question question;
        int questionSize = questions.size();
        for (int i = 0; i < questionSize; i++) {
            question = questions.get(i);
            String message;
            if (i == questionSize - 1) message = ResponseType.LAST_QUESTION;
            else if (i == 0) message = ResponseType.FIRST_QUESTION;
            else message = ResponseType.NEXT_QUESTION;
            simpMessagingTemplate.convertAndSend("/topic/room-message/" + id,
                    new ResponseMessage().builder().type(message).message(question).status(MessageStatus.SUCCESS).build());
            Thread.sleep(question.getDuration() * 1000);
        }
    }

    @MessageMapping("/send-answer/{id}")
    public void sendAnswer(@DestinationVariable Long id, @Payload RequestMessage<QuestionAnswer> clientMessage) throws Exception {
        log.info(clientMessage.getBody());
        ResponseObject responseObject = webClientBuilder.build().post()
                .uri("lb://test-service/api/v1/test/question-answers/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(clientMessage.getBody()))
                .retrieve().bodyToMono(ResponseObject.class)
                .block();
        log.info("Saved question answer: {}", responseObject);
    }

    private void logClientMessage(RequestMessage clientMessage) {
        log.info("Receive message {}", clientMessage);
    }

}
