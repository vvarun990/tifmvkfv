package org.ticketservice.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Durga on 12/14/2015.
 */
@ApplicationPath(value = "/ticket")
public class TicketServiceJerseyConfig extends ResourceConfig{

    public TicketServiceJerseyConfig() {
        packages(true, "org.ticketservice.controller");
    }
}
