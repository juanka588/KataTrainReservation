package coltrain.domain;

import java.util.List;

public interface TrainDataService {

    Train getTrain(String trainId);

    void doReservation(String train, List<Seat> availableSeats, String bookingRef);
}
