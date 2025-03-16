package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entities.Conversation;
import tn.esprit.tp1.entities.Message;
import tn.esprit.tp1.entities.MessageResponse;
import tn.esprit.tp1.entities.MessageType;
import tn.esprit.tp1.services.ConversationService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/conversations")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;


    @GetMapping("/user/{email}")
    public ResponseEntity<List<Conversation>> getAllConversationsByUser(@PathVariable String email) {
        List<Conversation> conversations = conversationService.getAllConversationsByUserEmail(email);
        return ResponseEntity.ok(conversations);
    }

    @PostMapping("/start")
    public String startConversation(@RequestParam String senderEmail,
                                    @RequestParam String receiverEmail,
                                    @RequestParam String doctorPhoto,
                                    @RequestParam String patientPhoto) {
        return conversationService.startConversation(senderEmail, receiverEmail, doctorPhoto, patientPhoto);
    }


    @PostMapping("/send-message")
    public ResponseEntity<MessageResponse> sendMessage(@RequestParam String senderEmail,
                                                       @RequestParam String receiverEmail,
                                                       @RequestParam String content,
                                                       @RequestParam MessageType type) {
        MessageResponse response = conversationService.sendMessage(senderEmail, receiverEmail, content, type);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{conversationId}")
    public List<Message> getMessagesByConversationId(@PathVariable String conversationId) {
        return conversationService.getMessagesByConversationId(conversationId);
    }
}
