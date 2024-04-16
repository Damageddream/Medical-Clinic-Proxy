package com.damageddream.MedicalClinicProxy.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@RequiredArgsConstructor
@Builder
@EqualsAndHashCode
public class NewPatientDTO {
    private final String email;
    private final String password;
    private final String idCardNo;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final LocalDate birthday;
}

