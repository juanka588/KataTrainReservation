package coltrain;

import coltrain.api.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class Train {
    private static final double CAPACITY_THRESHOLD = 0.70;
    private final List<Seat> seats;

    public Train(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return this.seats;
    }

    public int getReservedSeats() {
        return Math.toIntExact(seats.stream()
                .filter(Seat::isBooked)
                .count());
    }

    public int getMaxSeat() {
        return this.seats.size();
    }

    public boolean doNotExceedCapacityThreshold(int requestedSeats) {
        return (getReservedSeats() + requestedSeats) <= Math.floor(CAPACITY_THRESHOLD * getMaxSeat());
    }

    public ReservationAttempt buildReservationAttempt(int requestedSeats) {
        final List<Seat> seats = getSeats();
        int numberOfSeatsAlreadyBooked = 0;
        final List<Seat> availableSeats = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getBookingRef().isEmpty() && numberOfSeatsAlreadyBooked < requestedSeats) {
                availableSeats.add(seat);
                numberOfSeatsAlreadyBooked++;
            }
        }
        return new ReservationAttempt(availableSeats);
    }
}
