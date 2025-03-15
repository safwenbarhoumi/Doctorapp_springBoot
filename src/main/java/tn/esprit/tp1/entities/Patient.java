package tn.esprit.tp1.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Role role;
}
