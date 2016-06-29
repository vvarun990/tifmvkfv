package org.ticketservice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.ticketservice.api.JSeatHold;
import org.ticketservice.api.TicketServiceResponse;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TicketServiceApplication.class)
@WebIntegrationTest(value = "server.address=localhost", randomPort = true)
public class TicketServiceApplicationTests {

    private RestTemplate restTemplate = null;

    @Value("${local.server.port}")  //boot injects the port used for bootstart the application.
    private int port;

    private String baseURL = null;

    @Before
    public void setUp() {
        restTemplate = new TestRestTemplate();
        baseURL = "http://localhost:" + port + "/ticket";
    }

    @Test
    public void testCheckAvailability() {
        TicketServiceResponse response = restTemplate.getForObject(baseURL + "/level/1/available", TicketServiceResponse.class);
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertEquals(10, response.getAvailableSeats());
    }

    @Test
    public void testFindAndHoldSeat() {
        TicketServiceResponse response = restTemplate.postForObject(baseURL + "/hold", jSeatHold("mkambam@gmail.com", 4, 1, 3, null), TicketServiceResponse.class);
        assertEquals(response.getStatusCode(), Response.Status.ACCEPTED.getStatusCode());
        assertNotNull(response.getJSeatHold());
        //check the availability on level1, it should be 6 since 4seats has been hold.
        response = restTemplate.getForObject(baseURL + "/level/1/available", TicketServiceResponse.class);
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertEquals(6, response.getAvailableSeats());
    }

    @Test
    public void testReserve() {
        Integer holdId;
        TicketServiceResponse response = restTemplate.postForObject(baseURL + "/hold", jSeatHold("mkambam@gmail.com", 4, 2, 3, null), TicketServiceResponse.class);
        assertEquals(response.getStatusCode(), Response.Status.ACCEPTED.getStatusCode());
        assertNotNull(response.getJSeatHold());

        holdId = response.getJSeatHold().getId();

        //check the availability on level1, it should be 6 since 4seats has been hold.
        response = restTemplate.getForObject(baseURL + "/level/2/available", TicketServiceResponse.class);
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertEquals(6, response.getAvailableSeats());

        response = restTemplate.postForObject(baseURL + "/reserve", jSeatHold("mkambam@gmail.com", null, null, null, holdId), TicketServiceResponse.class);
        assertEquals(response.getStatusCode(), Response.Status.OK.getStatusCode());
        assertEquals(response.getMessage(), "Your tickets has been confirmed.");
    }

    private JSeatHold jSeatHold(String email, Integer numSeats, Integer minLevel, Integer maxLevel, Integer id) {
        JSeatHold jSeatHold = new JSeatHold();
        jSeatHold.setEmail(email);
        jSeatHold.setMinLevel(minLevel);
        jSeatHold.setMaxLevel(maxLevel);
        jSeatHold.setNumSeats(numSeats);
        jSeatHold.setId(id);
        return jSeatHold;
    }
}
