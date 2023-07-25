package com.main.miniproject.chatting;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.main.miniproject.chatting.entity.ChatMessages;
import com.main.miniproject.chatting.entity.ChatRoom;
import com.main.miniproject.chatting.service.ChatMessagesService;
import com.main.miniproject.chatting.service.ChatRoomService;

@Controller
public class ChatController {
	
	
	@Autowired ChatMessagesService chatMessagesService;
	
	@Autowired ChatRoomService chatRoomService;
	
	@GetMapping("/chat") 
	public String chatRooms(Model model) {
		
		List<ChatRoom> chatRooms = chatRoomService.getAllChatRooms();
		model.addAttribute("chatRooms",chatRooms);
		
		return "chat";
	}
	
	@GetMapping("/chatRoom/{chatRoomId}")
	public String chatRoom(@PathVariable String chatRoomId, Model model,Principal principal) {
	    ChatRoom chatRoom = chatRoomService.getChatRoom(Long.parseLong(chatRoomId));
	    // Assume you have a findById method in ChatRoomService
	    List<ChatMessages> messages = chatMessagesService.getMessagesByChatRoom(Long.parseLong(chatRoomId));
	    String username = principal.getName();
	    model.addAttribute("chatRoom", chatRoom); 
	    //model.addAttribute("chatRoomId",chatRoomId);
	    model.addAttribute("username", username);
	    model.addAttribute("messages",messages);
	    return "chatRoom2"; // This should be the template for the specific chat room
	}
	

	@MessageMapping("/chat/{chatRoomId}/sendMessage")
	@SendTo("/topic/chatrooms/{chatRoomId}")
	public ChatMessageDTO sendMessage(@Payload ChatMessageDTO chatMessage, SimpMessageHeaderAccessor headerAccessor
			,@PathVariable String chatRoomId) {
	    // get the user name from the session
	    String username = (String) headerAccessor.getSessionAttributes().get("username");
	    chatMessage.setSender(username);

	    // save the message to the database
	    ChatMessageDTO savedMessage = chatMessagesService.saveMessage(chatMessage);
	    
	    
	    // return the saved message
	    return savedMessage;
	}


	@MessageMapping("/chat/{chatRoomId}/addUser")
	@SendTo("/topic/chatrooms/{chatRoomId}")
	public ChatMessageDTO addUser(@Payload ChatMessageDTO chatMessage,Principal principal, SimpMessageHeaderAccessor headerAccessor) {
			String username = principal.getName();
		    headerAccessor.getSessionAttributes().put("username",username);
		    chatMessage.setSender(username);

		    // save the message to the database
		    ChatMessageDTO savedMessage = chatMessagesService.saveMessage(chatMessage);
		    
		    // return the saved message
		    return savedMessage;
	}
    
    
	

  

}
