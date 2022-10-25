package com.quizz.socketservice.listener;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Log4j2
public class WebSocketEventListener {

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {
        log.info("Socket connected: {}", event);
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("Socket disconnect: {}", event);
    }
}
