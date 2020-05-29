package coltrain;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebTicketManagerTest {

    @Test
    public void reserve_givenZeroSeatsRequested_itShouldReturnNoSeats() {
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(), new FakeBookingReferenceService());
        final String reservation = sut.reserve("express2000", 0);
        assertEquals("{\"trainId\": \"express2000\",\"bookingReference\": \"bookingReference\",\"seats\":[]}",reservation);
    }

}