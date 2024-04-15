package com.damageddream.MedicalClinicProxy.service;

import com.damageddream.MedicalClinicProxy.repository.PatientDTOCacheRepository;
import com.damageddream.MedicalClinicProxy.repository.PatientDTOListCacheRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClearCacheScheduler {

    private final PatientDTOCacheRepository patientDTOCacheRepository;
    private final PatientDTOListCacheRepo patientDTOListCacheRepo;
    @Scheduled(fixedDelay = 300000, initialDelay = 300000)
    public void scheduleClearCache() {
        patientDTOCacheRepository.deleteAll();
        patientDTOListCacheRepo.deleteAll();
    }
}
