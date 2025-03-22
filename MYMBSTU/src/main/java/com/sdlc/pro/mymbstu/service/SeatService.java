package com.sdlc.pro.mymbstu.service;

import com.sdlc.pro.mymbstu.model.Seat;
import com.sdlc.pro.mymbstu.jpa.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public Seat saveSeat(Seat seat) {
        return seatRepository.save(seat);
    }

    public Optional<Seat> findSeatById(String id) {

        return seatRepository.findById(id);
    }
}
