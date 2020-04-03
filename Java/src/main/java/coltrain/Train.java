package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class Train {
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
}
