package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.InvalidArgumentException;
import com.tbarauskas.parkingrestapi.exceptsion.ParkingRecordHasNotUserException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.UNPAID;

@Service
public class ParkingFineService {

    private final ParkingFineRepository fineRepository;

    private final ParkingRecordStatusService statusService;

    private final ParkingCityService cityService;

    private final ParkingZoneService zoneService;

    public ParkingFineService(ParkingFineRepository fineRepository, ParkingRecordStatusService statusService,
                              ParkingCityService cityService, ParkingZoneService zoneService) {
        this.fineRepository = fineRepository;
        this.statusService = statusService;
        this.cityService = cityService;
        this.zoneService = zoneService;
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

    public ParkingFine createFine(User user, String cityName, String zoneName) {
        LocalDateTime now = LocalDateTime.now();
        ParkingCity city = cityService.getCity(cityName);
        ParkingZone zone = zoneService.getZone(zoneName);
        ParkingRecordStatus unpaid = statusService.getStatus(UNPAID.name());

        return fineRepository.save(new ParkingFine(user, city, zone, unpaid, now));
    }

    public ParkingFine updateFine(Long id, ParkingFine updateParkingFine) {
        ParkingFine parkingFine = getFine(updateParkingFine.getId());
        updateParkingFine.setCreated(parkingFine.getCreated());
        return fineRepository.save(updateParkingFine);
    }

    public void deleteFine(Long id) {
        fineRepository.deleteById(getFine(id).getId());
    }

    public User getFinesUser(Long id) {
        User user = getFine(id).getUser();
        if (user == null) {
            throw new ParkingRecordHasNotUserException(id);
        } else {
            return user;
        }
    }

    public void setFineStatus(Long id, String fineStatus) {
        ParkingFine fine = getFine(id);
        fine.setRecordStatus(statusService.getStatus(fineStatus));
        fineRepository.save(fine);
    }

    public void setFineAmount(Long id, BigDecimal fineAmount) {
        if (fineAmount == null) {
            throw new InvalidArgumentException();
        } else {
            ParkingFine fine = getFine(id);
            fine.setFineAmount(fineAmount);
            fineRepository.save(fine);
        }

    }

    public List<ParkingFine> getUsersFinesByStatus(User user, ParkingRecordStatus status) {
        return fineRepository.getParkingFinesByUserAndRecordStatus(user, status);
    }
}
