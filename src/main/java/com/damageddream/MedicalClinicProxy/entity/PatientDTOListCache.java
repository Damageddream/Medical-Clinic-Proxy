package com.damageddream.MedicalClinicProxy.entity;

import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "patients_dto_list_cache")
public class PatientDTOListCache {
    @Id
    private String id;
    private List<PatientDTO> patientsDTO;
}
