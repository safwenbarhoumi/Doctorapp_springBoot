package tn.esprit.tp1.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

@Document // (collection = "patients")
public class Doctor {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String role;
    private String photo;
    private String specialty;
    private String location;
    private String description;
    private int numberExperience;
    private int numberPatients;
    private double numberRating;
    private boolean profileCompleted;

    // Use a Map to store available times, with each day mapped to a list of
    // available times
    private Map<String, List<String>> availableTimes;

    public void addAvailableTimes(Map<String, List<String>> newTimes) {
        if (this.availableTimes == null) {
            this.availableTimes = new HashMap<>();
        }

        for (Map.Entry<String, List<String>> entry : newTimes.entrySet()) {
            String date = entry.getKey();
            List<String> times = entry.getValue();

            this.availableTimes.merge(date, times, (existing, incoming) -> {
                Set<String> merged = new LinkedHashSet<>(existing);
                merged.addAll(incoming);
                return new ArrayList<>(merged);
            });
        }
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberExperience() {
        return numberExperience;
    }

    public void setNumberExperience(int numberExperience) {
        this.numberExperience = numberExperience;
    }

    public int getNumberPatients() {
        return numberPatients;
    }

    public void setNumberPatients(int numberPatients) {
        this.numberPatients = numberPatients;
    }

    public double getNumberRating() {
        return numberRating;
    }

    public void setNumberRating(double numberRating) {
        this.numberRating = numberRating;
    }

    public boolean isProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public Map<String, List<String>> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(Map<String, List<String>> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
