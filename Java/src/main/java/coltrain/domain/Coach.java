package coltrain.domain;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final List<Seat> seats;

    public Coach() {
        this.seats = new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public ReservationAttempt buildReservationAttempt(int requestedSeats) {
        int numberOfSeatsAlreadyBooked = 0;

        final List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.isAvailable() && numberOfSeatsAlreadyBooked < requestedSeats) {
                availableSeats.add(seat);
                numberOfSeatsAlreadyBooked++;
            }
        }

        return new ReservationAttempt(availableSeats, requestedSeats);
    }
}
