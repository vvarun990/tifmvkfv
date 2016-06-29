package org.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.ticketservice.jersey.TicketServiceJerseyConfig;

@SpringBootApplication
@ComponentScan(basePackages = {"org.ticketservice"})
public class TicketServiceApplication {

    @Bean
    public TicketServiceJerseyConfig ticketServiceJerseyConfig() {
        return new TicketServiceJerseyConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }
}
