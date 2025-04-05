package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.DoctorNotification;

public interface DoctorNotificationRepository extends MongoRepository<DoctorNotification, String> {
}