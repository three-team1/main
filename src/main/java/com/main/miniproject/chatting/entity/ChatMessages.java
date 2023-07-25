package com.main.miniproject.chatting.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.main.miniproject.chatting.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String sender;
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    private LocalDateTime messageRegdate = LocalDateTime.now();

    // getters and setters

    public String getMessageRegdateAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return messageRegdate.format(formatter);
    }

}