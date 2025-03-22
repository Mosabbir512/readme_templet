package com.sdlc.pro.mymbstu.service;

import com.sdlc.pro.mymbstu.model.Token;
import com.sdlc.pro.mymbstu.jpa.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepo;


}
