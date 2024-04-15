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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private static final String CACHE_ID = "allPatients";
    private final MedicalClinicClient medicalClinicClient;
    private final PatientDTOCacheRepository patientDTOCacheRepository;
    private final PatientDTOListCacheRepo patientDTOListCacheRepo;

    @Override
    public List<PatientDTO> findAllPatients(Optional<String> appointmentDate) {
        Optional<PatientDTOListCache> cachedPatients = patientDTOListCacheRepo
                .findById(CACHE_ID);
        if (cachedPatients.isPresent()) {
            return cachedPatients.get().getPatientsDTO();
        }
        List<PatientDTO> patients = appointmentDate.isPresent() ?
                medicalClinicClient.getPatientByAppointmentDate(appointmentDate.get()) :
                medicalClinicClient.getPatients();
        patientDTOListCacheRepo.insert(PatientDTOListCache.builder()
                .id(CACHE_ID)
                .patientsDTO(patients)
                .build());
        return patients;
    }

    @Override
    public PatientDTO getPatient(Long id) {
        String cachedId = id.toString();
        Optional<PatientDTOCache> cachedResponse = patientDTOCacheRepository
                .findById(cachedId);
        if (cachedResponse.isPresent()) {
            return cachedResponse.get().getPatientDTO();
        }
        PatientDTO patient = medicalClinicClient.getPatient(id);
        patientDTOCacheRepository.insert(PatientDTOCache.builder()
                .id(cachedId)
                .patientDTO(patient)
                .build());
        return patient;
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
