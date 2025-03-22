package com.sdlc.pro.mymbstu.jpa;




import com.sdlc.pro.mymbstu.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByUserId(String userId);
    List<Patient> findByDoctorNameAndResponseIsNull(String doctorName);

        List<Patient> findAllByOrderByAppointmentDateDesc();

}

