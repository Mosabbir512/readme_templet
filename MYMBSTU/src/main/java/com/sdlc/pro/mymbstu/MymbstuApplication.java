package com.sdlc.pro.mymbstu;

import com.sdlc.pro.mymbstu.service.DiaryImportService;
import com.sdlc.pro.mymbstu.service.PredefinedStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class MymbstuApplication implements CommandLineRunner {
    @Autowired
    private DiaryImportService diaryImportService;

    @Autowired
    private PredefinedStudentService predefinedStudentService;


    public static void main(String[] args) {
        SpringApplication.run(MymbstuApplication.class, args);
    }

    @Override
    public void run(String... args) {
        //diaryImportService.importCsv();
    }
}
