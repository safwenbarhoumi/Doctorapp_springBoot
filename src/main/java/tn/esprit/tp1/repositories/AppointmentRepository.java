package tn.esprit.tp1.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import tn.esprit.tp1.entities.Appointment;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByDoctorIdAndPatientId(String doctorId, String patientId);

    List<Appointment> findByPatientId(String patientId);

}