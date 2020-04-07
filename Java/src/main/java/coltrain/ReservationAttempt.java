package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class ReservationAttempt {

    private List<Seat> availableSeats;

    public ReservationAttempt(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }
}
