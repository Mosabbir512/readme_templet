package com.sdlc.pro.mymbstu.jpa;



import com.sdlc.pro.mymbstu.model.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, String> {

    List<Diary> findByNameContainingIgnoreCaseOrDesignationContainingIgnoreCaseOrPhoneContainingOrEmailContainingIgnoreCase(
            String name, String designation, String phone, String email);

}
