package de.pheru.demo.websocket.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SimpleWebSocketController {

    @MessageMapping("/simple/messagetopic")
    @SendTo("/topic/simple")
    public String simpleTopic(@Payload final String payload) {
        return "Simple Topic: payload=" + payload;
    }

    @MessageMapping("/simple/messagetopicP")
    @SendTo("/topic/simpleP")
    public Pups simpleTopic2(@Payload final String payload) {
        Pups p = new Pups();
        p.setS("pups " + payload);
        return p;
    }

    @MessageMapping("/simple/messagequeue")
    @SendTo("/queue/simple")
    public String simpleQueue(@Payload final String payload) {
        return "Simple Queue: payload=" + payload;
    }

    public static class Pups {
        private String s;

        public String getS() {
            return s;
        }

        public void setS(final String s) {
            this.s = s;
        }
    }
}
