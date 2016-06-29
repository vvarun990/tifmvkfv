package org.ticketservice.api;

import lombok.Data;

/**
 * Created by Durga on 12/15/2015.
 * Sample json :
    {
     "id":14212,
     "email":"aa",
     "minLevel":1,
     "maxLevel":2,
     "numSeats":3
     }
 *
 *
 */
@Data
public class JSeatHold {

    private Integer id;
    private String email;
    private Integer minLevel;
    private Integer maxLevel;
    private Integer numSeats;

   /* public static void main(String[] args) throws IOException {
        JSeatHold seatHold = new JSeatHold();
        seatHold.setEmail("aa");
        seatHold.setMaxLevel(2);
        seatHold.setMinLevel(1);
        seatHold.setNumSeats(3);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(System.out, seatHold);
    }*/
}
