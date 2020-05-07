package coltrain.domain;

public class ReservationService {

    private final BookingReferenceService bookingReferenceService;
    private final TrainDataService trainDataService;



    public ReservationService(BookingReferenceService bookingReferenceService, TrainDataService trainDataService) {
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
