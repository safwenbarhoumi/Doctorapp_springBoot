package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tp1.auth.dto.LoginRequest;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.services.DoctorService;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/signup")
    public String signup(@RequestBody Doctor doctor) {
        return doctorService.registerDoctor(doctor);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return doctorService.loginDoctor(request.getEmail(), request.getPassword());
    }

    @PutMapping("/complete-profile")
    public String completeProfile(@RequestParam String email,
                                  @RequestParam String photo,
                                  @RequestParam String specialty,
                                  @RequestParam String location,
                                  @RequestParam String description,
                                  @RequestParam int numberExperience,
                                  @RequestParam int numberPatients,
                                  @RequestParam float numberRating) {
        return doctorService.completeProfile(email, photo, specialty, location, description, numberExperience, numberPatients, numberRating);
    }
}
