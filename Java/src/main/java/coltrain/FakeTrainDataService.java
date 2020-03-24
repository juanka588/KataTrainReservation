package coltrain;

import coltrain.api.models.Seat;

import java.util.List;

public class FakeTrainDataService implements TrainDataService {

    private final String topology;

    public FakeTrainDataService(String topology) {
        this.topology = topology;
    }

    @Override
    public String getTrain(String train) {
        return this.topology;
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
    }
}
