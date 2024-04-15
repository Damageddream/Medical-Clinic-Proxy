package com.damageddream.MedicalClinicProxy.repository;

import com.damageddream.MedicalClinicProxy.entity.PatientDTOCache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientDTOCacheRepository extends MongoRepository<PatientDTOCache, String> {
}
