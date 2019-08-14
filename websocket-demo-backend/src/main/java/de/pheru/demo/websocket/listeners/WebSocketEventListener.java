package de.pheru.demo.websocket.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    public WebSocketEventListener(final SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleWebSocketConnectListener(final SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection.");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(final SessionDisconnectEvent event) {
        System.out.println("Disconnected");
        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        final Object tolles_attribut = headerAccessor.getSessionAttributes().get("tolles_attribut");
        System.out.println("Attribut=" + tolles_attribut);

        messagingTemplate.convertAndSend("/topic/simple", "disconnected: attr=" + tolles_attribut);
    }
}