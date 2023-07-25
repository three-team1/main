package com.main.miniproject.chatting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.miniproject.chatting.ChatMessageDTO;
import com.main.miniproject.chatting.entity.ChatMessages;
import com.main.miniproject.chatting.repository.ChatMessagesRepository;

@Service
public class ChatMessagesService {

    @Autowired
    private ChatMessagesRepository chatMessagesRepository;

    @Autowired
    private ChatRoomService chatRoomService;

    // Save a new message
    public ChatMessageDTO saveMessage(ChatMessageDTO dto) {
        ChatMessages entity = new ChatMessages();
        entity.setContent(dto.getContent());
        entity.setSender(dto.getSender());
        entity.setType(dto.getType());
        entity.setChatRoom(chatRoomService.getChatRoom(Long.parseLong(dto.getChatRoomId())));

        ChatMessages savedEntity = chatMessagesRepository.save(entity);
        return convertEntityToDto(savedEntity);
    }
    
    private ChatMessageDTO convertEntityToDto(ChatMessages entity) {
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setContent(entity.getContent());
        dto.setSender(entity.getSender());
        dto.setType(entity.getType());
        // Assuming chat room ID is available in the entity
        dto.setChatRoomId(String.valueOf(entity.getChatRoom().getId()));
        dto.setMessageRegdate(entity.getMessageRegdateAsString());
        return dto;
    }

    // Retrieve all messages of a chat room
    public List<ChatMessages> getMessagesByChatRoom(Long roomId) {
        return chatMessagesRepository.findByChatRoomId(roomId);
    }
    
    public List<ChatMessages> searchByContent(Long chatRoomId,String searchKeyword){
    	return chatMessagesRepository.searchByContent(chatRoomId, searchKeyword );
    }

		
}

	

