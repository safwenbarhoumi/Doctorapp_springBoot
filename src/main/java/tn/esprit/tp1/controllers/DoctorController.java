package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tp1.auth.dto.LoginRequest;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.services.DoctorService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Doctor doctor) {
        return doctorService.registerDoctor(doctor);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return doctorService.loginDoctor(request.getEmail(), request.getPassword());
    }


    @PostMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(
            @RequestParam("email") String email,
            @RequestBody Doctor profileData) {

        Optional<Doctor> doctorOptional = doctorService.findByEmail(email);

        if (doctorOptional.isEmpty()) {
            return ResponseEntity.status(404).body("Doctor not found!");
        }

        Doctor doctor = doctorOptional.get();
        doctor.setSpecialty(profileData.getSpecialty());
        doctor.setLocation(profileData.getLocation());
        doctor.setDescription(profileData.getDescription());
        doctor.setNumberExperience(profileData.getNumberExperience());
        doctor.setNumberPatients(profileData.getNumberPatients());
        doctor.setProfileCompleted(true);

        doctorService.updateDoctor(doctor);

        return ResponseEntity.ok("Profile updated successfully!");
    }




    @GetMapping("/details/{email}")
    public ResponseEntity<Doctor> getDoctorDetails(@PathVariable String email) {
        return ResponseEntity.ok(doctorService.getDoctorDetails(email));
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Map<String, Object>>> getTop10Doctors() {
        return ResponseEntity.ok(doctorService.getTop10Doctors());
    }

    @GetMapping("/category/{specialty}")
    public ResponseEntity<List<Map<String, Object>>> getDoctorsByCategory(@PathVariable String specialty) {
        return ResponseEntity.ok(doctorService.getDoctorsByCategory(specialty));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

}
