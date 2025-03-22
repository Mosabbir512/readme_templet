package com.sdlc.pro.mymbstu.controller;

import com.sdlc.pro.mymbstu.model.Diary;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.service.DiaryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

//@Controller
public class diaryController1 {

    @Autowired
    private DiaryService userService;

    @GetMapping("/diary")
    public String diaryHome(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        boolean isAdmin = user != null && "admin_1".equalsIgnoreCase(user.getId());

        model.addAttribute("isLoggedIn", user != null);
        model.addAttribute("isAdmin", isAdmin);

        return "diary/diaryHome";
    }

    @GetMapping("/api/search")
    @ResponseBody
    public ResponseEntity<?> searchDiary(@RequestParam("query") String query, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("status", "Unauthorized"));
        }
        return ResponseEntity.ok(userService.searchDiary(query));
    }

    @GetMapping("/add-info")
    public String addInfoPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to access this page.");
            return "basic/login";
        }

        if (!"admin_1".equalsIgnoreCase(user.getId())) {
            return "diary/diaryHome";
        }

        model.addAttribute("message", null);
        return "diary/addInfo";
    }

    @PostMapping("/add-info")
    public String saveInfo(@ModelAttribute Diary diary, Model model) {
        userService.saveDiary(diary);
        model.addAttribute("message", "Information about " + diary.getName() + " is added successfully.");
        return "diary/addInfo";
    }

    @GetMapping("/delete-info")
    public String deleteInfoPage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            model.addAttribute("errorMessage", "You must log in to access this page.");
            return "basic/login";
        }

        if (!"admin_1".equalsIgnoreCase(user.getId())) {
            return "diary/diaryHome";
        }

        model.addAttribute("message", null);
        return "diary/deleteInfo";
    }

    @PostMapping("/delete-info")
    public String deleteInfo(@RequestParam("email") String email, Model model) {
        Optional<Diary> diaryOpt = userService.findDiaryByEmail(email);
        if (diaryOpt.isPresent()) {
            Diary diary = diaryOpt.get();
            userService.deleteDiary(email);
            model.addAttribute("message", "Information about " + diary.getName() + " is deleted successfully.");
        } else {
            model.addAttribute("message", "No record found for email " + email);
        }
        return "diary/deleteInfo";
    }
}
