package coltrain;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import coltrain.api.models.Seat;

import javax.ws.rs.client.*;
import java.util.ArrayList;
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

    public String reserve(String train, int nbSeats) {
        List<Seat> availableSeats = new ArrayList<Seat>();
        String bookingRef;

        // get the train
        String jsonTrain = trainDataService.getTrain(train);

        Train trainInst = new Train(jsonTrain);
        if (trainHasEnoughSeats(nbSeats, trainInst)) {
            int numberOfReserv = 0;

            // find seats to reserve
            for (int index = 0, i = 0; index < trainInst.getSeats().size(); index++) {
                Seat each = (Seat) trainInst.getSeats().toArray()[index];
                if (each.getBookingRef().isEmpty()) {
                    i++;
                    if (i <= nbSeats) {
                        availableSeats.add(each);
                    }
                }
            }

            int reservedSeats = 0;

            if (availableSeats.size() != nbSeats) {
                return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", train);
            } else {
                StringBuilder sb = new StringBuilder("{\"trainId\": \"");
                sb.append(train);
                sb.append("\",");

                Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
                bookingRef = bookingReferenceService.getBookRef(client);

                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                    reservedSeats++;
                }

                sb.append("\"bookingReference\": \"");
                sb.append(bookingRef);
                sb.append("\",");

                if (numberOfReserv == nbSeats) {
                    trainCaching.save(train, trainInst, bookingRef);

                    if (reservedSeats == 0) {
                        System.out.println("Reserved seat(s): " + reservedSeats);
                    }

                    trainDataService.doReservation(train, availableSeats, bookingRef);

                    sb.append("\"seats\":");
                    sb.append(seatsToCommaSeparateValues(availableSeats));
                    sb.append("}");


                    return sb.toString();
                }
            }
        }

        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", train);

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
