package org.ticketservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by Durga on 12/15/2015.
 */
@Setter
@Getter
@ToString
public class SeatHold {

    private Integer id;
    private String email;
    private List<Ticket> tickets;
}
