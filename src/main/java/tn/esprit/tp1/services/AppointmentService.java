package tn.esprit.tp1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tp1.entities.Appointment;
import tn.esprit.tp1.entities.AppointmentHistory;
import tn.esprit.tp1.entities.Doctor;
import tn.esprit.tp1.entities.DoctorNotification;
import tn.esprit.tp1.entities.Patient;
import tn.esprit.tp1.repositories.AppointmentHistoryRepository;
import tn.esprit.tp1.repositories.AppointmentRepository;
import tn.esprit.tp1.repositories.DoctorNotificationRepository;
import tn.esprit.tp1.repositories.DoctorRepository;
import tn.esprit.tp1.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DoctorNotificationRepository doctorNotificationRepository;

    @Autowired
    private AppointmentHistoryRepository appointmentHistoryRepository;

    @Autowired
    private PatientRepository patientRepository;

    public String addAvailableTimes(String doctorId, Map<String, List<String>> newTimes) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (doctorOptional.isEmpty()) {
            return "Doctor not found!";
        }

        Doctor doctor = doctorOptional.get();
        doctor.addAvailableTimes(newTimes);
        doctorRepository.save(doctor);

        return "Available times added successfully";
    }

    public Appointment makeAppointment(String doctorId, String patientId, String date, String time) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
        Optional<Patient> patientOptional = patientRepository.findById(patientId); // Make sure this exists

        if (doctorOptional.isPresent() && patientOptional.isPresent()) {
            Doctor doctor = doctorOptional.get();
            Patient patient = patientOptional.get();
            Map<String, List<String>> availableTimes = doctor.getAvailableTimes();

            if (availableTimes.containsKey(date) && availableTimes.get(date).contains(time)) {
                // Remove the time
                availableTimes.get(date).remove(time);

                // Remove the whole date if it's empty
                if (availableTimes.get(date).isEmpty()) {
                    availableTimes.remove(date);
                }

                // Update doctor
                doctor.setAvailableTimes(availableTimes);
                doctorRepository.save(doctor);

                // Save appointment
                Appointment appointment = new Appointment(null, doctorId, patientId, date, time);
                appointmentRepository.save(appointment);

                // Notification for doctor
                DoctorNotification notification = new DoctorNotification(null, doctorId,
                        "New appointment scheduled at " + time + " on " + date);
                doctorNotificationRepository.save(notification);

                // Save history message
                String historyMessage = patient.getFirstName() + " " + patient.getLastName() +
                        " has made an appointment with Dr " +
                        doctor.getFirstName() + " " + doctor.getLastName() +
                        " on " + date + " at " + time;
                AppointmentHistory history = new AppointmentHistory(null, historyMessage);
                appointmentHistoryRepository.save(history);

                return appointment;
            }
        }
        return null;
    }

    public Map<String, List<String>> getAvailableTimesByDoctorId(String doctorId) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        if (doctorOptional.isEmpty()) {
            throw new NoSuchElementException("Doctor not found with id: " + doctorId);
        }

        Doctor doctor = doctorOptional.get();
        return doctor.getAvailableTimes() != null ? doctor.getAvailableTimes() : new HashMap<>();
    }

    public List<Appointment> getAppointmentByDoctorIdAndPatientId(String doctorId, String patientId) {
        System.out.println("DoctorId: " + doctorId);
        System.out.println("PatientId: " + patientId);
        List<Appointment> results = appointmentRepository.findByDoctorIdAndPatientId("66108b94e558084aa6d6d292",
                "66108e15e558084aa6d6d29b");
        System.out.println("Found: " + results.size());
        return appointmentRepository.findByDoctorIdAndPatientId(doctorId, patientId);
    }

    /*
     * public List<Appointment> getAppointmentByDoctorIdAndPatientId(String
     * doctorId, String patientId) {
     * List<Appointment> all = appointmentRepository.findAll();
     * return all.stream()
     * .filter(a -> a.getDoctorId().equals(doctorId) &&
     * a.getPatientId().equals(patientId))
     * .collect(Collectors.toList());
     * }
     */

    public List<String> getAppointmentsByPatientId(String patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);
        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        if (appointments.isEmpty() || patientOptional.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> result = new ArrayList<>();

        for (Appointment appointment : appointments) {
            Optional<Doctor> doctorOptional = doctorRepository.findById(appointment.getDoctorId());

            if (doctorOptional.isPresent()) {
                Doctor doctor = doctorOptional.get();
                String doctorFullName = doctor.getFirstName() + " " + doctor.getLastName();
                // String createdAt = appointment.getCreatedAt().toString(); // Make sure
                // Appointment has this field
                String message = String.format(
                        "You made an appointment with Dr %s on %s at %s ",
                        doctorFullName,
                        appointment.getDate(),
                        appointment.getTime()
                // createdAt
                );
                result.add(message);
            }
        }

        return result;
    }

}
