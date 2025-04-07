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

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/all")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/top10")
    public List<Doctor> getTop10Doctors() {
        return doctorService.getTop10Doctors(); // ✅ Returns full doctor info
    }

    @GetMapping("/category/{specialty}")
    public List<Doctor> getDoctorsByCategory(@PathVariable String specialty) {
        return doctorService.getDoctorsByCategory(specialty); // ✅ Full data too
    }
}

/*
 * @RestController
 * 
 * @RequestMapping("/doctor")
 * public class DoctorController {
 * 
 * @Autowired
 * private DoctorService doctorService;
 * 
 * public DoctorController(DoctorService doctorService) {
 * this.doctorService = doctorService;
 * }
 * 
 * @PostMapping("/signup")
 * public String signup(@RequestBody Doctor doctor) {
 * return doctorService.registerDoctor(doctor);
 * }
 * 
 * @PostMapping("/login")
 * public String login(@RequestBody LoginRequest request) {
 * return doctorService.loginDoctor(request.getEmail(), request.getPassword());
 * }
 * 
 * // @PostMapping("/complete-profile")
 * // public ResponseEntity<String> completeProfile(
 * // @RequestParam("email") String email,
 * // @RequestBody Doctor profileData) {
 * 
 * // Optional<Doctor> doctorOptional = doctorService.findByEmail(email);
 * 
 * // if (doctorOptional.isEmpty()) {
 * // return ResponseEntity.status(404).body("Doctor not found!");
 * // }
 * 
 * // Doctor doctor = doctorOptional.get();
 * // doctor.setSpecialty(profileData.getSpecialty());
 * // doctor.setLocation(profileData.getLocation());
 * // doctor.setDescription(profileData.getDescription());
 * // doctor.setNumberExperience(profileData.getNumberExperience());
 * // doctor.setNumberPatients(profileData.getNumberPatients());
 * // doctor.setProfileCompleted(true);
 * 
 * // doctorService.updateDoctor(doctor);
 * 
 * // return ResponseEntity.ok("Profile updated successfully!");
 * // }
 * 
 * @PostMapping(value = "/complete-profile", consumes =
 * MediaType.MULTIPART_FORM_DATA_VALUE)
 * public ResponseEntity<String> completeProfile(
 * 
 * @RequestParam("email") String email,
 * 
 * @RequestParam("specialty") String specialty,
 * 
 * @RequestParam("location") String location,
 * 
 * @RequestParam("description") String description,
 * 
 * @RequestParam("numberExperience") int numberExperience,
 * 
 * @RequestParam("numberPatients") int numberPatients,
 * 
 * @RequestParam(value = "photo", required = false) MultipartFile photo) {
 * 
 * Optional<Doctor> doctorOptional = doctorService.findByEmail(email);
 * 
 * if (doctorOptional.isEmpty()) {
 * return ResponseEntity.status(404).body("Médecin non trouvé !");
 * }
 * 
 * Doctor doctor = doctorOptional.get();
 * doctor.setSpecialty(specialty);
 * doctor.setLocation(location);
 * doctor.setDescription(description);
 * doctor.setNumberExperience(numberExperience);
 * doctor.setNumberPatients(numberPatients);
 * doctor.setProfileCompleted(true);
 * 
 * // Si une photo est envoyée, on la sauvegarde
 * if (photo != null && !photo.isEmpty()) {
 * try {
 * String fileName = saveDoctorPhoto(photo);
 * doctor.setPhoto(fileName); // Ajoute un champ photoPath dans l'entité Doctor
 * si nécessaire
 * } catch (IOException e) {
 * return ResponseEntity.status(500).
 * body("Erreur lors de l’enregistrement de la photo.");
 * }
 * }
 * 
 * doctorService.updateDoctor(doctor);
 * return ResponseEntity.ok("Profil mis à jour avec succès !");
 * }
 * 
 * private String saveDoctorPhoto(MultipartFile photo) throws IOException {
 * // Dossier où stocker les photos (tu peux le changer selon ton projet)
 * String uploadDir = "uploads/";
 * 
 * // Nom du fichier (ici on garde le nom original, mais tu peux le modifier)
 * String fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
 * 
 * // Crée le dossier s'il n'existe pas
 * File dir = new File(uploadDir);
 * if (!dir.exists()) {
 * dir.mkdirs();
 * }
 * 
 * // Sauvegarde du fichier sur le disque
 * File destination = new File(uploadDir + fileName);
 * photo.transferTo(destination);
 * 
 * return fileName; // Tu peux retourner aussi le chemin complet si tu veux
 * }
 * 
 * @GetMapping("/details/{email}")
 * public ResponseEntity<Doctor> getDoctorDetails(@PathVariable String email) {
 * return ResponseEntity.ok(doctorService.getDoctorDetails(email));
 * }
 * 
 * @GetMapping("/top10")
 * public ResponseEntity<List<Map<String, Object>>> getTop10Doctors() {
 * return ResponseEntity.ok(doctorService.getTop10Doctors());
 * }
 * 
 * @GetMapping("/category/{specialty}")
 * public ResponseEntity<List<Map<String, Object>>>
 * getDoctorsByCategory(@PathVariable String specialty) {
 * return ResponseEntity.ok(doctorService.getDoctorsByCategory(specialty));
 * }
 * 
 * @GetMapping("/all")
 * public ResponseEntity<List<Doctor>> getAllDoctors() {
 * return ResponseEntity.ok(doctorService.getAllDoctors());
 * }
 * 
 * }
 */