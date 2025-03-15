package tn.esprit.tp1.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Document(collection = "users") // MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id; // MongoDB unique ID
    private String name;
    private int age;
}