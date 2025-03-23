package tn.esprit.tp1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.repositories.DoctorRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }


    public String registerDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor.getEmail()).isPresent()) {
            return "Email already in use!";
        }
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctorRepository.save(doctor);
        return "Doctor registered successfully!";
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.save(doctor); // Cette ligne met bien à jour les données en base
    }


    public String loginDoctor(String email, String password) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(email);

        if (doctor.isPresent()) {
            Doctor d = doctor.get();
            if (passwordEncoder.matches(password, d.getPassword())) { // Vérifier le mot de passe haché
                return "Login successful";
            } else {
                return "Invalid credentials";
            }
        } else {
            return "User not found";
        }
    }


    public Doctor getDoctorDetails(String email) {
        return doctorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Doctor not found!"));
    }


    public Optional<Doctor> findByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public void completeDoctorProfile(Doctor doctor) {
        // Mettre à jour les informations du docteur
        doctor.setProfileCompleted(true); // Exemple de mise à jour d'un champ

        // Enregistrer les modifications
        doctorRepository.save(doctor);
    }

    public String saveDoctorPhoto(MultipartFile photo) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
        Path photoPath = Paths.get("uploads/doctors/" + fileName);

        // Créer le dossier s'il n'existe pas
        Files.createDirectories(photoPath.getParent());
        Files.write(photoPath, photo.getBytes());

        return fileName; // Retourne le nom du fichier enregistré
    }



    public List<Map<String, Object>> getTop10Doctors() {
        return doctorRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Doctor::getNumberRating).reversed())
                .limit(10)
                .map(doctor -> {
                    Map<String, Object> doctorMap = new HashMap<>();
                    doctorMap.put("name", doctor.getFirstName() + " " + doctor.getLastName());
                    doctorMap.put("photo", doctor.getPhoto());
                    doctorMap.put("rating", doctor.getNumberRating());
                    doctorMap.put("specialty", doctor.getSpecialty());
                    return doctorMap;
                })
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getDoctorsByCategory(String specialty) {
        return doctorRepository.findBySpecialty(specialty).stream()
                .map(doctor -> {
                    Map<String, Object> doctorMap = new HashMap<>();
                    doctorMap.put("name", doctor.getFirstName() + " " + doctor.getLastName());
                    doctorMap.put("photo", doctor.getPhoto());
                    doctorMap.put("rating", doctor.getNumberRating());
                    doctorMap.put("specialty", doctor.getSpecialty());
                    return doctorMap;
                })
                .collect(Collectors.toList());
    }


}
