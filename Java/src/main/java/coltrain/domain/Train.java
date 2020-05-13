package coltrain.domain;

import java.util.*;

import static java.util.stream.Collectors.*;

public class Train {
    private static final double CAPACITY_THRESHOLD = 0.70;
    private final Map<String, Coach> coaches;

    public Train(List<Seat> seats) {
        this.coaches = new HashMap<>();
        for (Seat seat : seats) {
            final String coachName = seat.getCoachName();
            if (!this.coaches.containsKey(coachName)) {
                coaches.put(coachName, new Coach());
            }
            coaches.get(coachName).addSeat(seat);
        }
    }

    public List<Seat> getSeats() {
        return this.coaches
                .values()
                .stream()
                .map(Coach::getSeats)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public int getReservedSeats() {
        return Math.toIntExact(getSeats().stream()
                .filter(Seat::isBooked)
                .count());
    }

    public int getMaxSeat() {
        return getSeats().size();
    }

    public boolean doNotExceedCapacityThreshold(int requestedSeats) {
        return (getReservedSeats() + requestedSeats) <= Math.floor(CAPACITY_THRESHOLD * getMaxSeat());
    }

    public ReservationAttempt buildReservationAttempt(final int requestedSeats) {

        for (Coach coach : this.coaches.values()) {
            final ReservationAttempt reservationAttempt = coach.buildReservationAttempt(requestedSeats);
            if (reservationAttempt.matchesRequest()) {
                return reservationAttempt;
            }
        }

        return ReservationAttempt.failed(requestedSeats);
    }

}
