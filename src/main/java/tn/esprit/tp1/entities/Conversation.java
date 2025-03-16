package tn.esprit.tp1.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Document(collection = "conversations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    @Id
    private String id;
    private String doctorId;
    private String patientId;
    private Date lastMessageTime;
    private String lastMessageContent;
    private String lastMessageSender;
    private String doctorPhoto;
    private String patientPhoto;
    private String doctorEmail;
    private String patientEmail;
    private List<Message> messages = new ArrayList<>();
}
