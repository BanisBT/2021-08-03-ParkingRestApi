package com.tbarauskas.parkingrestapi.service.parking;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.InvalidArgumentException;
import com.tbarauskas.parkingrestapi.exceptsion.ParkingRecordHasNoUserException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.repository.ParkingFineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingFineServiceTest {

    @Mock
    private User user;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private ParkingFineRepository fineRepository;

    @Mock
    private ParkingFine fine;

    @InjectMocks
    private ParkingFineService fineService;

    @Test
    void testGetFineByOwner() {
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(fineRepository.getParkingFineById(1L)).thenReturn(Optional.of(fine));
        when(fine.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(1L);

        ParkingFine parkingFine = fineService.getFine(1L);

        assertEquals(fine, parkingFine);
    }

    @Test
    void testGetFineByManager() {
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(fineRepository.getParkingFineById(1L)).thenReturn(Optional.of(fine));
        when(user.isManager()).thenReturn(true);

        ParkingFine parkingFine = fineService.getFine(1L);

        assertEquals(fine, parkingFine);
    }

    @Test
    void testGetFineThenNotExist() {
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(fineRepository.getParkingFineById(any(long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> fineService.getFine(101L));
    }

    @Test
    void testGetFinesBothDateNull() {
        when(fineRepository.findAll()).thenReturn(List.of(fine));

        List<ParkingFine> fines = fineService.getFines(null, null);

        verify(fineRepository, times(1)).findAll();
        assertFalse(fines.isEmpty());
    }

    @Test
    void testGetFinesDateFromNull() {
        LocalDateTime now = LocalDateTime.now();
        when(fineRepository.getParkingFinesByFineDateTimeBefore(now)).thenReturn(List.of(fine));

        List<ParkingFine> fines = fineService.getFines(null, now);

        verify(fineRepository, times(1)).getParkingFinesByFineDateTimeBefore(now);
        assertFalse(fines.isEmpty());
    }

    @Test
    void testGetFinesDateToNull() {
        LocalDateTime now = LocalDateTime.now();
        when(fineRepository.getParkingFinesByFineDateTimeAfter(now)).thenReturn(List.of(fine));

        List<ParkingFine> fines = fineService.getFines(now, null);

        verify(fineRepository, times(1)).getParkingFinesByFineDateTimeAfter(now);
        assertFalse(fines.isEmpty());
    }

    @Test
    void testGetFines() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus = now.plusDays(1);
        when(fineRepository.getParkingFinesByFineDateTimeBetween(now, nowPlus)).thenReturn(List.of(fine));

        List<ParkingFine> fines = fineService.getFines(now, nowPlus);

        verify(fineRepository, times(1)).getParkingFinesByFineDateTimeBetween(now, nowPlus);
        assertFalse(fines.isEmpty());
    }

    @Test
    void testDeleteFine() {
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(user.isManager()).thenReturn(true);
        when(fineRepository.getParkingFineById(1L)).thenReturn(Optional.of(fine));

        fineService.deleteFine(1L);

        verify(fineRepository, times(1)).getParkingFineById(1L);
        verify(fineRepository, times(1)).deleteById(fine.getId());
    }

    @Test
    void getFinesUser() {
        when(fineRepository.getParkingFineById(1L)).thenReturn(Optional.of(fine));

        assertThrows(ParkingRecordHasNoUserException.class, () -> fineService.getFinesUser(1L));
    }

    @Test
    void testSetFineStatus() {
    }

    @Test
    void testSetFineAmount() {
        SecurityContextHolder.setContext(securityContext);
        BigDecimal newAmount = BigDecimal.valueOf(10);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(user);
        when(fineRepository.getParkingFineById(1L)).thenReturn(Optional.of(fine));
        when(user.isManager()).thenReturn(true);

        fineService.setFineAmount(1L, newAmount);

        verify(fineRepository, times(1)).getParkingFineById(1L);
        verify(fineRepository, times(1)).save(fine);
    }

    @Test
    void testSetFineAmountThenNull() {
        assertThrows(InvalidArgumentException.class, () -> fineService.setFineAmount(1L, null));
        verify(fineRepository, times(0)).getParkingFineById(1L);
        verify(fineRepository, times(0)).save(fine);
    }

    @Test
    void getUsersFinesByStatus() {
    }

    @Test
    void createFine() {
    }
}
