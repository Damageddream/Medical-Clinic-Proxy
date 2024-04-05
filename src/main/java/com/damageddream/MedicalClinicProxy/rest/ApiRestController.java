package com.damageddream.MedicalClinicProxy.rest;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiRestController {
    private final ApiService apiService;

    @GetMapping("/patients")
    public List<PatientDTO> findAll(@RequestParam(value = "appointmentDate") Optional<String> appointmentDate) {
        return apiService.findAllPatients(appointmentDate);
    }

    @GetMapping("/patients/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        return apiService.getPatient(id);
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@RequestBody NewPatientDTO patient) {
        return apiService.addPatient(patient);
    }

    @GetMapping("/patients/{id}/appointments")
    public List<AppointmentDTO> getPatientsAppointments(@PathVariable Long id) {
        return apiService.getPatientsAppointments(id);
    }

    @PostMapping("/appointments")
    public AppointmentDTO addAppointment(@RequestBody AppointmentDTO appointment,
                                         @RequestParam("doctorId") Long doctorId) {
        return apiService.addAppointment(appointment, doctorId);
    }

    @PatchMapping("/appointments")
    public AppointmentDTO makeAnAppointment(@RequestBody GetIdCommand appointmentId,
                                            @RequestParam("patientId") Long patientId) {
        return apiService.makeAnAppointment(appointmentId, patientId);
    }
}
