package tn.esprit.tp1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.*;
import tn.esprit.tp1.repositories.ConversationRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.repositories.PatientRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

    public List<Conversation> getAllConversationsByUserEmail(String email) {
        List<Conversation> conversations = conversationRepository.findByUserEmail(email);
        for (Conversation conversation : conversations) {
            conversation.setLastMessageTimeFormatted(timeFormatter.format(conversation.getLastMessageTime().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime()));
        }
        return conversations;
    }

    public String startConversation(String senderEmail, String receiverEmail, String doctorPhoto, String patientPhoto) {
        Optional<Conversation> existingConversation = conversationRepository
                .findByDoctorEmailAndPatientEmail(senderEmail, receiverEmail);

        if (existingConversation.isPresent()) {
            return "Conversation already exists!";
        }

        Conversation conversation = new Conversation();
        conversation.setDoctorEmail(senderEmail);
        conversation.setPatientEmail(receiverEmail);
        conversation.setDoctorPhoto(doctorPhoto);
        conversation.setPatientPhoto(patientPhoto);
        conversation.setLastMessageTime(new Date());
        conversation.setLastMessageTimeFormatted(timeFormatter.format(LocalDateTime.now()));

        conversationRepository.save(conversation);
        return "Conversation started successfully!";
    }

    public MessageResponse sendMessage(String senderEmail, String receiverEmail, String content, MessageType type) {
        Optional<Conversation> existingConversation = conversationRepository
                .findByDoctorEmailAndPatientEmail(senderEmail, receiverEmail);
        Conversation conversation = existingConversation.orElseGet(() -> {
            Conversation newConv = new Conversation();
            newConv.setDoctorEmail(senderEmail);
            newConv.setPatientEmail(receiverEmail);
            return conversationRepository.save(newConv);
        });

        Message newMessage = new Message();
        newMessage.setSenderEmail(senderEmail);
        newMessage.setContent(content);
        newMessage.setMessageType(type);
        newMessage.setTimestampFormatted(timeFormatter.format(LocalDateTime.now()));
        newMessage.setTimestamp(LocalDateTime.now());

        Optional<Doctor> doctor = doctorRepository.findByEmail(senderEmail);
        Optional<Patient> patient = patientRepository.findByEmail(senderEmail);
        String senderName = doctor.map(d -> d.getFirstName() + " " + d.getLastName())
                .orElseGet(() -> patient.map(p -> p.getFirstName() + " " + p.getLastName()).orElse("Unknown"));
        newMessage.setSenderName(senderName);

        conversation.getMessages().add(newMessage);
        conversation.setLastMessageTime(new Date());
        conversation.setLastMessageTimeFormatted(timeFormatter.format(LocalDateTime.now()));
        conversation.setLastMessageContent(content);
        conversation.setLastMessageSender(senderEmail);

        conversationRepository.save(conversation);
        return new MessageResponse(conversation.getId(), "Message sent successfully!");
    }

    public List<Message> getMessagesByConversationId(String conversationId) {
        return conversationRepository.findById(conversationId).map(conversation -> {
            for (Message message : conversation.getMessages()) {
                message.setTimestampFormatted(timeFormatter.format(message.getTimestamp()));
            }
            return conversation.getMessages();
        }).orElse(Collections.emptyList());
    }
}
