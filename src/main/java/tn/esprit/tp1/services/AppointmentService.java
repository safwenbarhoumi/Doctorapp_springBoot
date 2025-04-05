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

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    /*
     * public Appointment makeAppointment(String doctorId, String patientId, String
     * date, String time) {
     * Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
     * if (doctorOptional.isPresent()) {
     * Doctor doctor = doctorOptional.get();
     * Map<String, List<String>> availableTimes = doctor.getAvailableTimes();
     * 
     * if (availableTimes.containsKey(date) &&
     * availableTimes.get(date).contains(time)) {
     * // Suppression du créneau de la liste
     * availableTimes.get(date).remove(time);
     * 
     * // Si aucun créneau ne reste pour ce jour, supprimer l'entrée
     * if (availableTimes.get(date).isEmpty()) {
     * availableTimes.remove(date);
     * }
     * 
     * doctor.setAvailableTimes(availableTimes);
     * doctorRepository.save(doctor);
     * 
     * // Création du rendez-vous
     * Appointment appointment = new Appointment(null, doctorId, patientId, date,
     * time);
     * appointmentRepository.save(appointment);
     * 
     * // Envoi de la notification au médecin
     * DoctorNotification notification = new DoctorNotification(null, doctorId,
     * "New appointment scheduled at " + time + " on " + date);
     * doctorNotificationRepository.save(notification);
     * 
     * return appointment;
     * }
     * }
     * return null; // Retourner null si le créneau n'existe pas
     * }
     */

    /*
     * public Appointment makeAppointment(String doctorId, String patientId, String
     * date, String time) {
     * Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
     * Optional<Patient> patientOptional = patientRepository.findById(patientId);
     * 
     * if (doctorOptional.isPresent() && patientOptional.isPresent()) {
     * Doctor doctor = doctorOptional.get();
     * Patient patient = patientOptional.get();
     * Map<String, List<String>> availableTimes = doctor.getAvailableTimes();
     * 
     * if (availableTimes.containsKey(date) &&
     * availableTimes.get(date).contains(time)) {
     * // Remove time slot
     * availableTimes.get(date).remove(time);
     * 
     * // Remove the date if it's empty
     * if (availableTimes.get(date).isEmpty()) {
     * availableTimes.remove(date);
     * }
     * 
     * doctor.setAvailableTimes(availableTimes);
     * doctorRepository.save(doctor);
     * 
     * // Save appointment
     * Appointment appointment = new Appointment(null, doctorId, patientId, date,
     * time);
     * appointmentRepository.save(appointment);
     * 
     * // Send doctor notification
     * String notifMsg = "New appointment scheduled at " + time + " on " + date;
     * DoctorNotification notification = new DoctorNotification(null, doctorId,
     * notifMsg);
     * doctorNotificationRepository.save(notification);
     * 
     * // Save appointment history
     * String historyMessage = patient.getFirstName() + " " + patient.getLastName()
     * + " has made an appointment with Dr " + doctor.getFirstName() + " " +
     * doctor.getLastName()
     * + " on " + date + " at " + time;
     * 
     * AppointmentHistory history = new AppointmentHistory(null, historyMessage);
     * appointmentHistoryRepository.save(history);
     * 
     * return appointment;
     * }
     * }
     * return null;
     * }
     */

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

}
