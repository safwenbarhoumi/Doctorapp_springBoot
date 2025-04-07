package tn.esprit.tp1.controllers;

import lombok.RequiredArgsConstructor;
import tn.esprit.tp1.services.AppointmentHistoryService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointment-history")
@RequiredArgsConstructor
public class AppointmentHistoryController {

    private final AppointmentHistoryService appointmentHistoryService;

    @PostMapping("/get-by-doctor-patient")
    public ResponseEntity<List<String>> getHistoryByDoctorAndPatient(@RequestBody Map<String, String> body) {
        String doctorId = body.get("doctorId");
        String patientId = body.get("patientId");
        List<String> historyMessages = appointmentHistoryService.getHistoryByDoctorAndPatient(doctorId, patientId);
        return ResponseEntity.ok(historyMessages);
    }
}
