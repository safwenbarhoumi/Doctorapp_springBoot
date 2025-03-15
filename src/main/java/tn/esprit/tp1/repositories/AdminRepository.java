package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.Admin;

import java.util.Optional;

public interface AdminRepository extends MongoRepository<Admin, String> {
    Optional<Admin> findByEmail(String email);
}