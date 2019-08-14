package de.pheru.demo.websocket;

import de.pheru.demo.websocket.controllers.AdvancedWebSocketController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebsocketEndpointTest {

    @Value("${local.server.port}")
    private int port;
    private String URL;

    private static final String MESSAGE_ENDPOINT = "/app/simple/messagetopic";
    private static final String SUBSCRIBE_ENDPOINT = "/topic/simple";

    private CompletableFuture<String> completableFuture;
    private CompletableFuture<AdvancedWebSocketController.Pups> completableFutureP;

    @Before
    public void setup() {
        completableFuture = new CompletableFuture<>();
        completableFutureP = new CompletableFuture<>();
        URL = "ws://localhost:" + port + "/websocket-demo";
    }

    @Test
    public void simpleTest() throws Exception {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
//        stompClient.setMessageConverter(new StringMessageConverter());
        stompClient = new WebSocketStompClient(new SockJsClient(
                asList(new WebSocketTransport(new StandardWebSocketClient()))));

        final StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
        }).get(1, SECONDS);

        stompSession.subscribe(SUBSCRIBE_ENDPOINT, new SimpleStompFrameHandler());
        stompSession.send(MESSAGE_ENDPOINT, "Hallo?");

        final String value = completableFuture.get(10, SECONDS);

        assertNotNull(value);
    }

    private List<Transport> createTransportClient() {
        final List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class SimpleStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(final StompHeaders stompHeaders) {
            System.out.println("HEADERS: " + stompHeaders.toString());
            return String.class;
        }

        @Override
        public void handleFrame(final StompHeaders stompHeaders, final Object o) {
            System.out.println("COMPLETE?");
            completableFuture.complete((String) o);
        }
    }

    private class PupsStompFrameHandler implements StompFrameHandler {

        @Override
        public Type getPayloadType(final StompHeaders stompHeaders) {
            System.out.println("HEADERS PUPS: " + stompHeaders.toString());
            return AdvancedWebSocketController.Pups.class;
        }

        @Override
        public void handleFrame(final StompHeaders stompHeaders, final Object o) {
            System.out.println("COMPLETE PUPS?");
            completableFutureP.complete((AdvancedWebSocketController.Pups) o);
        }
    }
}