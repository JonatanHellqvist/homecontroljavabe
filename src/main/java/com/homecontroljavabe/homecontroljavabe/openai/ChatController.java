package com.homecontroljavabe.homecontroljavabe.openai;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping("/chat")
	public String postChat(@RequestBody String prompt) {
		//TODO: process POST request
		ChatResponse response = chatService.sendChatResponse(prompt);
		
		return response.getChoices().get(0).getMessage().getContent();
	}
	
	
}
