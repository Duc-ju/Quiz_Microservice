package com.quizz.synchronoustestservice.listener;

import com.quizz.synchronoustestservice.service.SocketRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SocketRegistrationService socketRegistrationService;

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
}
