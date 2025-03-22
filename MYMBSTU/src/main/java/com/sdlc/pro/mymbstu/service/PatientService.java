// PatientService.java
package com.sdlc.pro.mymbstu.service;

import com.sdlc.pro.mymbstu.model.Patient;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.jpa.PatientRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


import com.sdlc.pro.mymbstu.model.Patient;
import com.sdlc.pro.mymbstu.jpa.PatientRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


import com.sdlc.pro.mymbstu.model.Patient;
import com.sdlc.pro.mymbstu.jpa.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getUserHistory(String userId) {
        return patientRepository.findByUserId(userId);
    }
    // New methods for doctor dashboard
    public List<Patient> getAppointmentsByDoctorName(String doctorName) {
        return patientRepository.findByDoctorNameAndResponseIsNull(doctorName);
    }

    public void saveResponse(Long id, String response) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setResponse(response);
        patientRepository.save(patient);
    }
    public List<Patient> getAllAppointments() {
        return patientRepository.findAllByOrderByAppointmentDateDesc();
    }
}