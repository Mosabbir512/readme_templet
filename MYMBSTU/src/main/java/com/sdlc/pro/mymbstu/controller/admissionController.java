package com.sdlc.pro.mymbstu.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class admissionController {


    @GetMapping("/admission")
    public String admissionPage(HttpSession session, Model model) {
        return "admission/haha";
    }

    @PostMapping("/admission")
    public String admissionPage1(HttpSession session, Model model){
        return "admission/haha";
    }

    @GetMapping("/apply-admission")
    public String applyForAdmission() {
        return "admission/apply-admission"; // Create apply-admission.html
    }

    @GetMapping("/exam-dates")
    public String examDates(Model model) {
        model.addAttribute("examDates", new String[]{"10 April 2025", "12 April 2025", "15 April 2025"});
        return "admission/exam-dates"; // Create exam-dates.html
    }

    @GetMapping("/admission-notices")
    public String admissionNotices(Model model) {
        model.addAttribute("notices", new String[]{"Admission starts from March", "Online applications open till April 5"});
        return "admission/admission-notices"; // Create admission-notices.html
    }

    @GetMapping("/seat-allocation")
    public String seatAllocation(Model model) {
        model.addAttribute("seatInfo", "Your exam center: MBSTU Main Hall");
        return "admission/exam-seat-allocation"; // Create seat-allocation.html
    }

    @GetMapping("/guidance")
    public String admissionGuidance(Model model) {
        model.addAttribute("tips", "Prepare well, carry necessary documents, and be on time.");
        return "admission/guidance"; // Create guidance.html
    }
}
