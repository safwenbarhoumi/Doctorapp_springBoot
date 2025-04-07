package tn.esprit.tp1.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "appointments")
public class Appointment {

    @Id
    private String id;

    private String doctorId;
    private String patientId;
    private String date;
    private String time;

    // private LocalDateTime createdAt;

    public Appointment() {
        // Default constructor
    }

    public Appointment(String id, String doctorId, String patientId, String date, String time) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.date = date;
        this.time = time;
        // this.createdAt = LocalDateTime.now();
    }

    /*
     * public LocalDateTime getCreatedAt() {
     * return createdAt;
     * }
     * 
     * public void setCreatedAt(LocalDateTime createdAt) {
     * this.createdAt = createdAt;
     * }
     */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
