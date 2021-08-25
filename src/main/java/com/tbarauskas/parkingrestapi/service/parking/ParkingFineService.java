package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineRequestCreateDTO;
import com.tbarauskas.parkingrestapi.dto.parking.fine.ParkingFineRequestUpdateDTO;
import com.tbarauskas.parkingrestapi.entity.parking.city.ParkingCity;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.status.ParkingRecordStatus;
import com.tbarauskas.parkingrestapi.entity.parking.zone.ParkingZone;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.InvalidArgumentException;
import com.tbarauskas.parkingrestapi.exceptsion.ParkingRecordHasNoUserException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingFine fine = fineRepository.getParkingFineById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if ( user.isManager() || fine.getUser().getId().equals(user.getId()) ) {
            return fine;
        }
        throw new AccessDeniedException("Access denied");
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

    public ParkingFine createFine(User user, ParkingFineRequestCreateDTO fine) {
        LocalDateTime now = LocalDateTime.now();
        ParkingCity city = cityService.getCity(fine.getParkingCity());
        ParkingZone zone = zoneService.getZone(fine.getParkingZone());
        ParkingRecordStatus unpaid = statusService.getStatus(UNPAID.name());

        return fineRepository.save(new ParkingFine(user, city, zone, unpaid, now));
    }

    public ParkingFine updateFine(Long id, ParkingFineRequestUpdateDTO fine) {
        ParkingZone zone = zoneService.getZone(fine.getParkingZone());
        ParkingCity city = cityService.getCity(fine.getParkingCity());
        ParkingRecordStatus status = statusService.getStatus(fine.getRecordStatus());
        ParkingFine parkingFine = getFine(fine.getId());

        parkingFine.setRecordStatus(status);
        parkingFine.setParkingCity(city);
        parkingFine.setParkingZone(zone);
        return fineRepository.save(parkingFine);
    }

    public void deleteFine(Long id) {
        fineRepository.deleteById(getFine(id).getId());
    }

    public User getFinesUser(Long id) {
        User user = getFine(id).getUser();
        if (user == null) {
            throw new ParkingRecordHasNoUserException(id);
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
