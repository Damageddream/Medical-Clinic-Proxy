package com.damageddream.MedicalClinicProxy.rest;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.remote.MedicalClinicClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiRestController {
    @Autowired
    private final MedicalClinicClient medicalClinicClient;

    @GetMapping("/patients")
    public List<PatientDTO> findAll() {
        return medicalClinicClient.getPatients();
    }

    @GetMapping("/patients/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        return medicalClinicClient.getPatient(id);
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@RequestBody NewPatientDTO patient) {
        return medicalClinicClient.addPatient(patient);
    }

    @GetMapping("/patients/{id}/appointments ")
    public List<AppointmentDTO> getPatientsAppointments(@PathVariable Long id) {
        return medicalClinicClient.getPatientsAppointments(id);
    }

    @PostMapping("/appointments")
    public AppointmentDTO addAppointment(@RequestBody AppointmentDTO appointment,
                                         @RequestParam("doctorId") Long doctorId) {
        return medicalClinicClient.addAppointment(appointment, doctorId);
    }

    @PatchMapping("/appointments")
    public AppointmentDTO makeAnAppointment(@RequestBody GetIdCommand appointmentId,
                                            @RequestParam("patientId") Long patientId){
        return medicalClinicClient.makeAnAppointment(appointmentId, patientId);
    }
}
