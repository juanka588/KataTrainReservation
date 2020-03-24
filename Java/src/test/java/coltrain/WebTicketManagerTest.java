package coltrain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WebTicketManagerTest {

    public static final String BOOKING_REFERENCE = "bookRef";
    public static final String NO_BOOKING_REFERENCE = "";

    @Test
    public void reserve_givenZeroSeats_itShouldReturnNoSeats() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.EMPTY_TRAIN_WITH_TWO_SEATS);
        final WebTicketManager sut = new WebTicketManager(bookingReferenceService, trainDataService);
        final String train = "express_2000";
        final int nbSeats = 0;

        final String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"" + BOOKING_REFERENCE + "\",\"seats\":[]}", reservation);
    }

    @Test
    public void reserve_givenOneSeat_itShouldReturnOneSeat() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.EMPTY_TRAIN_WITH_TWO_SEATS);
        final WebTicketManager sut = new WebTicketManager(bookingReferenceService, trainDataService);
        final String train = "express_2000";
        final int nbSeats = 1;

        final String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\",\"bookingReference\": \"" + BOOKING_REFERENCE + "\",\"seats\":[\"1A\"]}", reservation);
    }

    @Test
    public void reserve_givenMoreThan70percentOfSeatsToBook_itShouldReturnNoSeats() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(
                TrainTopology.TWO_SEATS_ONE_TAKEN);
        final WebTicketManager sut = new WebTicketManager(bookingReferenceService, trainDataService);
        final String train = "express_2000";
        final int nbSeats = 1;

        final String reservation = sut.reserve(train, nbSeats);
        assertEquals("{\"trainId\": \"express_2000\", \"bookingReference\": \"" + NO_BOOKING_REFERENCE + "\", \"seats\":[]}", reservation);
    }
}
