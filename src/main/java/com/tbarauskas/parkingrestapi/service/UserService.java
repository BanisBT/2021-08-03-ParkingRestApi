package com.tbarauskas.parkingrestapi.service;

import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingFine;
import com.tbarauskas.parkingrestapi.entity.parking.record.ParkingTicket;
import com.tbarauskas.parkingrestapi.entity.user.User;
import com.tbarauskas.parkingrestapi.exceptsion.NotEnoughMoneyToPayForParkingRecordException;
import com.tbarauskas.parkingrestapi.exceptsion.ResourceNotFoundException;
import com.tbarauskas.parkingrestapi.exceptsion.UsernameAlreadyExistException;
import com.tbarauskas.parkingrestapi.model.UserRoleName;
import com.tbarauskas.parkingrestapi.repository.UserRepository;
import com.tbarauskas.parkingrestapi.service.parking.ParkingFineService;
import com.tbarauskas.parkingrestapi.service.parking.ParkingRecordStatusService;
import com.tbarauskas.parkingrestapi.service.parking.ParkingTicketService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.PAID;
import static com.tbarauskas.parkingrestapi.model.ParkingStatusName.UNPAID;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    private final UserRoleService roleService;

    private final ParkingRecordStatusService statusService;

    private final ParkingFineService fineService;

    private final ParkingTicketService ticketService;

    private final FinanceService financeService;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, UserRoleService roleService,
                       ParkingRecordStatusService statusService, ParkingFineService fineService,
                       ParkingTicketService ticketService, FinanceService financeService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.statusService = statusService;
        this.fineService = fineService;
        this.ticketService = ticketService;
        this.financeService = financeService;
    }

    public User getUser(Long id) {
        return userRepository.getUserById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public List<User> getUsers(String search) {
        if (search == null) {
            return userRepository.findAll();
        }
        return userRepository.getUsersByUsernameContainingIgnoreCase(search);
    }

    public User createUser(User user) {
        if (userRepository.getUserByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistException(user.getUsername());
        }
        user.setRoles(Set.of(roleService.getUserRole(UserRoleName.REGULAR.name())));
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(Long id, User updateUser) {
        User user = getUser(updateUser.getId());
        updateUser.setCreated(user.getCreated());
        return userRepository.save(updateUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(getUser(id).getId());
    }

    public List<ParkingFine> getUsersFines(Long id) {
        return getUser(id).getFines();
    }

    public List<ParkingFine> getUsersUnpaidFines(Long id) {
        return fineService.getUsersFinesByStatus(getUser(id), statusService.getStatus(UNPAID.name()));
    }

    public List<ParkingTicket> getUsersTickets(Long id) {
        return getUser(id).getTickets();
    }

    public void isEnoughMoneyToPayForTicket(User user) {
        ParkingTicket openTicket = ticketService.getUsersOpenTicket(user);

//        TODO time for testing in real will be LocalTime.now
        LocalDateTime testingEndTime = openTicket.getParkingBegan().plusHours(1);

        BigDecimal ticketAmount = financeService.getTicketsAmount(openTicket.getParkingZone().getCostPerHour(),
                openTicket.getParkingBegan(), testingEndTime);

        if (user.getBalance().compareTo(ticketAmount) >= 0) {
            user.setBalance(user.getBalance().subtract(ticketAmount));
            updateUser(user.getId(), user);
            ticketService.setTicketsStatus(openTicket.getId(), PAID.name());
        } else {
            ticketService.setTicketsStatus(openTicket.getId(), UNPAID.name());
            throw new NotEnoughMoneyToPayForParkingRecordException(user.getBalance().subtract(ticketAmount),
                    getUser(user.getId()));
        }
    }

    public void isEnoughMoneyToPayForFine(User user, Long fineId) {
        ParkingFine fine = fineService.getFine(fineId);
        BigDecimal fineAmount = fine.getFineAmount();

        if (user.getBalance().compareTo(fineAmount) >= 0) {
            user.setBalance(user.getBalance().subtract(fineAmount));
            updateUser(user.getId(), user);
            fineService.setFineStatus(fine.getId(), PAID.name());
        } else {
            fineService.setFineStatus(fine.getId(), UNPAID.name());
            throw new NotEnoughMoneyToPayForParkingRecordException(user.getBalance().subtract(fineAmount),
                    getUser(user.getId()));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
