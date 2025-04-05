package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.AppointmentHistory;

public interface AppointmentHistoryRepository extends MongoRepository<AppointmentHistory, String> {
}
