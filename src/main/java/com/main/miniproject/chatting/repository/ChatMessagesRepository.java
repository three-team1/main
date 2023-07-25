package com.main.miniproject.chatting.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.miniproject.chatting.entity.ChatMessages;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessages, Long> {

	List<ChatMessages> findByChatRoomId(Long roomId);
	
	@Query("SELECT c FROM ChatMessages c WHERE c.chatRoom.id = :chatRoomId AND c.content LIKE %:keyword%")
	List<ChatMessages> searchByContent(@Param("chatRoomId") Long chatRoomId, @Param("keyword") String keyword);

	
}
