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
}
