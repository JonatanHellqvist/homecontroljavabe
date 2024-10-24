package com.homecontroljavabe.homecontroljavabe.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
	
	private final SimpMessagingTemplate	messagingTemplate;

	public WebSocketService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

	public void notifyFrontend(Object update) {
		messagingTemplate.convertAndSend("/topic/update", update);
	}
}
