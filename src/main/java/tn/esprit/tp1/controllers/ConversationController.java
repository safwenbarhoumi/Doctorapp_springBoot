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

    @PostMapping("/AllConversationOfUser")
    public List<Conversation> getAllConversationsForUser(@RequestBody String userEmail ) {
        ///userEmail = request.get("usermail");
        return conversationService.getAllConversationsForUser(userEmail);
    }

    /*
    @PostMapping("/start")
    public String startConversation(@RequestParam String doctorId, @RequestParam String patientId,
                                    @RequestParam String doctorPhoto, @RequestParam String patientPhoto) {
        return conversationService.startConversation(doctorId, patientId, doctorPhoto, patientPhoto);
    }*/

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
    /*
    @GetMapping("/user/{userId}")
    public List<Conversation> getAllConversationsForUser(@PathVariable String userId) {
        return conversationService.getAllConversationsForUser(userId);
    }*/

    @GetMapping("/{conversationId}")
    public List<Message> getMessagesByConversationId(@PathVariable String conversationId) {
        return conversationService.getMessagesByConversationId(conversationId);
    }
}
