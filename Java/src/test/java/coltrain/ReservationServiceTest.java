package coltrain;

import coltrain.domain.*;
import coltrain.fake.FakeBookingReferenceService;
import coltrain.fake.FakeTrainDataService;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.*;
import static org.junit.Assert.assertEquals;

public class ReservationServiceTest {

    public static final String BOOKING_REFERENCE = "bookRef";
    public static final String NO_BOOKING_REFERENCE = "";
    public static final String TRAIN_ID = "express_2000";
    public static final Pattern SEAT_PATTERN = Pattern.compile("(\\d+)([A-Z])");

    @Test
    public void reserve_givenZeroSeats_itShouldReturnNoSeats() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.EMPTY_TRAIN_WITH_TWO_SEATS);
        final ReservationService sut = new ReservationService(bookingReferenceService, trainDataService);
        final String trainId = "express_2000";
        final int nbSeats = 0;

        final Reservation reservation = sut.reserve(trainId, nbSeats);

        assertEquals(new Reservation(BOOKING_REFERENCE, trainId, Collections.emptyList()), reservation);
    }

    @Test
    public void reserve_givenOneSeat_itShouldReturnOneSeat() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(TrainTopology.EMPTY_TRAIN_WITH_TWO_SEATS);
        final ReservationService sut = new ReservationService(bookingReferenceService, trainDataService);
        final int nbSeats = 1;

        final Reservation reservation = sut.reserve(TRAIN_ID, nbSeats);

        assertEquals(new Reservation(BOOKING_REFERENCE, TRAIN_ID, seats("1A")), reservation);
    }

    private List<Seat> seats(String ... seats){
        return Arrays.stream(seats)
                .map(SEAT_PATTERN::matcher)
                .peek(Matcher::find)
                .map(m -> new Seat(Integer.parseInt(m.group(1)), m.group(2)))
                .map(s -> {
                    s.setBookingRef(BOOKING_REFERENCE); return s;
                })
                .collect(toList());
    }

    @Test
    public void reserve_givenMoreThan70percentOfSeatsToBook_itShouldReturnNoSeats() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(
                TrainTopology.TWO_SEATS_ONE_TAKEN);
        final ReservationService sut = new ReservationService(bookingReferenceService, trainDataService);
        final int nbSeats = 1;

        final Reservation reservation = sut.reserve(TRAIN_ID, nbSeats);

        assertEquals(Reservation.failedReservation(TRAIN_ID), reservation);
    }

    @Test
    public void reserve_givenSeveralSeats_theyShouldBeInTheSameCoach() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(
                TrainTopology.SIX_SEATS_ONE_TAKEN_THREE_COACHES);
        final ReservationService sut = new ReservationService(bookingReferenceService, trainDataService);
        final int nbSeats = 2;

        final Reservation reservation = sut.reserve(TRAIN_ID, nbSeats);
        assertEquals(new Reservation(BOOKING_REFERENCE, TRAIN_ID, seats("3B", "4B")), reservation);
    }

    @Test
    public void reserve_givenOneSeatBooked_itShouldReturnASeat() {
        final BookingReferenceService bookingReferenceService = new FakeBookingReferenceService();
        final TrainDataService trainDataService = new FakeTrainDataService(
                TrainTopology.THREE_SEATS_ONE_TAKEN);
        final ReservationService sut = new ReservationService(bookingReferenceService, trainDataService);
        final int nbSeats = 1;

        final Reservation reservation = sut.reserve(TRAIN_ID, nbSeats);
        assertEquals(new Reservation(BOOKING_REFERENCE, TRAIN_ID, seats("2A")), reservation);
    }
}
