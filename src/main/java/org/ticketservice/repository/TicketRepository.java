package org.ticketservice.repository;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import lombok.Synchronized;
import org.springframework.stereotype.Repository;
import org.ticketservice.domain.SeatHold;
import org.ticketservice.domain.Ticket;
import org.ticketservice.util.TicketLevelEnum;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Durga on 12/15/2015.
 */

@Repository
public class TicketRepository {

    private static Map<Integer, List<Ticket>> TICKET_DATA;
    private static Map<String, SeatHold> SEAT_HOLDER;
    private Integer holdId = new Random().nextInt(999999);

    @PostConstruct
    public void init() {
        System.out.println("Initializing static ticketData map.");
        TICKET_DATA = new HashMap<>(4);
        TICKET_DATA.put(TicketLevelEnum.ORCHESTRA.getLevel(), getTickets(TicketLevelEnum.ORCHESTRA.getLevel()));
        TICKET_DATA.put(TicketLevelEnum.MAIN.getLevel(), getTickets(TicketLevelEnum.MAIN.getLevel()));
        TICKET_DATA.put(TicketLevelEnum.BALCONY2.getLevel(), getTickets(TicketLevelEnum.BALCONY2.getLevel()));
        TICKET_DATA.put(TicketLevelEnum.BALCONY1.getLevel(), getTickets(TicketLevelEnum.BALCONY1.getLevel()));

        SEAT_HOLDER = new HashMap<>();
    }

    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        return (int) TICKET_DATA.get(venueLevel.get()).stream()
                .filter(ticket -> (!ticket.isSold() && !ticket.isHold())).count();

    }

    @Synchronized
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel,
                                     Optional<Integer> maxLevel, String customerEmail) {
        List<Ticket> allocatedTickets = new ArrayList<>(numSeats);
        for (int i = minLevel.get(); i <= maxLevel.get(); i++) {
            for (Ticket ticket: TICKET_DATA.get(i)) {
                if (!ticket.isHold() && !ticket.isSold()) {
                    ticket.setHold(true);
                    allocatedTickets.add(ticket);
                }

                if (allocatedTickets.size() == numSeats) {
                    //break inner loop.
                    break;
                }
            }
            //break outer loop.
            if (allocatedTickets.size() == numSeats) {
                break;
            }
        }

        SeatHold seatHold = new SeatHold();
        seatHold.setEmail(customerEmail);
        seatHold.setId(holdId);
        seatHold.setTickets(allocatedTickets);
        SEAT_HOLDER.put(seatHold.getId() + customerEmail, seatHold);
        System.out.println("SeatHoldId for the customer: " + customerEmail + " is: " + seatHold.getId());
        return seatHold;
    }


    public String reserveSeats(int seatHoldId, String customerEmail) {
        SeatHold seatHold = SEAT_HOLDER.get(seatHoldId + customerEmail);
        for (Ticket t : seatHold.getTickets()) {
            for (Ticket ticket : TICKET_DATA.get(t.getLevel())) {
                if (t.getSeatNo() == ticket.getSeatNo()) {
                    ticket.setSold(true);
                }
            }
        }
        return "Your tickets has been confirmed.";
    }

    public Map<String, List<Ticket>> currentStatus() {
        Map<String, List<Ticket>> ticketMap = new HashMap<>(4);
        ticketMap.put(TicketLevelEnum.ORCHESTRA.name(), TICKET_DATA.get(TicketLevelEnum.ORCHESTRA.getLevel()));
        ticketMap.put(TicketLevelEnum.MAIN.name(), TICKET_DATA.get(TicketLevelEnum.MAIN.getLevel()));
        ticketMap.put(TicketLevelEnum.BALCONY1.name(), TICKET_DATA.get(TicketLevelEnum.BALCONY1.getLevel()));
        ticketMap.put(TicketLevelEnum.BALCONY2.name(), TICKET_DATA.get(TicketLevelEnum.BALCONY2.getLevel()));
        return ticketMap;
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroying the data.");
        TICKET_DATA = null;
        SEAT_HOLDER = null;
    }


    //Initializing the ticket data with. This may come from DB.
    private List<Ticket> getTickets(Integer level) {
        List<Ticket> tickets;
        switch (level) {
            case 1: {
                tickets = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Ticket ticket = new Ticket();
                    ticket.setSeatNo(i);
                    ticket.setPrice(new BigDecimal(100));
                    ticket.setLevel(TicketLevelEnum.ORCHESTRA.getLevel());
                    tickets.add(ticket);
                }
                return tickets;
            }

            case 2: {
                tickets = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Ticket ticket = new Ticket();
                    ticket.setSeatNo(i);
                    ticket.setPrice(new BigDecimal(75));
                    ticket.setLevel(TicketLevelEnum.MAIN.getLevel());
                    tickets.add(ticket);
                }
                return tickets;
            }

            case 3: {
                tickets = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Ticket ticket = new Ticket();
                    ticket.setSeatNo(i);
                    ticket.setPrice(new BigDecimal(50));
                    ticket.setLevel(TicketLevelEnum.BALCONY2.getLevel());
                    tickets.add(ticket);
                }
                return tickets;
            }

            case 4: {
                tickets = new ArrayList<>();
                for (int i = 1; i <= 10; i++) {
                    Ticket ticket = new Ticket();
                    ticket.setSeatNo(i);
                    ticket.setPrice(new BigDecimal(40));
                    ticket.setLevel(TicketLevelEnum.BALCONY1.getLevel());
                    tickets.add(ticket);
                }
                return tickets;
            }
        }
        return ImmutableList.of();
    }
}
