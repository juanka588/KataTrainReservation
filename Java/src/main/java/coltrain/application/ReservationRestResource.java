package coltrain.application;

import coltrain.Context;
import coltrain.domain.Reservation;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationRestResource {


    @POST
    public String post(ReservationRequestDTO reservationRequest) {
        Reservation reserve = Context.getReservationService().reserve(reservationRequest.getTrainId(), reservationRequest.getNumberOfSeats());
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