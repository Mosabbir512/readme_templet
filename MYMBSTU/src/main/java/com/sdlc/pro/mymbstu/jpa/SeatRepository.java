package com.sdlc.pro.mymbstu.jpa;



import com.sdlc.pro.mymbstu.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat,String> {

}
