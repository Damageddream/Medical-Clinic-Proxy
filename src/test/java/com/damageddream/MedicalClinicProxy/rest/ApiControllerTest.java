package com.damageddream.MedicalClinicProxy.rest;

import com.damageddream.MedicalClinicProxy.dto.AppointmentDTO;
import com.damageddream.MedicalClinicProxy.dto.GetIdCommand;
import com.damageddream.MedicalClinicProxy.dto.NewPatientDTO;
import com.damageddream.MedicalClinicProxy.dto.PatientDTO;
import com.damageddream.MedicalClinicProxy.service.ApiServiceImpl;
import com.damageddream.MedicalClinicProxy.utils.TestDataFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApiServiceImpl apiService;

    @Test
    void getPatients_patientsExists_returnPatientDTOList() throws Exception {
        //given
        PatientDTO patient1 = TestDataFactory.createPatientDTO("mar@email.com", "Mar");
        PatientDTO patient2 = TestDataFactory.createPatientDTO("lesz@email.com", "Leszek");

        List<PatientDTO> patients = List.of(patient1,patient2);

        when(apiService.findAllPatients(any())).thenReturn(patients);

        //whe //then
        mockMvc.perform(get("/api/patients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Mar"))
                .andExpect(jsonPath("$[0].email").value("mar@email.com"))
                .andExpect(jsonPath("$[1].firstName").value("Leszek"))
                .andExpect(jsonPath("$[1].email").value("lesz@email.com"));
    }

    @Test
    void getPatientById_PatientExists_returnPatientDTO() throws Exception {
        //given
        PatientDTO patient = TestDataFactory.createPatientDTO("mar@email.com", "Mar");

        when(apiService.getPatient(1L)).thenReturn(patient);

        //when then
        mockMvc.perform(get("/api/patients/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Mar"))
                .andExpect(jsonPath("$.lastName").value("GrabDTO"))
                .andExpect(jsonPath("$.phoneNumber").value("222222"))
                .andExpect(jsonPath("$.email").value("mar@email.com"))
                .andExpect(jsonPath("$.birthday").value("1902-02-02"));
    }

    @Test
    void postPatient_patientDataValid_returnPatientDTO() throws Exception{
        //given
        NewPatientDTO newPatientDTO = TestDataFactory
                .createNewPatientDTO("marNewDto@email.com", "MarNewDto", "654321");
        PatientDTO patientDTO = TestDataFactory.createPatientDTO("marDTO@email.com", "MarDTO");

        when(apiService.addPatient(any())).thenReturn(patientDTO);

        //when //then
        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPatientDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("MarDTO"))
                .andExpect(jsonPath("$.lastName").value("GrabDTO"))
                .andExpect(jsonPath("$.phoneNumber").value("222222"))
                .andExpect(jsonPath("$.email").value("marDTO@email.com"))
                .andExpect(jsonPath("$.birthday").value("1902-02-02"));
    }

    @Test
    void getPatientsAppointments_appointmentsExists_returnAppointmentDTOList() throws Exception {
        //given
        AppointmentDTO appointmentDTO1 = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 15, 30, 10),
                LocalDateTime.of(2024, 3, 22, 16, 30, 10));
        AppointmentDTO appointmentDTO2 = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 13, 30, 10),
                LocalDateTime.of(2024, 3, 22, 14, 30, 10));
        List<AppointmentDTO> appointments = List.of(appointmentDTO1, appointmentDTO2);

        when(apiService.getPatientsAppointments(any())).thenReturn(appointments);

        //when //then
        mockMvc.perform(get("/api/patients/{id}/appointments",1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].appointmentStart").value("2024-03-22T15:30:10"))
                .andExpect(jsonPath("$[0].appointmentEnd").value("2024-03-22T16:30:10"))
                .andExpect(jsonPath("$[1].appointmentStart").value("2024-03-22T13:30:10"))
                .andExpect(jsonPath("$[1].appointmentEnd").value("2024-03-22T14:30:10"));
    }

    @Test
    void addAppointment_validData_returnAppointmentDTO() throws Exception {
        //given
        AppointmentDTO appointmentDTO = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 15, 30, 10),
                LocalDateTime.of(2024, 3, 22, 16, 30, 10)
        );

        when(apiService.addAppointment(any(),any())).thenReturn(appointmentDTO);

        //when //then
        mockMvc.perform(post("/api/appointments")
                        .param("doctorId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.appointmentStart").value("2024-03-22T15:30:10"))
                .andExpect(jsonPath("$.appointmentEnd").value("2024-03-22T16:30:10"))
                .andExpect(jsonPath("$.doctor.email").value("doc@mail.com"))
                .andExpect(jsonPath("$.doctor.firstName").value("Doc"));
    }

    @Test
    void patchAppointment_appointmentExists_returnAppointmentDTO() throws Exception {
        //given
        GetIdCommand appointmentId = new GetIdCommand(2L);
        AppointmentDTO appointmentDTO = TestDataFactory.createAppointmentDTO(
                LocalDateTime.of(2024, 3, 22, 15, 30, 10),
                LocalDateTime.of(2024, 3, 22, 16, 30, 10));

        when(apiService.makeAnAppointment(any(), any())).thenReturn(appointmentDTO);

        //when //then
        mockMvc.perform(patch("/api/appointments")
                        .param("patientId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.appointmentStart")
                        .value("2024-03-22T15:30:10"))
                .andExpect(jsonPath("$.appointmentEnd").value("2024-03-22T16:30:10"))
                .andExpect(jsonPath("$.doctor.email").value("doc@mail.com"))
                .andExpect(jsonPath("$.doctor.firstName").value("Doc"))
                .andExpect(jsonPath("$.patient.email").value("mar@mail.com"));
    }
}
