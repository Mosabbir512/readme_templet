package com.sdlc.pro.mymbstu.controller;

import com.sdlc.pro.mymbstu.model.Token;
import com.sdlc.pro.mymbstu.model.User;
import com.sdlc.pro.mymbstu.service.TokenService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class tokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")
    public String tokenHome() {
        return "token/tokenHome";
    }


}
