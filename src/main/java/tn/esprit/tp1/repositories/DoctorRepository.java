package tn.esprit.tp1.repositories;


import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findBySpecialty(String specialty);
    //Optional<Doctor> findByEmailAndRole(String email);
    // Doctor findByEmail(String email);

}