package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entities.Appointment;
import tn.esprit.tp1.entities.AppointmentRequest;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.repositories.AppointmentRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.services.AppointmentService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @PostMapping("/add-available-times/{doctorId}")
    public ResponseEntity<?> addAvailableTimes(
            @PathVariable String doctorId,
            @RequestBody Map<String, List<String>> availableTimes) {

        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Doctor not found.");
        }

        Doctor doctor = doctorOptional.get();
        Map<String, List<String>> existingTimes = doctor.getAvailableTimes();

        if (existingTimes == null) {
            existingTimes = new HashMap<>();
        }

        for (Map.Entry<String, List<String>> entry : availableTimes.entrySet()) {
            existingTimes.put(entry.getKey(), entry.getValue());
        }

        doctor.setAvailableTimes(existingTimes);
        doctorRepository.save(doctor);

        return ResponseEntity.status(201).body(doctor.getAvailableTimes());
    }

    /*
     * @PostMapping("/make")
     * public ResponseEntity<Appointment> makeAppointment(@RequestBody
     * AppointmentRequest request) {
     * // Debug log
     * System.out.println(">>> Request received: " + request);
     * 
     * // Validation et logique métier
     * Optional<Doctor> optionalDoctor =
     * doctorRepository.findById(request.getDoctorId());
     * if (optionalDoctor.isEmpty()) {
     * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
     * }
     * 
     * Doctor doctor = optionalDoctor.get();
     * Map<String, List<String>> availableTimes = doctor.getAvailableTimes();
     * 
     * // Vérifier si la date et l'heure sont disponibles
     * if (availableTimes == null || !availableTimes.containsKey(request.getDate()))
     * {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
     * }
     * 
     * List<String> times = availableTimes.get(request.getDate());
     * if (!times.contains(request.getTime())) {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
     * }
     * 
     * // Créer l'objet Appointment
     * Appointment appointment = new Appointment();
     * appointment.setDoctorId(request.getDoctorId());
     * appointment.setPatientId(request.getPatientId());
     * appointment.setDate(request.getDate());
     * appointment.setTime(request.getTime());
     * 
     * // Enregistrer le rendez-vous dans le repository
     * appointmentRepository.save(appointment); // Enregistrer l'AppointmentRequest
     * dans la base
     * // Mettre à jour la disponibilité du docteur
     * times.remove(request.getTime()); // Retirer le créneau réservé
     * availableTimes.put(request.getDate(), times);
     * doctor.setAvailableTimes(availableTimes);
     * doctorRepository.save(doctor);
     * 
     * return ResponseEntity.ok(appointment);
     * }
     */

    /*
     * @PostMapping("/make")
     * public ResponseEntity<Appointment> makeAppointment(@RequestBody
     * AppointmentRequest request) {
     * Appointment appointment = appointmentService.makeAppointment(
     * request.getDoctorId(),
     * request.getPatientId(),
     * request.getDate(),
     * request.getTime());
     * 
     * if (appointment == null) {
     * return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
     * }
     * 
     * return ResponseEntity.ok(appointment);
     * }
     */

    @PostMapping("/make")
    public ResponseEntity<Appointment> makeAppointment(@RequestBody AppointmentRequest request) {
        System.out.println(">>> Request received: " + request);

        Optional<Doctor> optionalDoctor = doctorRepository.findById(request.getDoctorId());
        if (optionalDoctor.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Doctor doctor = optionalDoctor.get();
        Map<String, List<String>> availableTimes = doctor.getAvailableTimes();

        if (availableTimes == null || !availableTimes.containsKey(request.getDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<String> times = availableTimes.get(request.getDate());
        if (!times.contains(request.getTime())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        // Create the appointment using the service
        Appointment appointment = appointmentService.makeAppointment(
                request.getDoctorId(),
                request.getPatientId(),
                request.getDate(),
                request.getTime());

        if (appointment == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(appointment);
    }

}
