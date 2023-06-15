package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

	@Autowired
	private TestEntityManager entityManager;
	private Doctor d1;
	private Patient p1;
    private Room r1;
    private Appointment a1, a2, a3;


    @BeforeEach
    public void setup() {

        d1 = new Doctor("Josep", "Noguera", 35, "josep.nog@gmail.com");

        p1 = new Patient("Pol", "Alonso", 18, "polalonso@hotmail.com");

        r1 = new Room("Dermatology");

        LocalDateTime startsAt = LocalDateTime.parse("19:30 20/06/2023", formatter);
        LocalDateTime finishesAt = LocalDateTime.parse("20:30 20/06/2023", formatter);

        a1 = new Appointment(p1, d1, r1, startsAt, finishesAt);
    }

    @Test
    void testSaveDoctor() {
        Doctor savedDoctor = entityManager.persistAndFlush(d1);

        assertEquals(d1.getFirstName(), savedDoctor.getFirstName());
        assertEquals(d1.getLastName(), savedDoctor.getLastName());
        assertEquals(d1.getAge(), savedDoctor.getAge());
        assertEquals(d1.getEmail(), savedDoctor.getEmail());
    }

    @Test
    void testSetterDoctor() {
        d1.setId(1);

        assertEquals(1, d1.getId());
    }

    @Test
    void testSavePatient() {
        Patient savedPatient = entityManager.persistAndFlush(p1);

        assertEquals(p1.getFirstName(), savedPatient.getFirstName());
        assertEquals(p1.getLastName(), savedPatient.getLastName());
        assertEquals(p1.getAge(), savedPatient.getAge());
        assertEquals(p1.getEmail(), savedPatient.getEmail());
    }

    @Test
    void testSetterPatient() {
        p1.setId(1);

        assertEquals(1, p1.getId());
    }

    @Test
    void testSaveRoom() {
        Room savedRoom = entityManager.persistAndFlush(r1);

        assertEquals(r1.getRoomName(), savedRoom.getRoomName());
    }

    @Test
    void testCreateRoom() {
        Room r2 = new Room();

        assertNotNull(r2);
    }

    @Test
    void testSaveAppointment() {
        Appointment savedAppointment = entityManager.persistAndFlush(a1);

        assertEquals(p1, savedAppointment.getPatient());
        assertEquals(d1, savedAppointment.getDoctor());
        assertEquals(r1, savedAppointment.getRoom());
        assertEquals(a1.getStartsAt(), savedAppointment.getStartsAt());
        assertEquals(a1.getFinishesAt(), savedAppointment.getFinishesAt());
    }

    @Test
    void testSettersAppointment() {
        a2 = new Appointment();
        LocalDateTime startsAt2 = LocalDateTime.parse("19:30 20/06/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("20:30 20/06/2023", formatter);

        a2.setId(1);
        a2.setPatient(p1);
        a2.setDoctor(d1);
        a2.setRoom(r1);
        a2.setStartsAt(startsAt2);
        a2.setFinishesAt(finishesAt2);

        assertEquals(1, a2.getId());
        assertEquals(p1, a2.getPatient());
        assertEquals(d1, a2.getDoctor());
        assertEquals(r1, a2.getRoom());
        assertEquals(startsAt2, a2.getStartsAt());
        assertEquals(finishesAt2, a2.getFinishesAt());
    }

    @Test
    void testOverlapsAppointment() {

        Doctor d2 = new Doctor("Marta", "Martinez", 60, "mma_1962@gmail.com");
        Patient p2 = new Patient("Carles", "Jou", 41, "carlesjou@hotmail.com");

        // Case 1: A.starts == B.starts
        // Case 2: A.finishes == B.finishes
        LocalDateTime startsAt2 = LocalDateTime.parse("19:30 20/06/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("20:30 20/06/2023", formatter);

        a2 = new Appointment(p2, d2, r1, startsAt2, finishesAt2);

        assertTrue(a1.overlaps(a2));


        // Case 3: A.starts < B.finishes && B.finishes < A.finishes
        // Case 4: B.starts < A.starts && A.finishes < B.finishes
        LocalDateTime startsAt3= LocalDateTime.parse("20:00 20/06/2023", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("21:00 20/06/2023", formatter);

        a3 = new Appointment(p2, d2, r1, startsAt3, finishesAt3);

        assertTrue(a1.overlaps(a3));
        assertTrue(a3.overlaps(a2));
    }

    @Test
    void testNoOverlapsAppointment() {
        Room r2 = new Room("Oncology");

        Doctor d2 = new Doctor("Marta", "Martinez", 60, "mma_1962@gmail.com");
        Patient p2 = new Patient("Carles", "Jou", 41, "carlesjou@hotmail.com");

        // Different room
        LocalDateTime startsAt2 = LocalDateTime.parse("19:30 20/06/2023", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("20:30 20/06/2023", formatter);

        a2 = new Appointment(p2, d2, r2, startsAt2, finishesAt2);

        assertFalse(a1.overlaps(a2));


        // Different datetime without overlapping
        LocalDateTime startsAt3= LocalDateTime.parse("20:30 20/06/2023", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("21:30 20/06/2023", formatter);

        a3 = new Appointment(p2, d2, r1, startsAt3, finishesAt3);

        assertFalse(a1.overlaps(a3));
    }


}
