package com.main.miniproject.chatting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.chatting.entity.ChatRoom;
import com.main.miniproject.chatting.repository.ChatRoomRepository;

@Service
public class ChatRoomService {

	   @Autowired
	    private ChatRoomRepository chatRoomRepository;

	    public List<ChatRoom> getAllChatRooms() {
	        return chatRoomRepository.findAll();
	    }

	    public ChatRoom getChatRoom(Long id) {
	        return chatRoomRepository.findById(id).orElse(null);
	    }

	    public ChatRoom createChatRoom(ChatRoom chatRoom) {
	        return chatRoomRepository.save(chatRoom);
	    }
	
	
	
	
}
