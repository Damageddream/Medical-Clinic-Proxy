package com.damageddream.MedicalClinicProxy.service;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.entity.PatientDTOCache;
import com.damageddream.MedicalClinicProxy.entity.PatientDTOListCache;
import com.damageddream.MedicalClinicProxy.remote.MedicalClinicClient;
import com.damageddream.MedicalClinicProxy.repository.PatientDTOCacheRepository;
import com.damageddream.MedicalClinicProxy.repository.PatientDTOListCacheRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private static final String CACHE_ID = "allPatients";
    private final MedicalClinicClient medicalClinicClient;
    private final PatientDTOCacheRepository patientDTOCacheRepository;
    private final PatientDTOListCacheRepo patientDTOListCacheRepo;
    // poprawić logi na taki jak ten na górze
    @Override
    public List<PatientDTO> findAllPatients(Optional<String> appointmentDate) {
        Optional<PatientDTOListCache> cachedPatients = patientDTOListCacheRepo
                .findById(CACHE_ID);
        if (cachedPatients.isPresent()) {
            log.info("Process of finding patients completed, patients: {}",
                    cachedPatients.get().getPatientsDTO().toString());
            return cachedPatients.get().getPatientsDTO();
        }
        List<PatientDTO> patients = appointmentDate.isPresent() ?
                medicalClinicClient.getPatientByAppointmentDate(appointmentDate.get()) :
                medicalClinicClient.getPatients();
        patientDTOListCacheRepo.insert(PatientDTOListCache.builder()
                .id(CACHE_ID)
                .patientsDTO(patients)
                .build());
        log.info("Return response status: 200, with patients list:{}", patients.toString());
        return patients;
    }

    @Override
    public PatientDTO getPatient(Long id) {
        String cachedId = id.toString();
        Optional<PatientDTOCache> cachedResponse = patientDTOCacheRepository
                .findById(cachedId);
        if (cachedResponse.isPresent()) {
            log.info("Return cached response with status: 200, patient:{}",
                    cachedResponse.get().getPatientDTO().toString());
            return cachedResponse.get().getPatientDTO();
        }
        PatientDTO patient = medicalClinicClient.getPatient(id);
        patientDTOCacheRepository.insert(PatientDTOCache.builder()
                .id(cachedId)
                .patientDTO(patient)
                .build());
        log.info("Return response with status: 200, patient:{}", patient.toString());
        return patient;
    }

    @Override
    public PatientDTO addPatient(NewPatientDTO patient) {
        log.info("Return response with status: 201, created patient, patient:{}", patient.toString());
        return medicalClinicClient.addPatient(patient);
    }

    @Override
    public List<AppointmentDTO> getPatientsAppointments(Long id) {
        List<AppointmentDTO> appointments = medicalClinicClient.getPatientsAppointments(id);
        log.info("Return response with status: 200, patients: {}", appointments.toString());
        return appointments;
    }

    @Override
    public AppointmentDTO addAppointment(AppointmentDTO appointment, Long doctorId) {
        AppointmentDTO appointmentDTO = medicalClinicClient.addAppointment(appointment, doctorId);
        log.info("Return response with status: 201, created appointment, appointment: {}", appointmentDTO.toString());
        return appointmentDTO;
    }

    @Override
    public AppointmentDTO makeAnAppointment(GetIdCommand appointmentId, Long patientId) {
        AppointmentDTO appointmentDTO = medicalClinicClient.makeAnAppointment(appointmentId, patientId);
        log.info("Return response with status: 200,appointment was reserved, appointment: {}", appointmentDTO.toString());
        return appointmentDTO;
    }
}
