package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.Patient;

import java.util.Optional;

// Patient Repository
public interface PatientRepository extends MongoRepository<Patient, String> {
    Optional<Patient> findByEmail(String email);
}
