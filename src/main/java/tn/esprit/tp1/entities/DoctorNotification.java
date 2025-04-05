package tn.esprit.tp1.entities;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

@Document(collection = "doctor_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorNotification {
    @Id
    private String id;
    private String doctorId;
    private String message;
}