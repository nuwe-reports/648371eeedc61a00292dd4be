
package com.example.demo;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDoctor() throws Exception {
        Doctor doctor = new Doctor ("Sílvia", "Martínez", 33, "s.martinez@hospital.com");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doctor)))
                        .andExpect(status().isCreated());
    }

    @Test
    void shouldGetNoDoctors() throws Exception{
        List<Doctor> doctors = new ArrayList<>();

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoDoctors() throws Exception{
        Doctor doctor1 = new Doctor ("Sílvia", "Martínez", 33, "s.martinez@hospital.com");
        Doctor doctor2 = new Doctor ("Marc", "Àvila", 40, "marc_avila@hospital.com");

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor1);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetDoctorById() throws Exception{
        Doctor doctor = new Doctor ("Sílvia", "Martínez", 33, "s.martinez@hospital.com");
        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception{
        long id = 4;

        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteDoctorById() throws Exception{
        Doctor doctor = new Doctor ("Sílvia", "Martínez", 33, "s.martinez@hospital.com");
        doctor.setId(1);

        Optional<Doctor> opt = Optional.of(doctor);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteDoctor() throws Exception{
        long id = 6;

        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception{
        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePatient() throws Exception {
        Patient patient = new Patient ("Carla", "Blay", 28, "carla_1995@gmail.com");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                        .andExpect(status().isCreated());
    }

    @Test
    void shouldGetNoPatients() throws Exception{
        List<Patient> patients = new ArrayList<>();

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoPatients() throws Exception{
        Patient patient1 = new Patient ("Carla", "Blay", 28, "carla_1995@gmail.com");
        Patient patient2 = new Patient ("Martí", "Navarro", 56, "mnavarro@hotmail.com");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient1);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetPatientById() throws Exception{
        Patient patient = new Patient ("Carla", "Blay", 28, "carla_1995@gmail.com");
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception{
        long id = 4;

        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeletePatientById() throws Exception{
        Patient patient = new Patient ("Carla", "Blay", 28, "carla_1995@gmail.com");
        patient.setId(1);

        Optional<Patient> opt = Optional.of(patient);

        assertThat(opt).isPresent();
        assertThat(opt.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(opt);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatient() throws Exception{
        long id = 6;

        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception{
        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateRoom() throws Exception {
        Room room = new Room ("Dermatology");

        mockMvc.perform(post("/api/room").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room)))
                        .andExpect(status().isCreated());
    }

    @Test
    void shouldGetNoRooms() throws Exception{
        List<Room> rooms = new ArrayList<>();

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoRooms() throws Exception{
        Room room1 = new Room ("Dermatology");
        Room room2 = new Room("Oncology");

        List<Room> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);

        when(roomRepository.findAll()).thenReturn(rooms);
        mockMvc.perform(get("/api/rooms"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRoomByRoomName() throws Exception{
        Room room = new Room ("Dermatology");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Dermatology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(get("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyRoomByRoomName() throws Exception{
        String roomName = "Oncology";

        mockMvc.perform(get("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteRoomById() throws Exception{
        Room room = new Room ("Dermatology");

        Optional<Room> opt = Optional.of(room);

        assertThat(opt).isPresent();
        assertThat(opt.get().getRoomName()).isEqualTo(room.getRoomName());
        assertThat(room.getRoomName()).isEqualTo("Dermatology");

        when(roomRepository.findByRoomName(room.getRoomName())).thenReturn(opt);
        mockMvc.perform(delete("/api/rooms/" + room.getRoomName()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteRoom() throws Exception{
        String roomName = "Oncology";

        mockMvc.perform(delete("/api/rooms/" + roomName))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllRooms() throws Exception{
        mockMvc.perform(delete("/api/rooms"))
                .andExpect(status().isOk());
    }


}
