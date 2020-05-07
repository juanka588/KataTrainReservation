package coltrain.fake;

import coltrain.domain.Seat;
import coltrain.domain.Train;
import coltrain.domain.TrainDataService;
import coltrain.infra.TrainDataServiceImpl;

import java.util.List;

public class FakeTrainDataService implements TrainDataService {

    private final String topology;

    public FakeTrainDataService(String topology) {
        this.topology = topology;
    }

    @Override
    public Train getTrain(String train) {
        return TrainDataServiceImpl.adaptTrainTopology(topology);
    }

    @Override
    public void doReservation(String train, List<Seat> availableSeats, String bookingRef) {
    }
}
