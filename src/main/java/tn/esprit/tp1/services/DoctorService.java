package tn.esprit.tp1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.repositories.DoctorRepository;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public String registerDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            return "Email already in use!";
        }
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctorRepository.save(doctor);
        return "Doctor registered successfully!";
    }



    public String loginDoctor(String email, String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);
        return doctor.map(d -> d.getPassword().equals(password) ? "Login successful" : "Invalid credentials")
                .orElse("User not found");
    }


    public String completeProfile(String email, String photo, String specialty, String location, String description,
                                  int numberExperience, int numberPatients, float numberRating) {
        Optional<Doctor> doctorOptional = doctorRepository.findByEmail(email);

        if (doctorOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            doctor.setPhoto(photo);
            doctor.setSpecialty(specialty);
            doctor.setLocation(location);
            doctor.setDescription(description);
            doctor.setNumberExperience(numberExperience);
            doctor.setNumberPatients(numberPatients);
            doctor.setNumberRating(numberRating);

            doctorRepository.save(doctor);
            return "Profile updated successfully!";
        } else {
            return "Doctor not found!";
        }
    }
}
