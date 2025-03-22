package com.sdlc.pro.mymbstu.controller;


import com.sdlc.pro.mymbstu.model.Seat;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.service.PredefinedStudentService;
import com.sdlc.pro.mymbstu.service.SeatService;
import com.sdlc.pro.mymbstu.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class mainController {


    @Autowired
    private SeatService seatService;

    @Autowired
    private UserService userService;
    @Autowired
    private PredefinedStudentService predefinedStudentService;

    private final Map<String, String> verificationCodes = new HashMap<>();

    // Initialize predefined admin users when the application starts
    @PostConstruct
    public void init() {
        createAdminIfNotExists("admin_1", "touhid_Admin", "1234", "touhid20@gmail.com", "", "ADMIN", "ICT", "","");
        createAdminIfNotExists("admin_2", "mosabbir_Admin", "1234", "mosabbir19@gmail.com", "", "ADMIN", "ICT", "","");
        createAdminIfNotExists("doctor_1", "Dr. Ahmad Hossain Siddiqui", "1234", "ahmadhossain@gmail.com", "", "ADMIN", "Senior Consultant", "","");
        createAdminIfNotExists("doctor_2", "Dr. Kawsar Ahmad", "1234", "kawsarahmad@gmail.com", "", "ADMIN", "Chief Medical Officer", "", "");
        createAdminIfNotExists("doctor_3", "Dr. Lipa Debnath", "1234", "lipadebnath@gmail.com", "", "ADMIN", "Emergency Specialist", "", "");
        createAdminIfNotExists("doctor_4", "Dr. Harun-Ur-Rashid Rasel", "1234", "harunrashid@gmail.com", "", "ADMIN", "Oncology Specialist", "", "");
        createAdminIfNotExists("doctor_5", "Dr. Nur Md. Kawser Abid", "1234", "nurmdkawser@gmail.com", "", "ADMIN", "Full-time Physician", "", "");
        createAdminIfNotExists("doctor_6", "Dr. Tahmina Yasmin", "1234", "tahminayasmin@gmail.com", "", "ADMIN", "Specialist", "", "");

    }

    private void createAdminIfNotExists(String id, String username, String password, String email, String phone, String role, String department, String session2,String hallName) {
        if (userService.findById(id) == null) {
            User adminUser = new User(id, username, password, email, phone, role, department, session2,hallName);
            userService.save(adminUser);
        }
    }

    // Home page
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        return "home";
    }



    // Login page
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "successMessage", required = false) String successMessage,
            @RequestParam(value = "errorMessage", required = false) String errorMessage,
            HttpSession session,
            Model model) {



        String email = (String) session.getAttribute("email");
        if (email != null && userService.findByEmail(email) != null) {
            return "redirect:/home"; // Redirect logged-in users to home page
        }

        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "basic/login"; // Show login page only for non-authenticated users
    }


    @PostMapping("/login")
    public String login(
            @RequestParam("id") String id,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {

        User user = userService.findById(id);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("email", user.getEmail());
            session.setAttribute("role", user.getRole());
            session.setAttribute("loggedInUser", user);

            if (id.matches("(?i)doctor_[1-6]")) {
                return "redirect:/medical/dashboard";
            }

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "redirect:/home";
               // return "redirect:/adminHome";
            } else {
                return "redirect:/home";
            }
        }

        model.addAttribute("errorMessage", "ID or Password doesn't match.");
        return "basic/login";
    }

    // Registration page
    @GetMapping("/register")
    public String registerPage() {
        return "basic/register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            @RequestParam("department") String department,
            @RequestParam("session2") String session2,@RequestParam("hallName") String hallName,
            HttpSession session,
            Model model) {


        if (phone.length() < 11) {
            model.addAttribute("errorMessage", "Phone number must be at least 11 characters long.");
            return "basic/register";
        }

        if (userService.findById(id) != null) {
            //System.out.println("id exist");
            model.addAttribute("errorMessage", "Id(user) already exists.");
            return "basic/register";
        }


        if (!(email.endsWith("@gmail.com") || email.endsWith("@mbstu.ac.bd"))) {
            model.addAttribute("errorMessage", "Email must end with @gmail.com or @mbstu.ac.bd");
            return "basic/register";
        }


        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "basic/register";
        }

        if (userService.findByEmail(email) != null) {
            model.addAttribute("errorMessage", "Email already exists.");
            return "basic/register";
        }


        if(predefinedStudentService.findStudentById(id)==null)
        {

            model.addAttribute("errorMessage", "Please,Enter a valid Student ID");
            return "basic/register"; // return to the registration page with an error
        }


        //System.out.println(predefinedStudentService.searchPredefinedStudent(id,department,session2,hallName).isEmpty());

        if (predefinedStudentService.searchPredefinedStudent(id,department,session2,hallName).isEmpty()) {
                model.addAttribute("errorMessage", "Provided information does not match with any legal student's Information.Please,Enter your information correctly");
                return "basic/register"; // return to the registration page with an error
            }


        User newUser = new User(id, username, password, email, phone, "STUDENT", department, session2,hallName);
        userService.save(newUser);

        // Check if the user's ID already exists in the database



        if (seatService.findSeatById(id).isEmpty()) {
            // Create and save a new Seat object
            Seat newSeat = new Seat();
            newSeat.setId(id);
            newSeat.setHallName(hallName);
            newSeat.setSessionId(session2);
            newSeat.setRoomNumber("Pending"); // Default value indicating allocation pending
            newSeat.setSeatNumber("Pending"); // Default value indicating allocation pending
            newSeat.setAllocationDate("Pending");
            newSeat.setExpiryDate("Pending");

            seatService.saveSeat(newSeat);
            model.addAttribute("seat12345", newSeat);

           // return "redirect:/seatInfo";
        }

        session.setAttribute("loggedInUser", newUser);


        // Generate verification code
        String verificationCode = String.valueOf((int) (Math.random() * 900000) + 100000);
        verificationCodes.put(email, verificationCode);
        System.out.println("Verification code sent to " + email + ": " + verificationCode);

        // Store email in session for the next step
        session.setAttribute("email", email);

        return "redirect:/insertCode";
    }


    @GetMapping("/insertCode")
    public String insertCodePage() {
        return "basic/insertCode";
    }

    @PostMapping("/insertCode")
    public String processInsertCode(@RequestParam("code") String code, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        String storedCode = verificationCodes.get(email);

        if (storedCode != null && storedCode.equals(code)) {
            // Verification successful, remove the code from the map
            verificationCodes.remove(email);
            return "redirect:/login?successMessage=Registration successful! Please log in.";
        }

        model.addAttribute("errorMessage", "Invalid verification code.");
        return "basic/insertCode";
    }


    // Forgot password page
    @GetMapping("/forgotPassword")
    public String forgotPasswordPage() {
        return "basic/forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, HttpSession session, Model model) {
        User user = userService.findByEmail(email);

        if (user == null || "ADMIN".equalsIgnoreCase(user.getRole())) {
            model.addAttribute("errorMessage", "Email not registered.");
            return "basic/forgotPassword"; // Use forward here, not redirect
        }

        String verificationCode = String.valueOf((int) (Math.random() * 900000) + 100000);
        verificationCodes.put(email, verificationCode);
        System.out.println("Verification code sent to " + email + ": " + verificationCode);

        session.setAttribute("email", email);
        return "redirect:/verifyCode";
    }




    @GetMapping("/verifyCode")
    public String verifyCodePage(Model model, HttpSession session) {
        String errorMessage = (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        return "basic/verifyCode";
    }

    @PostMapping("/verifyCode")
    public String processVerifyCode(@RequestParam("code") String code, HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");
        String storedCode = verificationCodes.get(email);

        if (storedCode != null && storedCode.equals(code)) {

            return "redirect:/resetPassword";
        }

        model.addAttribute("errorMessage", "Invalid verification code.");
        return "basic/verifyCode";
    }


    @GetMapping("/resetPassword")
    public String resetPasswordPage(Model model, HttpSession session) {
        String errorMessage = (String) session.getAttribute("errorMessage");

        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
            session.removeAttribute("errorMessage");
        }

        return "basic/resetPassword";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session,
            Model model) {

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match.");
            return "basic/resetPassword";
        }

        if (newPassword.length() < 8) {
            model.addAttribute("errorMessage", "Password must be at least 8 characters long.");
            return "basic/resetPassword";
        }

        String email = (String) session.getAttribute("email");
        User user = userService.findByEmail(email);


        if (user != null) {
            user.setPassword(newPassword);
            userService.save(user);
            session.setAttribute("loggedInUser", user);
            return "redirect:/login?successMessage=Password reset successfully.";
        }

        model.addAttribute("errorMessage", "Failed to reset password.");
        return "basic/resetPassword";
    }

//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate(); // Invalidate the session
//        return "redirect:/login?successMessage=You have been logged out.";
//    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
//        return "redirect:/login?successMessage=You have been logged out.";
        return "home";
    }


}
