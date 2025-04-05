package tn.esprit.tp1.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import tn.esprit.tp1.entities.Appointment;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
}