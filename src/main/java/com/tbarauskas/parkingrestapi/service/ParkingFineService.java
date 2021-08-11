package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.excepsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingFineService {

    private final ParkingFineRepository fineRepository;

    public ParkingFineService(ParkingFineRepository fineRepository) {
        this.fineRepository = fineRepository;
    }

    public ParkingFine getFine(Long id) {
        return fineRepository.getParkingFineById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<ParkingFine> getFines(LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (dateFrom == null && dateTo == null) {
            return fineRepository.findAll();
        } else if (dateFrom == null) {
            return fineRepository.getParkingFinesByFineDateTimeBefore(dateTo);
        } else if (dateTo == null) {
            return fineRepository.getParkingFinesByFineDateTimeAfter(dateFrom);
        }
        return fineRepository.getParkingFinesByFineDateTimeBetween(dateFrom, dateTo);
    }

    public ParkingFine createFine(ParkingFine parkingFine) {
        return fineRepository.save(parkingFine);
    }

    public ParkingFine updateFine(Long id, ParkingFine updateParkingFine) {
        ParkingFine parkingFine = getFine(updateParkingFine.getId());

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
