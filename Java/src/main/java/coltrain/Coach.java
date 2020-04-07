package coltrain;

import coltrain.api.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class Coach {
    private final List<Seat> seats;

    public Coach() {
        this.seats= new ArrayList<>();
    }

    public void addSeat(Seat seat) {
        seats.add(seat);
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
