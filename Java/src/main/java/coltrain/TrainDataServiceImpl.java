package coltrain;

import coltrain.api.models.Seat;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrainDataServiceImpl implements TrainDataService {
    final String uri;

    public TrainDataServiceImpl(String uri) {
        this.uri = uri;
    }

    private String buildPostContent(String trainId, String bookingRef, List<Seat> availableSeats) {
        StringBuilder seats = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat s : availableSeats) {
            if (!firstTime) {
                seats.append(", ");
            } else {
                firstTime = false;
            }

            seats.append(String.format("\"%s%s\"", s.getSeatNumber(), s.getCoachName()));
        }

        seats.append("]");


        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"%s\", \"seats\":%s}",
                trainId,
                bookingRef,
                seats.toString());

    }

    @Override
    public Train getTrain(String trainId) {
        Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget webTarget = client.target(uri).path("data_for_train/" + trainId);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return adaptTrainTopology(response.readEntity(String.class));
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
        Client client;
        client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        WebTarget uri = client.target(this.uri).path("reserve");
        Invocation.Builder request = uri.request(MediaType.APPLICATION_JSON);


        // HTTP POST
        Response post = request.post(Entity.entity(buildPostContent(train, bookingRef, availableSeats), MediaType.APPLICATION_JSON));

        assert post.getStatus() == Response.Status.OK.getStatusCode();
    }

    // visible for test
    public static Train adaptTrainTopology(String trainTopology) {
        final Set<Map.Entry<String, JsonValue>> seatsData = Json.createReader(new StringReader(trainTopology))
                .readObject()
                .getJsonObject("seats")
                .entrySet();

        final List<Seat> seats = new ArrayList<>();
        for (Map.Entry<String, JsonValue> seatData : seatsData) {
            final JsonObject seatJson = seatData.getValue().asJsonObject();
            final Seat seat = new Seat(Integer.parseInt(seatJson.getString("seat_number")), seatJson.getString("coach"));
            seats.add(seat);

            if(!seatJson.getString("booking_reference").isEmpty()) {
                seat.setBookingRef(seatJson.getString("booking_reference"));
            }
        }
        return new Train(seats);
    }
}
