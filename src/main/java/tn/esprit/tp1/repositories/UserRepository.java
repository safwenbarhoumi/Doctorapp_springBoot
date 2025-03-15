package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import tn.esprit.tp1.entities.User;


public interface UserRepository extends MongoRepository<User, String>{
}
