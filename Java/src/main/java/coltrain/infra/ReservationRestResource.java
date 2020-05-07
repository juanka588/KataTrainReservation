package coltrain.infra;

import coltrain.domain.Reservation;
import coltrain.domain.ReservationService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationRestResource {
    private static final String URI_BOOKING_REFERENCE = "http://localhost:8282";
    private static final String URI_TRAIN_DATA = "http://localhost:8181";

    @POST
    public String post(ReservationRequestDTO reservationRequest) {
        final ReservationService manager = new ReservationService(new BookingReferenceServiceRest(URI_BOOKING_REFERENCE), new TrainDataServiceImpl(URI_TRAIN_DATA));
        Reservation reserve = manager.reserve(reservationRequest.getTrainId(), reservationRequest.getNumberOfSeats());
        return new ReservationJsonSerializer().toReservationJsonString(reserve);
    }

    public static class ReservationRequestDTO {
        private String trainId;
        private int numberOfSeats;

        public ReservationRequestDTO(String trainId, int numberOfSeats) {
            this.trainId = trainId;
            this.numberOfSeats = numberOfSeats;
        }

        String getTrainId() {
            return this.trainId;
        }

        int getNumberOfSeats() {
            return this.numberOfSeats;
        }
    }
}