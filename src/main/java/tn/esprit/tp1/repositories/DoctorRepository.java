package tn.esprit.tp1.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.Doctor;

import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Optional<Doctor> findByEmail(String email);
}