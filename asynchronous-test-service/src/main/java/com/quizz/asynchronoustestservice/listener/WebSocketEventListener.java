package com.quizz.asynchronoustestservice.listener;

import com.quizz.asynchronoustestservice.model.SocketRegistration;
import com.quizz.asynchronoustestservice.service.SocketRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SocketRegistrationService socketRegistrationService;
    public static Map<String, SocketRegistration> socketRegistrationMap = new HashMap<>();

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        log.info("Socket connected: {}", event);
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

    public static Long countUserInRoom(Long roomId) {
        Long count = Long.valueOf(0);
        for (String key : socketRegistrationMap.keySet()) {
            if (socketRegistrationMap.get(key).getRoomId().equals(roomId)) {
                count++;
            }
        }
        return count;
    }

    public static void killAllSocketRegistration(String socketId) {
        socketRegistrationMap.remove(socketId);
    }
}
