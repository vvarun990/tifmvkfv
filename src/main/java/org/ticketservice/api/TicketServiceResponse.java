package org.ticketservice.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ticketservice.domain.Ticket;

import java.util.List;
import java.util.Map;

/**
 * Created by Durga on 12/14/2015.
 */

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketServiceResponse {

    private int statusCode;
    private int availableSeats;
    private JSeatHold jSeatHold;
    private String message;
    private Map<String, List<Ticket>> tickets;

    public TicketServiceResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.tickets = builder.tickets;
        this.availableSeats = builder.availableSeats;
        this.jSeatHold = builder.jSeatHold;
        this.message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        public TicketServiceResponse build() {
            return new TicketServiceResponse(this);
        }

        private int statusCode;
        private int availableSeats;
        private JSeatHold jSeatHold;
        private String message;
        private Map<String, List<Ticket>> tickets;


        public Builder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withTickets(Map<String, List<Ticket>> tickets) {
            this.tickets = tickets;
            return this;
        }

        public Builder withAvailableSeats(int seats) {
            this.availableSeats = seats;
            return this;
        }

        public Builder withJSeatHold(JSeatHold jSeatHold) {
            this.jSeatHold = jSeatHold;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
