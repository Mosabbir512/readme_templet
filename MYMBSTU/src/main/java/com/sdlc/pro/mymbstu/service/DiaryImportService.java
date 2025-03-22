package com.sdlc.pro.mymbstu.service;

import com.sdlc.pro.mymbstu.jpa.DiaryRepository;
import com.sdlc.pro.mymbstu.model.Diary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class
DiaryImportService {

    @Autowired
    private DiaryRepository repository;

    public void importCsv() {
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource("data.csv").getFile()))) {
            br.readLine();  // Skip header

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                String email = data[0].trim();
                String em=email.toLowerCase()+"@mbstu.ac.bd";
                String name = data[1].trim();
                String designation = data[5].trim();
                double d= Double.parseDouble(data[2].trim());
                String phone=data[2].trim();
                String department = data[3].trim();

                Diary diary = new Diary(em, name, designation, phone, department);
                repository.save(diary);
            }
            System.out.println("CSV Import Completed Successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}