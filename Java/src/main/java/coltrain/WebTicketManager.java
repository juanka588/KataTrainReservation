package coltrain;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import coltrain.api.models.Seat;

import javax.ws.rs.client.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class WebTicketManager {

    public static final String URI_BOOKING_REFERENCE = "http://localhost:8282";
    public static final String URI_TRAIN_DATA = "http://localhost:8181";
    private final BookingReferenceService bookingReferenceService;
    private final TrainDataService trainDataService;
    private TrainCaching trainCaching;

    public WebTicketManager() {
        this(new BookingReferenceServiceRest(URI_BOOKING_REFERENCE), new TrainDataServiceImpl(URI_TRAIN_DATA));
    }

    public WebTicketManager(BookingReferenceService bookingReferenceService, TrainDataService trainDataService) {
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();

        this.bookingReferenceService = bookingReferenceService;
        this.trainDataService = trainDataService;
    }

    public String reserve(String train, int requestedSeats) {
        List<Seat> reservedSeats = new ArrayList<Seat>();
        String bookingRef;

        // get the train
        String jsonTrain = trainDataService.getTrain(train);

        Train trainInst = new Train(jsonTrain);
        if (trainHasEnoughSeats(requestedSeats, trainInst)) {

            // find seats to reserve
            final List<Seat> seats = trainInst.getSeats();
            int numberOfSeatsAlreadyBooked = 0;
            for (Seat seat : seats) {
                if (seat.getBookingRef().isEmpty() && numberOfSeatsAlreadyBooked < requestedSeats) {
                    reservedSeats.add(seat);
                    numberOfSeatsAlreadyBooked++;
                }
            }

            Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
            bookingRef = bookingReferenceService.getBookRef(client);

            for (Seat availableSeat : reservedSeats) {
                availableSeat.setBookingRef(bookingRef);
            }

            trainCaching.save(train, trainInst, bookingRef);
            trainDataService.doReservation(train, reservedSeats, bookingRef);
            return toReservationJsonString(train, reservedSeats, bookingRef);
        }

        return toReservationJsonString(train, Collections.emptyList(), "");

    }

    public String toReservationJsonString(String train, List<Seat> availableSeats, String bookingRef) {
        return "{\"trainId\": \"" + train +
                "\"," +
                "\"bookingReference\": \"" +
                bookingRef +
                "\"," +
                "\"seats\":" +
                seatsToCommaSeparateValues(availableSeats) +
                "}";
    }

    public boolean trainHasEnoughSeats(int seats, Train trainInst) {
        return (trainInst.getReservedSeats() + seats) <= Math.floor(ThreasholdManager.getMaxRes() * trainInst.getMaxSeat());
    }

    private String seatsToCommaSeparateValues(List<Seat> seats) {
        return seats.stream()
                .map(seat -> String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()))
                .collect(joining(", ", "[", "]"));
    }

}
