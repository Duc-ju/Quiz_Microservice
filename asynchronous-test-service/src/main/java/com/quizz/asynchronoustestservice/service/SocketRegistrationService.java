package com.quizz.asynchronoustestservice.service;

import com.quizz.asynchronoustestservice.common.MessageStatus;
import com.quizz.asynchronoustestservice.common.ResponseType;
import com.quizz.asynchronoustestservice.dto.response.ResponseMessage;
import com.quizz.asynchronoustestservice.listener.WebSocketEventListener;
import com.quizz.asynchronoustestservice.model.SocketRegistration;
import com.quizz.asynchronoustestservice.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Log4j2
public class SocketRegistrationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void handleShutdownSocket(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        SocketRegistration socketRegistration = WebSocketEventListener.socketRegistrationMap.get(headers.getSessionId());
        WebSocketEventListener.userInfoMap.remove(headers.getSessionId());
        WebSocketEventListener.killAllSocketRegistration(headers.getSessionId());
        if (socketRegistration == null) return;
        List<UserInfo> listUserInRoom = WebSocketEventListener.getListUserInRoom(socketRegistration.getRoomId());
        log.info("socketRegistrationMap: {}", WebSocketEventListener.socketRegistrationMap);
        if (listUserInRoom.size() > 0) {
            simpMessagingTemplate.convertAndSend("/topic/room-message/" + socketRegistration.getRoomId(),
                    new ResponseMessage().builder().type(ResponseType.USER_LEFT_ROOM).message(listUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

    public void handleSubscribeRoom(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headers.getDestination().split("/")[3];
        String checkerRole = headers.getDestination().split("/")[2];
        if ("room-admin".equals(checkerRole)) {
            return;
        }
        SocketRegistration socketRegistration = new SocketRegistration();
        socketRegistration.setActive(true);
        socketRegistration.setJoinedTime(LocalDateTime.now());
        socketRegistration.setRoomId(Long.parseLong(roomId));
        socketRegistration.setUserInfo(WebSocketEventListener.userInfoMap.get(headers.getSessionId()));
        WebSocketEventListener.socketRegistrationMap.put(headers.getSessionId(), socketRegistration);
        log.info("socketRegistrationMap: {}", WebSocketEventListener.socketRegistrationMap);
        List<UserInfo> listUserInRoom = WebSocketEventListener.getListUserInRoom(Long.parseLong(roomId));
        if (listUserInRoom.size() > 0) {
            simpMessagingTemplate.convertAndSend(headers.getDestination(),
                    new ResponseMessage().builder().type(ResponseType.USER_JOIN_ROOM).message(listUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

    public void handleUnsubscribeRoom(SessionUnsubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String roomId = headers.getDestination().split("/")[3];
        WebSocketEventListener.killAllSocketRegistration(headers.getSessionId());
        List<UserInfo> listUserInRoom = WebSocketEventListener.getListUserInRoom(Long.parseLong(roomId));
        log.info("socketRegistrationMap: {}", WebSocketEventListener.socketRegistrationMap);
        if (listUserInRoom.size() > 0) {
            simpMessagingTemplate.convertAndSend(headers.getDestination(),
                    new ResponseMessage().builder().type(ResponseType.USER_LEFT_ROOM).message(listUserInRoom).status(MessageStatus.SUCCESS).build());
        }
    }

    public void handleSessionConnected(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Map nativeHeaders = (Map) event.getMessage().getHeaders().get("nativeHeaders");
        List<String> userIds = (List<String>) nativeHeaders.get("userId");
        List<String> avatars = (List<String>) nativeHeaders.get("avatar");
        List<String> nicknames = (List<String>) nativeHeaders.get("nickname");
        List<String> usernames = (List<String>) nativeHeaders.get("username");
        UserInfo userInfo = new UserInfo();
        if (userIds != null && userIds.size() > 0) userInfo.setUserId(userIds.get(0));
        if (avatars != null && avatars.size() > 0) userInfo.setAvatar(avatars.get(0));
        if (nicknames != null && nicknames.size() > 0) userInfo.setNickname(nicknames.get(0));
        if (usernames != null && usernames.size() > 0) userInfo.setUsername(usernames.get(0));
        WebSocketEventListener.userInfoMap.put(headers.getSessionId(), userInfo);
        log.info("userInfoMap: {}", WebSocketEventListener.userInfoMap);
    }
}
