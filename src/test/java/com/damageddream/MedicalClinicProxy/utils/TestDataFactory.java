package com.damageddream.MedicalClinicProxy.utils;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.DoctorDTO;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestDataFactory {

    public static PatientDTO createPatientDTO(String email, String firstName) {
        return PatientDTO.builder()
                .email(email)
                .firstName(firstName)
                .lastName("GrabDTO")
                .phoneNumber("https://github.com/Damageddream/VeterinaryApp.git")
                .birthday(LocalDate.of(1902, 02, 02))
                .build();
    }
    public static NewPatientDTO createNewPatientDTO(String email, String firstName, String idCardNo) {
        return NewPatientDTO.builder()
                .password("passNewDto")
                .idCardNo(idCardNo)
                .email(email)
                .firstName(firstName)
                .lastName("GrabNewDto")
                .phoneNumber("333333333")
                .birthday(LocalDate.of(1903, 03, 03))
                .build();
    }
    public static DoctorDTO createDoctorDTO(String email, String firstName) {
        return DoctorDTO.builder()
                .email(email)
                .firstName(firstName)
                .lastName("doctorDTO")
                .specialization("surgeonDTO")
                .build();
    }
    public static AppointmentDTO createAppointmentDTO(LocalDateTime start, LocalDateTime end) {
        return AppointmentDTO.builder()
                .id(1L)
                .appointmentEnd(end)
                .appointmentStart(start)
                .patient(TestDataFactory
                        .createPatientDTO("mar@mail.com", "Marcin"))
                .doctor(TestDataFactory
                        .createDoctorDTO("doc@mail.com", "Doc"))
                .build();
    }
}
