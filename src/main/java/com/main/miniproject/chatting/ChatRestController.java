package com.main.miniproject.chatting;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.miniproject.chatting.entity.ChatMessages;
import com.main.miniproject.chatting.entity.ChatRoom;
import com.main.miniproject.chatting.service.ChatMessagesService;
import com.main.miniproject.chatting.service.ChatRoomService;

@RestController
public class ChatRestController {

	@Autowired
	private WebSocketConfig webSocketConfig;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private ChatMessagesService chatMessagesService;
	
    @GetMapping("/connected-users/{chatRoomId}")
    public ResponseEntity<Integer> getConnectedUsers(@PathVariable String chatRoomId) {
        return ResponseEntity.ok(webSocketConfig.getConnectedUsersSize(chatRoomId));
    }
    
//    @PostMapping("/chatRoom/{chatRoomId}/enter")
//    public String enterChatRoom(@PathVariable String chatRoomId, @RequestParam String password, Model model,Principal principal) {
//        ChatRoom chatRoom = chatRoomService.getChatRoom(Long.parseLong(chatRoomId));
//        if (chatRoom.getPassword().equals(password)) {
//            // Assume you have a findById method in ChatRoomService
//            List<ChatMessages> messages = chatMessagesService.getMessagesByChatRoom(Long.parseLong(chatRoomId));
//            String username = principal.getName();
//            model.addAttribute("chatRoom", chatRoom);
//            model.addAttribute("username", username);
//            model.addAttribute("messages",messages);
//            return "chatRoom2"; // This should be the template for the specific chat room
//        } else {
//            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
//            return "error";
//        }
//    }

    @PostMapping("/chatRoom/{chatRoomId}/enter")
    public ResponseEntity<?> enterChatRoom(@PathVariable String chatRoomId, @RequestParam String password) {
        ChatRoom chatRoom = chatRoomService.getChatRoom(Long.parseLong(chatRoomId));
        if (chatRoom.getPassword().equals(password)) {
            // 비밀번호가 일치하면 정상 상태 코드와 함께 응답을 보냅니다.
            return ResponseEntity.ok().build();
        } else {
            // 비밀번호가 틀린 경우, 클라이언트가 처리할 수 있는 에러 메시지와 함께 응답을 보냅니다.
            return ResponseEntity.badRequest().body(Map.of("error", "비밀번호가 일치하지 않습니다."));
        }
    }
    
    @GetMapping("/chatRoom/{chatRoomId}/search")
    public ResponseEntity<List<ChatMessages>> searchChatRoomMessages(@PathVariable String chatRoomId, @RequestParam String keyword) {
        List<ChatMessages> searchResults = chatMessagesService.searchByContent(Long.parseLong(chatRoomId), keyword);
        return ResponseEntity.ok(searchResults);
    }
}
