package com.sdlc.pro.mymbstu.controller;



import com.sdlc.pro.mymbstu.jpa.PredefinedStudentRepository;
import com.sdlc.pro.mymbstu.model.PredefinedStudent;

import com.sdlc.pro.mymbstu.service.PredefinedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class adminController {

    @Autowired
    private PredefinedStudentService predefinedStudentService;

//    // Admin page to add predefined student info
//    @GetMapping("/admin/addStudent")
//    public String showAdminPage() {
//        return "admin/addStudent"; // Return HTML form to input student data
//    }
//
//    // Handle form submission to add student info to the database
//    @PostMapping("/admin/addStudent")
//    public String addStudent(PredefinedStudent student) {
//        predefinedStudentRepository.save(student);
//        return "redirect:/admin/addStudent";
//    }
}
