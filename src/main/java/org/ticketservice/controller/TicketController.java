package org.ticketservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.ticketservice.api.JSeatHold;
import org.ticketservice.api.TicketServiceResponse;
import org.ticketservice.domain.SeatHold;
import org.ticketservice.domain.Ticket;
import org.ticketservice.service.impl.TicketServiceImpl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by Durga on 12/14/2015.
 */
@Path(value = "/")
@Produces(MediaType.APPLICATION_JSON)
public class TicketController {

    @Autowired
    private TicketServiceImpl ticketService;


    @GET
    @Path(value = "status")
    public Response bookingStatus() {
        try {
            Map<String, List<Ticket>> currentStatus = ticketService.currentStatus();
            TicketServiceResponse ticketServiceResponse = TicketServiceResponse.builder()
                    .withStatusCode(Response.Status.OK.getStatusCode()).withTickets(currentStatus).build();
            return Response.status(Response.Status.OK).entity(ticketServiceResponse).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "level/{level}/available")
    public Response numSeatsAvailable(@PathParam("level") Integer level) {
        try {
            int available = ticketService.numSeatsAvailable(Optional.of(level));
            TicketServiceResponse response = TicketServiceResponse.builder().withAvailableSeats(available)
                    .withStatusCode(Response.Status.OK.getStatusCode()).build();
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path(value = "hold")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response findAndHold(JSeatHold jSeatHold) {
        try {
            SeatHold seatHold = ticketService.findAndHoldSeats(jSeatHold.getNumSeats(),
                    Optional.of(jSeatHold.getMinLevel()), Optional.of(jSeatHold.getMaxLevel()), jSeatHold.getEmail());
            jSeatHold.setId(seatHold.getId());
            TicketServiceResponse response = TicketServiceResponse.builder()
                    .withStatusCode(Response.Status.ACCEPTED.getStatusCode()).withJSeatHold(jSeatHold).build();
            return Response.status(Response.Status.ACCEPTED).entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path(value = "reserve")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response reserveSeat(JSeatHold jSeatHold) {
        try {
            String message = ticketService.reserveSeats(jSeatHold.getId(), jSeatHold.getEmail());
            TicketServiceResponse response = TicketServiceResponse.builder()
                    .withStatusCode(Response.Status.OK.getStatusCode()).withMessage(message).build();
            return Response.status(Response.Status.OK).entity(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
