package tn.esprit.tp1.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.AppointmentHistory;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.entities.Patient;
import tn.esprit.tp1.repositories.AppointmentHistoryRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.repositories.PatientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentHistoryService {

    private final AppointmentHistoryRepository appointmentHistoryRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public List<String> getHistoryByDoctorAndPatient(String doctorId, String patientId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (doctor == null || patient == null) {
            return List.of("Doctor or Patient not found");
        }

        String doctorFullName = "Dr " + doctor.getFirstName() + " " + doctor.getLastName();
        String patientFullName = patient.getFirstName() + " " + patient.getLastName();

        return appointmentHistoryRepository.findAll().stream()
                .filter(h -> h.getMessage().contains(doctorFullName) && h.getMessage().contains(patientFullName))
                .map(AppointmentHistory::getMessage)
                .collect(Collectors.toList());
    }
}
