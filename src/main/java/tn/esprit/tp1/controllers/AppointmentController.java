package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.entities.Appointment;
import tn.esprit.tp1.entities.AppointmentRequest;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.entities.DoctorPatientRequest;
import tn.esprit.tp1.repositories.AppointmentRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.services.AppointmentService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

    @GetMapping("/available-times/{doctorId}")
    public ResponseEntity<?> getAvailableTimesByDoctorId(@PathVariable String doctorId) {
        try {
            Map<String, List<String>> availableTimes = appointmentService.getAvailableTimesByDoctorId(doctorId);
            return ResponseEntity.ok(availableTimes);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
        }
    }

    @PostMapping("/by-doctor-patient")
    public ResponseEntity<?> getAppointmentByDoctorAndPatient(@RequestBody DoctorPatientRequest request) {
        List<Appointment> appointments = appointmentService.getAppointmentByDoctorIdAndPatientId(
                request.getDoctorId(), request.getPatientId());

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No appointments found for the given doctor and patient.");
        }

        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<String>> getAppointmentsByPatient(@PathVariable String patientId) {
        List<String> appointments = appointmentService.getAppointmentsByPatientId(patientId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonList("No appointments found."));
        }

        return ResponseEntity.ok(appointments);
    }

}
