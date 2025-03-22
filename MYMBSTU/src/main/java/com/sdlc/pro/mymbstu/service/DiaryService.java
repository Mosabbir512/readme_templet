package com.sdlc.pro.mymbstu.service;



import com.sdlc.pro.mymbstu.model.Diary;
import com.sdlc.pro.mymbstu.jpa.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryService {

    @Autowired
    private DiaryRepository diaryRepository;
    public List<Diary> searchDiary(String query) {
        return diaryRepository.findByNameContainingIgnoreCaseOrDesignationContainingIgnoreCaseOrPhoneContainingOrEmailContainingIgnoreCase(query, query, query, query);
    }
    public void saveDiary(Diary diary) {
        diaryRepository.save(diary);

    }
    public void deleteDiary(String email) {
        diaryRepository.deleteById(email);
    }


    public Optional<Diary> findDiaryByEmail(String email) {
        return diaryRepository.findById(email);
    }


}
