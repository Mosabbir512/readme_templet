package com.sdlc.pro.mymbstu.controller;

import com.sdlc.pro.mymbstu.model.Patient;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.service.PatientService;
import com.sdlc.pro.mymbstu.service.PredefinedStudentService;
import com.sdlc.pro.mymbstu.service.SeatService;
import com.sdlc.pro.mymbstu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


import com.sdlc.pro.mymbstu.model.Patient;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.service.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class medicalController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/medical")
    public String showMedicalManagementPage(@RequestParam(value = "success", required = false) String success,
                                            HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to get medical services");
            return "basic/login"; // Redirect to login if user isn't logged in
        }

        // Add a new Patient object to the model for the form
        model.addAttribute("patient", new Patient());
        model.addAttribute("user", user);

        // Add the success message to the model if it exists
        if (success != null) {
            model.addAttribute("successMessage", "Your appointment has been successfully received. Please wait for our response.");
        }

        return "medical/medicalHome";
    }

    // Handle form submission
    @PostMapping("/book-appointment")
    public String bookAppointment(@ModelAttribute("patient") Patient patient,
                                  HttpSession session,
                                  Model model) {
        // Get logged-in user from session
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to book an appointment.");
            return "basic/login"; // Redirect to login if user isn't logged in
        }

        // Set user details in the patient object
        patient.setUserId(user.getId().toString());
        patient.setUsername(user.getUsername());
        patient.setResponse(null); // Initialize response as null

        // Save appointment
        patientService.savePatient(patient);

        // Redirect to a success page or show a success message
        return "medical/homeAfterSubmission";
    }


    @GetMapping("/medical/dashboard")
    public String doctorDashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        // Get ALL appointments from database
        List<Patient> allAppointments = patientService.getAllAppointments();

        model.addAttribute("appointments", allAppointments);
        model.addAttribute("doctorName", user.getUsername());
        return "medical/doctorDashboard";
    }
    @PostMapping("/save-response")
    public String saveResponse(@RequestParam("appointmentId") Long id,
                               @RequestParam("response") String response) {
        patientService.saveResponse(id, response);
        return "redirect:/medical/dashboard?success=Response+submitted";
    }
    @GetMapping("/view-responses")
    public String viewAppointmentHistory(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<Patient> appointments = patientService.getUserHistory(user.getId().toString());
        model.addAttribute("appointments", appointments);
        return "medical/appointmentHistory";
    }





}
