package org.ticketservice.util;

/**
 * Created by Durga on 12/15/2015.
 */
public enum TicketLevelEnum {
    ORCHESTRA(1), MAIN(2), BALCONY1(3), BALCONY2(4);

    private final Integer level;

    TicketLevelEnum(Integer level){
        this.level = level;
    }

    public Integer getLevel() {
        return this.level;
    }

    @Override
    public String toString(){
        return String.valueOf(this.level);
    }
}
