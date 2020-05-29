package coltrain;

import coltrain.api.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class WebTicketManager {

    static String uriBookingReferenceService = "http://localhost:8282";
    static String uriTrainDataService = "http://localhost:8181";
    private final TrainDataService trainDataService;
    private TrainCaching trainCaching;
    private BookingReferenceService bookingReferenceService;

    public WebTicketManager(TrainDataService trainDataService, BookingReferenceService bookingReferenceService) {
        this.bookingReferenceService = bookingReferenceService;
        this.trainCaching = new TrainCaching();
        this.trainCaching.clear();
        this.trainDataService = trainDataService;
    }

    public WebTicketManager() {
        this(new TrainDataServiceImpl(), new BookingReferenceServiceImpl());
    }

    public String reserve(String train, int seats) {
        List<Seat> availableSeats = new ArrayList<Seat>();
        int count = 0;
        String result = "";
        String bookingRef;

        // get the train
        String jsonTrain = trainDataService.getTrain(train);

        result = jsonTrain;

        Train trainInst = new Train(jsonTrain);
        if ((trainInst.getReservedSeats() + seats) <= Math.floor(ThreasholdManager.getMaxRes() * trainInst.getMaxSeat())) {
            int numberOfReserv = 0;

            // find seats to reserve
            for (int index = 0, i = 0; index < trainInst.getSeats().size(); index++) {
                Seat each = (Seat) trainInst.getSeats().toArray()[index];
                if (each.getBookingRef() == "") {
                    i++;
                    if (i <= seats) {
                        availableSeats.add(each);
                    }
                }
            }

            for (Seat a : availableSeats) {
                count++;
            }

            int reservedSeats = 0;

            if (count != seats) {
                return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", train);
            } else {
                StringBuilder sb = new StringBuilder("{\"trainId\": \"");
                sb.append(train);
                sb.append("\",");

                bookingRef = bookingReferenceService.getBookRef();

                for (Seat availableSeat : availableSeats) {
                    availableSeat.setBookingRef(bookingRef);
                    numberOfReserv++;
                    reservedSeats++;
                }

                sb.append("\"bookingReference\": \"");
                sb.append(bookingRef);
                sb.append("\",");

                if (numberOfReserv == seats) {
                    trainCaching.save(train, trainInst, bookingRef);

                    trainDataService.doReservation(train, availableSeats, bookingRef);

                    sb.append("\"seats\":");
                    sb.append(dumpSeats(availableSeats));
                    sb.append("}");


                    return sb.toString();
                }
            }
        }

        return String.format("{\"trainId\": \"%s\", \"bookingReference\": \"\", \"seats\":[]}", train);

    }

    private String dumpSeats(List<Seat> seats) {
        StringBuilder sb = new StringBuilder("[");

        boolean firstTime = true;
        for (Seat seat : seats) {
            if (!firstTime) {
                sb.append(", ");
            } else {
                firstTime = false;
            }

            sb.append(String.format("\"%s%s\"", seat.getSeatNumber(), seat.getCoachName()));
        }

        sb.append("]");

        return sb.toString();
    }


}
