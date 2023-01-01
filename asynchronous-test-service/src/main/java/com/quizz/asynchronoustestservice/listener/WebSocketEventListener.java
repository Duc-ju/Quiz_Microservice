package com.quizz.asynchronoustestservice.listener;

import com.quizz.asynchronoustestservice.model.SocketRegistration;
import com.quizz.asynchronoustestservice.model.UserInfo;
import com.quizz.asynchronoustestservice.service.SocketRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SocketRegistrationService socketRegistrationService;
    public static Map<String, SocketRegistration> socketRegistrationMap = new HashMap<>();
    public static Map<String, UserInfo> userInfoMap = new HashMap<>();

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        log.info("Socket connected: {}", event);
        socketRegistrationService.handleSessionConnected(event);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("Socket disconnect: {}, user: {}", event, event.getUser());
        socketRegistrationService.handleShutdownSocket(event);
    }

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) {
        log.info("Socket subscribed: {}, user: {}", event, event.getUser());
        socketRegistrationService.handleSubscribeRoom(event);
    }

    @EventListener
    private void handleSessionUnsubscribe(SessionUnsubscribeEvent event) {
        log.info("Socket unsubscribed: {}", event);
        socketRegistrationService.handleUnsubscribeRoom(event);
    }

    public static List<UserInfo> getListUserInRoom(Long roomId) {
        List<UserInfo> userInfos = new ArrayList<>();
        for (String key : socketRegistrationMap.keySet()) {
            SocketRegistration socketRegistration = socketRegistrationMap.get(key);
            if (socketRegistration.getRoomId().equals(roomId)) {
                userInfos.add(socketRegistration.getUserInfo());
            }
        }
        return userInfos;
    }

    public static void killAllSocketRegistration(String socketId) {
        socketRegistrationMap.remove(socketId);
    }
}
