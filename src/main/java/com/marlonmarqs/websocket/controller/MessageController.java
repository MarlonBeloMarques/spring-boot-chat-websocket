package com.marlonmarqs.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.marlonmarqs.websocket.model.MessageModel;
import com.marlonmarqs.websocket.storage.UserStorage;

@RestController
public class MessageController {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping("/chat/{to}")
	public void sendMessage(@DestinationVariable String to, MessageModel message) {
		System.out.println("tratamento envio de mensagem: " + message + " to: " + to);
		boolean isExists = UserStorage.getInstance().getUsers().contains(to);
		if(isExists) {
			simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
		}
	}
}
