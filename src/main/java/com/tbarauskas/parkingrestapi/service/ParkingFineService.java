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

    public ParkingFine getFine(Long id) {
        return fineRepository.getParkingFineById(id);
    }

    public List<ParkingFine> getFines() {
        return fineRepository.findAll();
    }

    public ParkingFine createFine(ParkingFine parkingFine) {
        return fineRepository.save(parkingFine);
    }

    public ParkingFine updateFine(Long id, ParkingFine updateParkingFine) {
        ParkingFine parkingFine = fineRepository.getParkingFineById(updateParkingFine.getId());

        if (parkingFine != null) {
            updateParkingFine.setCreated(parkingFine.getCreated());
            return fineRepository.save(updateParkingFine);
        }
        return null;
    }

    public void deleteFine(Long id) {
        if (getFine(id) != null) {
            fineRepository.deleteById(id);
        }
    }
}
