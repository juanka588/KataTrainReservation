package coltrain;

import coltrain.api.models.Seat;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class WebTicketManager {

    public static final String URI_BOOKING_REFERENCE = "http://localhost:8282";
    public static final String URI_TRAIN_DATA = "http://localhost:8181";
    private final BookingReferenceService bookingReferenceService;
    private final TrainDataService trainDataService;
    private TrainCaching trainCaching;

    public WebTicketManager() {
        this(new BookingReferenceServiceRest(URI_BOOKING_REFERENCE), new TrainDataServiceImpl(URI_TRAIN_DATA));
    }

    public WebTicketManager(BookingReferenceService bookingReferenceService, TrainDataService trainDataService) {
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();

        this.bookingReferenceService = bookingReferenceService;
        this.trainDataService = trainDataService;
    }

    public String reserve(String trainId, int requestedSeats) {
        final Train train = trainDataService.getTrain(trainId);
        if (train.doNotExceedCapacityThreshold(requestedSeats)) {
            // find seats to reserve
            final List<Seat> availableSeats = train.findAvailableSeats(requestedSeats);
            String bookingRef = bookingReferenceService.getBookingReference();

            for (Seat availableSeat : availableSeats) {
                availableSeat.setBookingRef(bookingRef);
            }

            trainCaching.save(trainId, train, bookingRef);
            trainDataService.doReservation(trainId, availableSeats, bookingRef);
            return toReservationJsonString(trainId, availableSeats, bookingRef);
        }

        return toReservationJsonString(trainId, Collections.emptyList(), "");

    }

    public String toReservationJsonString(String train, List<Seat> availableSeats, String bookingRef) {
        return "{\"trainId\": \"" + train +
                "\"," +
                "\"bookingReference\": \"" +
                bookingRef +
                "\"," +
                "\"seats\":" +
                seatsToCommaSeparateValues(availableSeats) +
                "}";
    }

    private String seatsToCommaSeparateValues(List<Seat> seats) {
        return seats.stream()
                .map(seat -> String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()))
                .collect(joining(", ", "[", "]"));
    }

}
