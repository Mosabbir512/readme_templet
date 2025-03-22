package com.sdlc.pro.mymbstu.jpa;

import com.sdlc.pro.mymbstu.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByStudentIdAndMealDate(String studentId, String mealDate);
}
