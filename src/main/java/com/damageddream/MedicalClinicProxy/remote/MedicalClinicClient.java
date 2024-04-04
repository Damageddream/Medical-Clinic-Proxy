package com.damageddream.MedicalClinicProxy.remote;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import feign.okhttp.OkHttpClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "MedicalClinicClient",
        url = "http://localhost:8080",
        configuration = OkHttpClient.class
)
public interface MedicalClinicClient {
    @GetMapping(value = "patients")
    List<PatientDTO> getPatients();

    @PostMapping(value = "patients")
    PatientDTO addPatient(@RequestBody NewPatientDTO patient);

    @GetMapping(value = "patients/id/{id}")
    PatientDTO getPatient(@PathVariable("id") Long id);

    @GetMapping(value = "appointments/patient/{id}")
    List<AppointmentDTO> getPatientsAppointments(@PathVariable("id") Long id);

    @PostMapping(value = "appointments")
    AppointmentDTO addAppointment(@RequestBody AppointmentDTO appointment,
                                  @RequestParam("doctorId") Long doctorId);
    @PatchMapping(value = "appointments")
    AppointmentDTO makeAnAppointment(@RequestBody GetIdCommand appointmentId,
                                     @RequestParam("patientId") Long patientId);

}
