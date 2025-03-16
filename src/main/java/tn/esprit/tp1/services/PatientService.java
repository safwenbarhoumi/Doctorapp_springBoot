package tn.esprit.tp1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.Admin;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.entities.Patient;
import tn.esprit.tp1.entities.Role;
import tn.esprit.tp1.repositories.AdminRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.repositories.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private AdminRepository adminRepository;

    public String registerPatient(Patient patient) {
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            return "Email already in use!";
        }
        // Encrypt password before saving
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patientRepository.save(patient);
        return "Patient registered successfully!";
    }


    public String login(String email, String rawPassword, Role role) {
        switch (role) {
            case PATIENT:
                return authenticateUser(patientRepository.findByEmail(email).orElse(null), rawPassword);
            case DOCTOR:
                return authenticateUser(doctorRepository.findByEmail(email).orElse(null), rawPassword);
            case ADMIN:
                return authenticateUser(adminRepository.findByEmail(email).orElse(null), rawPassword);
            default:
                return "Invalid role!";
        }
    }


    private String authenticateUser(Object user, String rawPassword) {
        if (user == null) {
            return "User not found!";
        }

        // Vérifiez si l'objet a bien un champ "password"
        if (user instanceof Patient) {
            Patient patient = (Patient) user;
            return passwordEncoder.matches(rawPassword, patient.getPassword()) ? "Login successful!" : "Invalid credentials!";
        } else if (user instanceof Doctor) {
            Doctor doctor = (Doctor) user;
            return passwordEncoder.matches(rawPassword, doctor.getPassword()) ? "Login successful!" : "Invalid credentials!";
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            return passwordEncoder.matches(rawPassword, admin.getPassword()) ? "Login successful!" : "Invalid credentials!";
        }

        return "Invalid user type!";
    }





}