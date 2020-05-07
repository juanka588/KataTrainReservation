package coltrain.infra;

import coltrain.domain.Seat;
import coltrain.domain.Reservation;

import java.util.List;

import static java.util.stream.Collectors.joining;

class ReservationJsonSerializer {

    String toReservationJsonString(Reservation reservation) {
        return "{\"trainId\": \"" + reservation.getTrainId() +
                "\"," +
                "\"bookingReference\": \"" +
                reservation.getBookingReference() +
                "\"," +
                "\"seats\":" +
                seatsToCommaSeparateValues(reservation.getSeats()) +
                "}";
    }

    private String seatsToCommaSeparateValues(List<Seat> seats) {
        return seats.stream()
                .map(seat -> String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()))
                .collect(joining(", ", "[", "]"));
    }
}
