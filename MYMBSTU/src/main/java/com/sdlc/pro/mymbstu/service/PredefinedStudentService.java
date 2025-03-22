package com.sdlc.pro.mymbstu.service;

import com.sdlc.pro.mymbstu.jpa.PredefinedStudentRepository;
import com.sdlc.pro.mymbstu.model.Diary;
import com.sdlc.pro.mymbstu.model.PredefinedStudent;
import com.sdlc.pro.mymbstu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class PredefinedStudentService {

    @Autowired
   private PredefinedStudentRepository predefinedStudentRepository;
    public Optional<PredefinedStudent> searchPredefinedStudent(String idPre, String departmentPre, String sessionPre,String hallPre) {

       // System.out.println("from service ");
        //System.out.println(idPre+" "+departmentPre+" "+sessionPre+" ");
        return predefinedStudentRepository.findByIdPreAndDepartmentPreAndSessionPreAndHallPre(idPre, departmentPre, sessionPre,hallPre);
    }
    public PredefinedStudent  findStudentById(String idPre) {
        Optional<PredefinedStudent> predefinedStudentOpt=predefinedStudentRepository.findByIdPre(idPre);

        return predefinedStudentOpt.orElse(null);
    }



//    Optional<User> userOpt = userRepository.findByEmail(email);
//        return userOpt.orElse(null); // Return null if user not found
}
