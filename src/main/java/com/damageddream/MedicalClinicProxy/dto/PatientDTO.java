package com.damageddream.MedicalClinicProxy.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PatientDTO {
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final LocalDate birthday;
}