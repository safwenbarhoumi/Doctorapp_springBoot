package tn.esprit.tp1.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    private String id;
    private String senderEmail;
    private String conversationId;
    private String senderId;
    private String content;
    private MessageType messageType;
    private LocalDateTime timestamp;
}
