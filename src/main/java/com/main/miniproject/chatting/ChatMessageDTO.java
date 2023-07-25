package com.main.miniproject.chatting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {

    private MessageType type;
    private String content;
    private String sender;
    private String chatRoomId;
    private String messageRegdate;
    
   public ChatMessageDTO() {
    }

    public ChatMessageDTO(MessageType type, String content, String sender, String chatRoomId,String messageRegdate) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.chatRoomId = chatRoomId;
        this.messageRegdate = messageRegdate;
    }
}
