package com.damageddream.MedicalClinicProxy.service;

import com.damageddream.MedicalClinicProxy.repository.PatientDTOCacheRepository;
import com.damageddream.MedicalClinicProxy.repository.PatientDTOListCacheRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClearCacheScheduler {

    private final PatientDTOCacheRepository patientDTOCacheRepository;
    private final PatientDTOListCacheRepo patientDTOListCacheRepo;
    @Scheduled(fixedDelay = 300000, initialDelay = 300000)
    public void scheduleClearCache() {
        log.info("Starting cache clearing process.");
        patientDTOCacheRepository.deleteAll();
        log.info("Cleared PatientDTOCache.");
        patientDTOListCacheRepo.deleteAll();
        log.info("Cleared PatientDTOListCache.");
        log.info("Cache clearing process completed.");
    }
}
