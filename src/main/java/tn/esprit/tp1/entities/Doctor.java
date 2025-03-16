package tn.esprit.tp1.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private String photo;
    private String specialty;
    private String location;
    private String description;
    private int numberExperience;
    private int numberPatients;
    private float numberRating;
}

