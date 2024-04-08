package com.damageddream.MedicalClinicProxy.service;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.remote.MedicalClinicClient;
import com.damageddream.MedicalClinicProxy.utils.TestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ApiServiceTest {
    private MedicalClinicClient medicalClinicClient;
    private ApiService apiService;

    @BeforeEach
    void setup() {
        this.medicalClinicClient = Mockito.mock(MedicalClinicClient.class);
        this.apiService = new ApiServiceImpl(medicalClinicClient);
    }

    @Test
    void findAllPatients_PatientsExists_PatientDTOListReturned() {
        //given
        PatientDTO patient1 = TestDataFactory.createPatientDTO("mar@email.com", "Mar");
        PatientDTO patient2 = TestDataFactory.createPatientDTO("lesz@email.com", "Leszek");

        List<PatientDTO> patients =List.of(patient1,patient2);

        when(medicalClinicClient.getPatientByAppointmentDate(any())).thenReturn(patients);

        //when
        var result = apiService.findAllPatients(Optional.of("2024-02-02"));

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("mar@email.com", result.get(0).getEmail());
        assertEquals("lesz@email.com", result.get(1).getEmail());
    }

    @Test
    void getPatient_PatientExists_PatientDTOReturned() {
        //given
        PatientDTO patient = TestDataFactory.createPatientDTO("mar@email.com", "Mar");

        when(medicalClinicClient.getPatient(any())).thenReturn(patient);

        //when
        var result = apiService.getPatient(1L);

        //then
        assertEquals("mar@email.com", result.getEmail());
        assertEquals("Mar", result.getFirstName());
        assertEquals("GrabDTO", result.getLastName());
        assertEquals("222222", result.getPhoneNumber());
        assertEquals(LocalDate.of(1902,02,02), result.getBirthday());
    }

    @Test
    void addPatient_patientDataCorrect_NewPatientDTOReturned() {
        //given
        NewPatientDTO newPatient = TestDataFactory
                .createNewPatientDTO("mar@email.com", "Mar", "1");
        PatientDTO patient = TestDataFactory.createPatientDTO("mar@email.com", "Mar");

        when(medicalClinicClient.addPatient(any())).thenReturn(patient);

        //when
        var result = apiService.addPatient(newPatient);

        //then
        assertEquals("mar@email.com", result.getEmail());
        assertEquals("Mar", result.getFirstName());
        assertEquals("GrabDTO", result.getLastName());
        assertEquals("222222", result.getPhoneNumber());
        assertEquals(LocalDate.of(1902,02,02), result.getBirthday());
    }

    @Test
    void getPatientsAppointments_atLeastOneAppointment_appointmentDTOListReturned() {
        //given
        AppointmentDTO appointmentDTO1 = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 15, 30, 10),
                LocalDateTime.of(2024, 3, 22, 16, 30, 10));
        AppointmentDTO appointmentDTO2 = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 13, 30, 10),
                LocalDateTime.of(2024, 3, 22, 14, 30, 10));
        List<AppointmentDTO> appointments = List.of(appointmentDTO1, appointmentDTO2);

        when(medicalClinicClient.getPatientsAppointments(any())).thenReturn(appointments);

        //when
        var result = apiService.getPatientsAppointments(1L);

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(LocalDateTime.of(2024, 3, 22, 15, 30, 10)
                , result.get(0).getAppointmentStart());
        assertEquals(LocalDateTime.of(2024, 3, 22, 16, 30, 10)
                , result.get(0).getAppointmentEnd());
        assertEquals(LocalDateTime.of(2024, 3, 22, 13, 30, 10),
                result.get(1).getAppointmentStart());
        assertEquals(LocalDateTime.of(2024, 3, 22, 14, 30, 10),
                result.get(1).getAppointmentEnd());
    }

    @Test
    void addAppointment_appointmentDataCorrect_appointmentDTOReturned(){
        //given
        AppointmentDTO appointmentDTO = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 15, 30, 10),
                LocalDateTime.of(2024, 3, 22, 16, 30, 10));

        when(medicalClinicClient.addAppointment(any(),any())).thenReturn(appointmentDTO);

        //when
        var result = apiService.addAppointment(appointmentDTO, 1L);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(LocalDateTime.of(2024, 3, 22, 15, 30, 10)
                , result.getAppointmentStart());
        assertEquals(LocalDateTime.of(2024, 3, 22, 16, 30, 10)
                , result.getAppointmentEnd());
        assertEquals("doc@mail.com", result.getDoctor().getEmail());
        assertEquals("Doc", result.getDoctor().getFirstName());
    }

    @Test
    void makeAnAppointment_appointmentExists_returnAppointmentDTO() {
        //given
        GetIdCommand getIdCommand = new GetIdCommand(1L);
        AppointmentDTO appointmentDTO = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 15, 30, 10),
                LocalDateTime.of(2024, 3, 22, 16, 30, 10));

       when(medicalClinicClient.makeAnAppointment(any(),any())).thenReturn(appointmentDTO);

       //when
        var result = apiService.makeAnAppointment(getIdCommand, 1L);

        //then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(LocalDateTime.of(2024, 3, 22, 15, 30, 10)
                , result.getAppointmentStart());
        assertEquals(LocalDateTime.of(2024, 3, 22, 16, 30, 10)
                , result.getAppointmentEnd());
        assertEquals("mar@mail.com", result.getPatient().getEmail());
        assertEquals("Marcin", result.getPatient().getFirstName());
        assertEquals("doc@mail.com", result.getDoctor().getEmail());
        assertEquals("Doc", result.getDoctor().getFirstName());
    }
}
