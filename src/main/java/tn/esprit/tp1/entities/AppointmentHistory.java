package tn.esprit.tp1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appointmentHistory")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentHistory {
    @Id
    private String id;
    private String message;
}
