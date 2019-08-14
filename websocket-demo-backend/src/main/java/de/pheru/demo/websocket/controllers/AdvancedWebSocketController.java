package de.pheru.demo.websocket.controllers;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Controller;

@Controller
public class AdvancedWebSocketController {

    private final SimpMessageSendingOperations messagingTemplate;

    public AdvancedWebSocketController(final SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/forid/messagetopic/{id}")
    @SendTo("/topic/forid/{id}")
    public String forIdTopic(@DestinationVariable final String id,
                             @Payload final String payload) {
        return "Forid Topic: id=" + id + ",payload=" + payload;
    }

    @MessageMapping("/forid/messagequeue/{id}")
    @SendTo("/queue/forid/{id}")
    public String forIdQueue(@DestinationVariable final String id,
                             @Payload final String payload) {
        return "Forid Queue: id=" + id + ",payload=" + payload;
    }

    @MessageMapping("/setattr")
    public void setSessionAttribute(@Payload final String payload,
                                    final SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("tolles_attribut", payload);
    }

    @MessageMapping("/convertandsend/simple")
    public void convertAndSendSimple(@Payload final String payload,
                                     final SimpMessageHeaderAccessor headerAccessor) {
        final SimpMessageHeaderAccessor newHeaderAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        newHeaderAccessor.setSessionId(headerAccessor.getSessionId());
        newHeaderAccessor.setLeaveMutable(true);
        final MessageHeaders headers = newHeaderAccessor.getMessageHeaders();

        messagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/queue/simple", "castu+header queue simple: payload=" + payload, headers);
        messagingTemplate.convertAndSend("/queue/simple", "cas+header queue simple: payload=" + payload, headers);
        messagingTemplate.convertAndSend("/queue/simple", "cas queue simple: payload=" + payload);

        messagingTemplate.convertAndSendToUser(headerAccessor.getSessionId(), "/topic/simple", "castu+header topic simple: payload=" + payload, headers);
        messagingTemplate.convertAndSend("/topic/simple", "cas+header topic simple: payload=" + payload, headers);
        messagingTemplate.convertAndSend("/topic/simple", "cas topic simple: payload=" + payload);
    }


}
