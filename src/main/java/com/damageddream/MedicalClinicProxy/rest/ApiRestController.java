package com.damageddream.MedicalClinicProxy.rest;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.service.ApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class ApiRestController {
    private final ApiService apiService;

    @GetMapping("/patients")
    public List<PatientDTO> findAll(@RequestParam(value = "appointmentDate") Optional<String> appointmentDate) {
        log.info("Get request on /api/patients, to fetch all patients with appointmentDate: {}",
                appointmentDate.orElse("not specified"));
        return apiService.findAllPatients(appointmentDate);
    }

    @GetMapping("/patients/{id}")
    public PatientDTO getPatient(@PathVariable Long id) {
        log.info("Get request on /api/patients/" + id + " to get patient with id");
        return apiService.getPatient(id);
    }

    @PostMapping("/patients")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientDTO addPatient(@RequestBody NewPatientDTO patient) {
        log.info("Post request on /api/patients to create new patient with body: {}", patient.toString());
        return apiService.addPatient(patient);
    }

    @GetMapping("/patients/{id}/appointments")
    public List<AppointmentDTO> getPatientsAppointments(@PathVariable Long id) {
        log.info("Get request on /api/patients/" + id + "/appointments");
        return apiService.getPatientsAppointments(id);
    }

    @PostMapping("/appointments")
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDTO addAppointment(@RequestBody AppointmentDTO appointment,
                                         @RequestParam("doctorId") Long doctorId) {
        log.info("Post request on /api/appointments to create new appointment with body: {} and doctorId",
                appointment.toString(), doctorId);
        return apiService.addAppointment(appointment, doctorId);
    }

    @PatchMapping("/appointments")
    public AppointmentDTO makeAnAppointment(@RequestBody GetIdCommand appointmentId,
                                            @RequestParam("patientId") Long patientId) {
        log.info("Patch request on api/appointments to make an appointment with body: {} and patientId: {}",
                appointmentId.toString(), patientId);
        return apiService.makeAnAppointment(appointmentId, patientId);
    }
}
