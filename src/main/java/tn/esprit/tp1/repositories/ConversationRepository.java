package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import tn.esprit.tp1.entities.Conversation;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends MongoRepository<Conversation, String> {
    // List<Conversation> findByDoctorIdOrPatientId(String doctorId, String patientId);
    Optional<Conversation> findByDoctorEmailAndPatientEmail(String doctorEmail, String patientEmail);

    @Query("{ $or: [ { 'doctorEmail': ?0 }, { 'patientEmail': ?0 } ] }")
    List<Conversation> findByUserEmail(String email);



}
