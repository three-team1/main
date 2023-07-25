package com.main.miniproject.chatting;

import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	

//    private Set<String> connectedUsers = ConcurrentHashMap.newKeySet();
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket-app")
//                .setHandshakeHandler(new DefaultHandshakeHandler() {
//                    @Override
//                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
//                        Principal principal = request.getPrincipal();
//                        if (principal != null) {
//                            connectedUsers.add(principal.getName());
//                        }
//                        return principal;
//                    }
//                })
//                .withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @EventListener
//    public void handleSessionDisconnect(SessionDisconnectEvent event) {
//        String username = event.getUser().getName();
//        connectedUsers.remove(username);
//    }
    private final Map<String, Set<String>> connectedUsers = new ConcurrentHashMap<>();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket-app/{roomId}")
                .setHandshakeHandler(new DefaultHandshakeHandler() {

                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        Principal principal = request.getPrincipal();
                        if (principal != null) {
                            String roomId = ((ServletServerHttpRequest) request).getServletRequest().getRequestURI().split("/")[2];
                            attributes.put("roomId", roomId);
                            connectedUsers.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(principal.getName());
                        }
                        return principal;
                    }
                })
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) sha.getSessionAttributes().get("username");
        String roomId = (String) sha.getSessionAttributes().get("roomId");
        Set<String> usersInRoom = connectedUsers.get(roomId);
        if (usersInRoom != null) {
            usersInRoom.remove(username);
        }
    }

    public int getConnectedUsersSize(String roomId) {
        Set<String> usersInRoom = connectedUsers.get(roomId);
        return usersInRoom != null ? usersInRoom.size() : 0;
    }

}


