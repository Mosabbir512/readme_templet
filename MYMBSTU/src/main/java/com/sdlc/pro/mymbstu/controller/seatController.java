package com.sdlc.pro.mymbstu.controller;


import com.sdlc.pro.mymbstu.model.Seat;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.service.PredefinedStudentService;
import com.sdlc.pro.mymbstu.service.SeatService;
import com.sdlc.pro.mymbstu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class seatController {


    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;

    @Autowired
    private PredefinedStudentService predefinedStudentService;

    // Display the seat management page
    @GetMapping("/seat")
    public String showSeatManagementPage(HttpSession session,
                                         Model model) {
        User user = (User) session.getAttribute("loggedInUser");


        // System.out.println(user);
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to see seat information.");
            return "basic/login"; // Redirect to login if user isn't logged in
        }

        Seat seat = seatService.findSeatById(user.getId())
                .orElse(new Seat(user.getId(), "", "Pending", "Pending", "Pending", "Pending", "Pending"));


        // Add the seat and user info to the model
        model.addAttribute("user", user);
        model.addAttribute("seat", seat);

        return "seat/seatManagement"; // Renders seatManagement.html
    }









    @GetMapping("/applySeat")
    public String requestSeatPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");



        // System.out.println(user);
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to see seat information.");
            return "basic/login"; // Redirect to login if user isn't logged in
        }

        model.addAttribute("user", user);
        return "seat/applySeat";
    }

    // Handle the request seat form submission
    @PostMapping("/applySeat")
    public String handleRequestSeat(@RequestParam String fatherIncome,
                                    @RequestParam String permanentAddress,
                                    @RequestParam String reason,
                                    HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        // System.out.println(user);
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to see seat information.");
            return "basic/login"; // Redirect to login if user isn't logged in
        }

        // Process and save ID card request (e.g., save to DB)
        String successMessage = String.format("Dear %s, your application for a Seat has been submitted successfully. Our team will process your request shortly.",
                user.getUsername());
        // Here you can process the request seat details (e.g., save to DB or log)
        model.addAttribute("successMessage", successMessage);
        return "seat/applicationSuccess";
    }
    @GetMapping("/applicationSuccess")
    public String applicationSuccessPage(HttpSession session,
                                         Model model) {

        User user = (User) session.getAttribute("loggedInUser");


        // System.out.println(user);
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to see seat information.");
            return "basic/login"; // Redirect to login if user isn't logged in
        }
        return "seat/applicationSuccess"; // Renders the success page
    }

    @GetMapping("/applyID")
    public String requestIDCardPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to apply for an ID card.");
            return "basic/login";
        }
        model.addAttribute("user", user);
        return "seat/applyID";
    }
    @PostMapping("/applyID")
    public String handleIDCardRequest(
            @RequestParam String idCardType,
            @RequestParam String dob,
            @RequestParam String fatherName,
            @RequestParam String motherName,
            @RequestParam String permanentAddress,
            @RequestParam("photo") MultipartFile photo,
            HttpSession session,
            Model model) {

        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to apply for an ID card.");
            return "basic/login";
        }

        // Process and save ID card request (e.g., save to DB)
        String successMessage = String.format("Dear %s, your application for a %s ID card has been submitted successfully. Our team will process your request shortly.",
                user.getUsername(), idCardType.equals("new") ? "new" : "replacement");

        model.addAttribute("successMessage", successMessage);
        return "seat/applicationSuccess"; // Ensure this corresponds to the success page template
    }


    @GetMapping("/applyAttestation")
    public String requestAttestationPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to apply for an attestation.");
            return "basic/login";
        }
        Seat seat = seatService.findSeatById(user.getId())
                .orElse(new Seat(user.getId(), "", "Pending", "Pending", "Pending", "Pending", "Pending"));


        // Add the seat and user info to the model
        model.addAttribute("user", user);
        model.addAttribute("seat", seat);
        return "seat/applyAttestation";
    }

    @PostMapping("/applyAttestation")
    public String handleAttestationRequest(@RequestParam String reason,
                                           HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to apply for an attestation.");
            return "basic/login";
        }
        // Process and save ID card request (e.g., save to DB)
        String successMessage = String.format("Dear %s, your application for an Attestation paper has been submitted successfully. Our team will process your request shortly.",
                user.getUsername());
        // Process and save attestation request (e.g., save to DB)
        model.addAttribute("successMessage", successMessage);
        return "seat/applicationSuccess";
    }

    @GetMapping("/applyLostCertificate")
    public String requestLostCertificatePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to apply for a lost certificate.");
            return "basic/login";
        }
        model.addAttribute("user", user);
        return "seat/applyLostCertificate";
    }

    @PostMapping("/applyLostCertificate")
    public String handleLostCertificateRequest(@RequestParam("gdPhoto") MultipartFile gdPhoto,
                                               HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to apply for a lost certificate.");
            return "basic/login";
        }


        String successMessage = String.format("Dear %s, your application for a Lost Certificate has been submitted successfully. Our team will process your request shortly.",
                user.getUsername());
        model.addAttribute("successMessage", successMessage);
        return "seat/applicationSuccess";
    }




}
