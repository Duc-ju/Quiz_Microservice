package com.quizz.synchronoustestservice.service;

import com.quizz.synchronoustestservice.common.MessageStatus;
import com.quizz.synchronoustestservice.common.ResponseType;
import com.quizz.synchronoustestservice.dto.response.ResponseMessage;
import com.quizz.synchronoustestservice.model.SocketRegistration;
import com.quizz.synchronoustestservice.repository.SocketRegistrationRepository;
import com.quizz.synchronoustestservice.repository.SocketRegistrationRepositoryCustomImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocketRegistrationService {

    private final SocketRegistrationRepository socketRegistrationRepository;
    private final SocketRegistrationRepositoryCustomImpl socketRegistrationRepositoryCustom;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public SocketRegistration saveSocketRegistration(SocketRegistration socketRegistration) {
        return socketRegistrationRepository.save(socketRegistration);
    }

    public void handleShutdownSocket(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        List<String> roomIdList = socketRegistrationRepositoryCustom.getRoomListBySocketId(headers.getSessionId());
        socketRegistrationRepositoryCustom.killAllSocketRegistration(headers.getSessionId());
        for (String roomId : roomIdList) {
            Long numberUserInRoom = socketRegistrationRepositoryCustom.countUserInRoom(roomId);
            if (numberUserInRoom > 0) {
                simpMessagingTemplate.convertAndSend("/topic/room-message/" + roomId,
                        new ResponseMessage().builder().type(ResponseType.USER_LEFT_ROOM).message(numberUserInRoom).status(MessageStatus.SUCCESS).build());
            }
        }
    }

    public void handleSubscribeRoom(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headers.getDestination().split("/")[3];
        SocketRegistration socketRegistration = new SocketRegistration();
        socketRegistration.setSocketId(headers.getSessionId());
        socketRegistration.setActive(true);
        socketRegistration.setJoinedTime(LocalDateTime.now());
        socketRegistration.setActive(true);
        socketRegistration.setRoomId(Long.parseLong(roomId));
        saveSocketRegistration(socketRegistration);
        Long numberUserInRoom = socketRegistrationRepositoryCustom.countUserInRoom(roomId);
        if (numberUserInRoom > 0) {
            simpMessagingTemplate.convertAndSend(headers.getDestination(),
                    new ResponseMessage().builder().type(ResponseType.USER_JOIN_ROOM).message(numberUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

    public void handleUnsubscribeRoom(SessionUnsubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headers.getDestination().split("/")[3];
        socketRegistrationRepositoryCustom.killAllSocketRegistration(headers.getSessionId());
        Long numberUserInRoom = socketRegistrationRepositoryCustom.countUserInRoom(roomId);
        if (numberUserInRoom > 0) {
            simpMessagingTemplate.convertAndSend(headers.getDestination(),
                    new ResponseMessage().builder().type(ResponseType.USER_LEFT_ROOM).message(numberUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

}
