package org.ticketservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ticketservice.domain.SeatHold;
import org.ticketservice.domain.Ticket;
import org.ticketservice.repository.TicketRepository;
import org.ticketservice.service.TicketService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Durga on 12/14/2015.
 */

@Service
public class TicketServiceImpl implements TicketService{

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public Map<String, List<Ticket>> currentStatus() {
        return ticketRepository.currentStatus();
    }

    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {

        return ticketRepository.numSeatsAvailable(venueLevel);
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
        return ticketRepository.findAndHoldSeats(numSeats, minLevel, maxLevel, customerEmail);
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        return ticketRepository.reserveSeats(seatHoldId, customerEmail);
    }
}
