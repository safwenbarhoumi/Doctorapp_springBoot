package tn.esprit.tp1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    /*
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
    }*/

    @PostMapping(value = "/doctor/complete-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String completeProfile(
            @RequestPart("doctor") Doctor doctor,
            @RequestPart("photo") MultipartFile photo) {

        // Appel à ton service pour compléter le profil avec les données reçues
        return doctorService.completeProfile(
                doctor.getEmail(),
                String.valueOf(photo),
                doctor.getSpecialty(),
                doctor.getLocation(),
                doctor.getDescription(),
                doctor.getNumberExperience(),
                doctor.getNumberPatients(),
                doctor.getNumberRating()
        );
    }

}
