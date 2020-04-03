package coltrain;

import coltrain.api.models.Seat;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Train {
    private final List<Seat> seats;
    private int maxSeat = 0;

    public Train(final String trainTopol) {
        this.seats = new ArrayList<Seat>();
        Seat e;
        //  sample
        //  {"seats": {"1A": {"booking_reference": "", "seat_number": "1", "coach": "A"},
        //  {"2A": {"booking_reference": "", "seat_number": "2", "coach": "A"}}}

        JsonObject parsed = Json.createReader(new StringReader(trainTopol)).readObject();

        final Set<Map.Entry<String, JsonValue>> allStuffs = parsed.getJsonObject("seats").entrySet();


        int reservedSeats = 0;
        for (Map.Entry<String, JsonValue> stuff : allStuffs) {
            final JsonObject seat = stuff.getValue().asJsonObject();
             e = new Seat(seat.getString("coach"), Integer.parseInt(seat.getString("seat_number")));
            this.seats.add(e);
            if(!seat.getString("booking_reference").isEmpty()){
                reservedSeats++;
            }
            this.maxSeat++;

            if(!seat.getString("booking_reference").isEmpty()) {
                e.setBookingRef(seat.getString("booking_reference"));
            }
        }
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
        return this.maxSeat;
    }
}
