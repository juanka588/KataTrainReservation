package coltrain.api.models;

import java.util.Objects;

public class Seat {
    private final String coach;
    private final int seatNumber;
    private String bookingRef = "";

    public Seat(int seatNumber, String coach) {
        this.coach = coach;
        this.seatNumber = seatNumber;
    }

    public void setBookingRef(final String bookingRef) {
        this.bookingRef = bookingRef;
    }

    public int getSeatNumber() {
        return this.seatNumber;
    }

    public String getCoachName() {
        return this.coach;
    }

    public boolean isBooked() {
        return !this.bookingRef.isEmpty();
    }

    public boolean isAvailable() {
        return this.bookingRef.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return seatNumber == seat.seatNumber &&
                Objects.equals(coach, seat.coach) &&
                Objects.equals(bookingRef, seat.bookingRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coach, seatNumber, bookingRef);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "coach='" + coach + '\'' +
                ", seatNumber=" + seatNumber +
                ", bookingRef='" + bookingRef + '\'' +
                '}';
    }
}