package com.bugapocalypse.srilankarailwaydigital;

import com.bugapocalypse.srilankarailwaydigital.data.Ticket;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;


public class TicketTest {

    @Test
    public void calculatePrice () {
        double result = Ticket.calculatePrice(2, 250);
        assertEquals(500.00, result);
    }
}
