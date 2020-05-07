package coltrain.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Reservation {
    final private String bookingReference;

    final private String trainId;
    final private List<Seat> seats;

    public Reservation(String bookingReference, String trainId, List<Seat> seats) {

        this.bookingReference = bookingReference;
        this.trainId = trainId;
        this.seats = seats;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public String getTrainId() {
        return trainId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public static Reservation failedReservation(String trainId) {

        return new Reservation("", trainId, Collections.emptyList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(bookingReference, that.bookingReference) &&
                Objects.equals(trainId, that.trainId) &&
                Objects.equals(seats, that.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingReference, trainId, seats);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "bookingReference='" + bookingReference + '\'' +
                ", trainId='" + trainId + '\'' +
                ", seats=" + seats +
                '}';
    }
}
