package com.damageddream.MedicalClinicProxy.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class AppointmentDTO {
    private final Long id;
    private final PatientDTO patient;
    private final DoctorDTO doctor;
    private final LocalDateTime appointmentStart;
    private final LocalDateTime appointmentEnd;
}
