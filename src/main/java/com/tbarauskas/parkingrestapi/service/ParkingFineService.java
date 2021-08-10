package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingFineService {

    private final ParkingFineRepository fineRepository;

    public ParkingFineService(ParkingFineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public ParkingFine getParkingFine(Long id) {
        return fineRepository.getParkingFineById(id);
    }

    public List<ParkingFine> getFines() {
        return fineRepository.findAll();
    }
}
