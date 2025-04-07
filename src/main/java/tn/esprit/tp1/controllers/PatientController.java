package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.auth.dto.LoginRequest;
import tn.esprit.tp1.entities.Patient;
import tn.esprit.tp1.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping("/signup")
    public String signup(@RequestBody Patient patient) {
        return patientService.registerPatient(patient);
    }

    /*
     * @PostMapping("/login")
     * public String login(@RequestBody LoginRequest request) {
     * return patientService.login(request.getEmail(),
     * request.getPassword(),request.getRole());
     * }
     */

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        Object user = patientService.login(request.getEmail(), request.getPassword(), request.getRole());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or user not found.");
        }
        return ResponseEntity.ok(user);
    }
}
