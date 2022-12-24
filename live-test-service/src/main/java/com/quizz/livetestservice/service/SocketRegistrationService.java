package com.quizz.livetestservice.service;

import com.quizz.livetestservice.common.MessageStatus;
import com.quizz.livetestservice.common.ResponseType;
import com.quizz.livetestservice.dto.response.ResponseMessage;
import com.quizz.livetestservice.listener.WebSocketEventListener;
import com.quizz.livetestservice.model.SocketRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class SocketRegistrationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void handleShutdownSocket(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        SocketRegistration socketRegistration = WebSocketEventListener.socketRegistrationMap.get(headers.getSessionId());
        WebSocketEventListener.killAllSocketRegistration(headers.getSessionId());
        Long numberUserInRoom = WebSocketEventListener.countUserInRoom(socketRegistration.getRoomId());
        log.info("socketRegistrationMap: {}", WebSocketEventListener.socketRegistrationMap);
        if (numberUserInRoom > 0) {
            simpMessagingTemplate.convertAndSend("/topic/room-message/" + socketRegistration.getRoomId(),
                    new ResponseMessage().builder().type(ResponseType.USER_LEFT_ROOM).message(numberUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

    public void handleSubscribeRoom(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headers.getDestination().split("/")[3];
        SocketRegistration socketRegistration = new SocketRegistration();
        socketRegistration.setActive(true);
        socketRegistration.setJoinedTime(LocalDateTime.now());
        socketRegistration.setActive(true);
        socketRegistration.setRoomId(Long.parseLong(roomId));
        WebSocketEventListener.socketRegistrationMap.put(headers.getSessionId(), socketRegistration);
        log.info("socketRegistrationMap: {}", WebSocketEventListener.socketRegistrationMap);
        Long numberUserInRoom = WebSocketEventListener.countUserInRoom(Long.parseLong(roomId));
        if (numberUserInRoom > 0) {
            simpMessagingTemplate.convertAndSend(headers.getDestination(),
                    new ResponseMessage().builder().type(ResponseType.USER_JOIN_ROOM).message(numberUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

    public void handleUnsubscribeRoom(SessionUnsubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headers.getDestination().split("/")[3];
        WebSocketEventListener.killAllSocketRegistration(headers.getSessionId());
        Long numberUserInRoom = WebSocketEventListener.countUserInRoom(Long.parseLong(roomId));
        log.info("socketRegistrationMap: {}", WebSocketEventListener.socketRegistrationMap);
        if (numberUserInRoom > 0) {
            simpMessagingTemplate.convertAndSend(headers.getDestination(),
                    new ResponseMessage().builder().type(ResponseType.USER_LEFT_ROOM).message(numberUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

}
