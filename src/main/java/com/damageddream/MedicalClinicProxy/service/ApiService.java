package com.damageddream.MedicalClinicProxy.service;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;

import java.util.List;
import java.util.Optional;

public interface ApiService {
    List<PatientDTO> findAllPatients(Optional<String> appointmentDate);
    PatientDTO getPatient(Long id);
    PatientDTO addPatient(NewPatientDTO patient);
    List<AppointmentDTO> getPatientsAppointments(Long id);
    AppointmentDTO addAppointment(AppointmentDTO appointment, Long doctorId);
    AppointmentDTO makeAnAppointment(GetIdCommand appointmentId, Long patientId);
}
