package com.quizz.notificationservice.listener;

import com.quizz.notificationservice.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
@Log4j2
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final WebSocketService webSocketService;

    @EventListener
    private void handleSessionSubscribe(SessionSubscribeEvent event) {
        log.info("Socket subscribed: {}, user: {}", event, event.getUser());
        webSocketService.handleSubscribeRoom(event);
    }
}
