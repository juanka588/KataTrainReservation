package coltrain;

import static java.util.stream.Collectors.joining;

public class WebTicketManager {

    public static final String URI_BOOKING_REFERENCE = "http://localhost:8282";
    public static final String URI_TRAIN_DATA = "http://localhost:8181";
    private final BookingReferenceService bookingReferenceService;
    private final TrainDataService trainDataService;

    public WebTicketManager() {
        this(new BookingReferenceServiceRest(URI_BOOKING_REFERENCE), new TrainDataServiceImpl(URI_TRAIN_DATA));
    }

    public WebTicketManager(BookingReferenceService bookingReferenceService, TrainDataService trainDataService) {
        this.bookingReferenceService = bookingReferenceService;
        this.trainDataService = trainDataService;
    }

    public Reservation reserve(String trainId, int requestedSeats) {
        final Train train = trainDataService.getTrain(trainId);
        if (train.doNotExceedCapacityThreshold(requestedSeats)) {
            final ReservationAttempt reservationAttempt = train.buildReservationAttempt(requestedSeats);

            if (reservationAttempt.matchesRequest()) {
                final String bookingRef = bookingReferenceService.getBookingReference();
                reservationAttempt.assignBookingReference(bookingRef);

                trainDataService.doReservation(trainId, reservationAttempt.getAvailableSeats(), bookingRef);
                return reservationAttempt.confirm(trainId);
            }
        }

        return Reservation.failedReservation(trainId);

    }

}
