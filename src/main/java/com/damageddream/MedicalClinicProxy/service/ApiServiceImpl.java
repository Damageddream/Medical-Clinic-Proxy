package com.damageddream.MedicalClinicProxy.service;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.remote.MedicalClinicClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {

    private final MedicalClinicClient medicalClinicClient;

    @Override
    public List<PatientDTO> findAllPatients(Optional<String> appointmentDate) {
        if (appointmentDate.isPresent()) {
            return medicalClinicClient.getPatientByAppointmentDate(appointmentDate.get());
        }
        return medicalClinicClient.getPatients();
    }

    @Override
    public PatientDTO getPatient(Long id) {
        return medicalClinicClient.getPatient(id);
    }

    @Override
    public PatientDTO addPatient(NewPatientDTO patient) {
        return medicalClinicClient.addPatient(patient);
    }

    @Override
    public List<AppointmentDTO> getPatientsAppointments(Long id) {
        return medicalClinicClient.getPatientsAppointments(id);
    }

    @Override
    public AppointmentDTO addAppointment(AppointmentDTO appointment, Long doctorId) {
        return medicalClinicClient.addAppointment(appointment, doctorId);
    }

    @Override
    public AppointmentDTO makeAnAppointment(GetIdCommand appointmentId, Long patientId) {
        return medicalClinicClient.makeAnAppointment(appointmentId, patientId);
    }
}
