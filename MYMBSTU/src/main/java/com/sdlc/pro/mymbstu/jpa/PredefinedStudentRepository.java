package com.sdlc.pro.mymbstu.jpa;


import com.sdlc.pro.mymbstu.model.PredefinedStudent;
import com.sdlc.pro.mymbstu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PredefinedStudentRepository extends JpaRepository<PredefinedStudent, String>{


   Optional<PredefinedStudent> findByIdPreAndDepartmentPreAndSessionPreAndHallPre(String idPre, String departmentPre, String sessionPre,String hallPre);


   Optional<PredefinedStudent> findByIdPre(String idPre);



}
