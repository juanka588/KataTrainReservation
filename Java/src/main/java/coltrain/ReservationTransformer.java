package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class ReservationTransformer {
    private final Reservation reservation;

    ReservationTransformer(Reservation reservation) {
        this.reservation = reservation;
    }


    public static String toReservationJsonString(Reservation reservation) {
        return "{\"trainId\": \"" + reservation.getTrainId() +
                "\"," +
                "\"bookingReference\": \"" +
                reservation.getBookingReference() +
                "\"," +
                "\"seats\":" +
                seatsToCommaSeparateValues(reservation.getSeats()) +
                "}";
    }

    public static String seatsToCommaSeparateValues(List<Seat> seats) {
        return seats.stream()
                .map(seat -> String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()))
                .collect(joining(", ", "[", "]"));
    }
}
