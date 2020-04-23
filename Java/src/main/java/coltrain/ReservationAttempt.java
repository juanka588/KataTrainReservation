package coltrain;

import coltrain.api.models.Seat;

import java.util.Collections;
import java.util.List;

public class ReservationAttempt {

    private List<Seat> availableSeats;
    private int requestedSeats;

    public ReservationAttempt(List<Seat> availableSeats, int requestedSeats) {
        this.availableSeats = availableSeats;
        this.requestedSeats = requestedSeats;
    }

    public static ReservationAttempt failed(int requestedSeats) {
        return new ReservationAttempt(Collections.emptyList(), requestedSeats);
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public boolean matchesRequest() {
        return availableSeats.size() == requestedSeats;
    }

    public void assignBookingReference(String bookingRef) {
        for (Seat availableSeat : availableSeats) {
            availableSeat.setBookingRef(bookingRef);
        }
    }
}
