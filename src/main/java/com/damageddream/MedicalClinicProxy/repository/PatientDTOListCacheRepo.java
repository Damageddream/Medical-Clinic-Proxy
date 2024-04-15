package com.damageddream.MedicalClinicProxy.repository;

import com.damageddream.MedicalClinicProxy.entity.PatientDTOListCache;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PatientDTOListCacheRepo extends MongoRepository<PatientDTOListCache, String> {
}
