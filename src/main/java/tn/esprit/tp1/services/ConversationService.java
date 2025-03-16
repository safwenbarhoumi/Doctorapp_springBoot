package tn.esprit.tp1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.*;
import tn.esprit.tp1.repositories.ConversationRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.repositories.PatientRepository;

import java.time.LocalDateTime;
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

    /*
    public String startConversation(String doctorId, String patientId, String doctorPhoto, String patientPhoto) {
        Optional<Conversation> existingConversation = conversationRepository.findByDoctorEmailAndPatientEmail(doctorId, patientId);
        if (existingConversation.isPresent()) {
            return "Conversation already exists!";
        }
        Conversation conversation = new Conversation();
        conversation.setDoctorId(doctorId);
        conversation.setPatientId(patientId);
        conversation.setDoctorPhoto(doctorPhoto);
        conversation.setPatientPhoto(patientPhoto);
        conversation.setLastMessageTime(new Date());
        conversationRepository.save(conversation);
        return "Conversation started successfully!";
    }*/

    public String startConversation(String senderEmail, String receiverEmail, String doctorPhoto, String patientPhoto) {
        // Check if conversation already exists
        Optional<Conversation> existingConversation = conversationRepository
                .findByDoctorEmailAndPatientEmail(senderEmail, receiverEmail);

        if (existingConversation.isPresent()) {
            return "Conversation already exists!";
        }

        // Create a new conversation
        Conversation conversation = new Conversation();
        conversation.setDoctorEmail(senderEmail);
        conversation.setPatientEmail(receiverEmail);
        conversation.setDoctorPhoto(doctorPhoto);
        conversation.setPatientPhoto(patientPhoto);
        conversation.setLastMessageTime(new Date());

        conversationRepository.save(conversation);
        return "Conversation started successfully!";
    }


    public MessageResponse sendMessage(String senderEmail, String receiverEmail, String content, MessageType type) {
        Optional<Conversation> existingConversation = conversationRepository.findByDoctorEmailAndPatientEmail(senderEmail, receiverEmail);
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
        newMessage.setTimestamp(LocalDateTime.now());
        conversation.getMessages().add(newMessage);
        conversationRepository.save(conversation);
        return new MessageResponse(conversation.getId(), "Message sent successfully!");
    }

    /*
    public List<Conversation> getAllConversationsForUser(String userId) {
        return conversationRepository.findByDoctorIdOrPatientId(userId, userId);
    }*/

    public List<Conversation> getAllConversationsForUser(String email) {
        System.out.println("Fetching conversations for email: " + email);
        return conversationRepository.findByUserEmail(email);
    }

    public List<Message> getMessagesByConversationId(String conversationId) {
        return conversationRepository.findById(conversationId).map(Conversation::getMessages).orElse(Collections.emptyList());
    }
}
