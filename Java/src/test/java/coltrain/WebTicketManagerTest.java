package coltrain;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class WebTicketManagerTest {

    @Test
    public void reserve_givenZeroSeatsRequested_itShouldReturnNoSeats() {
        // arrange
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_WITH_TWO_SEATS_NOT_RESERVED), new FakeBookingReferenceService());

        // act
        final String reservation = sut.reserve("express2000", 0);

        // assert
        assertEquals("{\"trainId\": \"express2000\",\"bookingReference\": \"bookingReference\",\"seats\":[]}", reservation);
    }

    @Test
    public void reserve_givenTwoSeatsRequested_itShouldReturnTwoSeats() {
        // arrange
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_WITH_SIX_SEATS_ONE_RESERVED), new FakeBookingReferenceService());

        // act
        final String reservation = sut.reserve("express2000", 2);

        // assert
        assertEquals("{\"trainId\": \"express2000\",\"bookingReference\": \"bookingReference\",\"seats\":[\"2A\", \"3A\"]}", reservation);
    }

    @Test
    public void reserve_givenMoreThanThresholdRequested_itShouldReturnNoSeats() {
        // arrange
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_WITH_SIX_SEATS_ONE_RESERVED), new FakeBookingReferenceService());

        // act
        final String reservation = sut.reserve("express2000", 5);

        // assert
        assertEquals("{\"trainId\": \"express2000\", \"bookingReference\": \"\", \"seats\":[]}", reservation);
    }

    @Ignore("ignored because this business rule is bugged")
    @Test
    public void reserve_givenMultipleSeatsRequested_itShouldReturnAllSeatsSameCoach() {
        // arrange
        final WebTicketManager sut = new WebTicketManager(new FakeTrainDataService(TrainTopology.TRAIN_WITH_SIX_SEATS_TWO_COACHES_ONE_SEATS_BOOKED), new FakeBookingReferenceService());

        // act
        final String reservation = sut.reserve("express2000", 3);

        // assert
        assertEquals("{\"trainId\": \"express2000\", \"bookingReference\": \"\", \"seats\":[\"4B\", \"5B\", \"6B\"]}", reservation);
    }
}