package coltrain;

import coltrain.domain.ReservationService;
import coltrain.infra.BookingReferenceServiceRest;
import coltrain.infra.TrainDataServiceImpl;

public class Context {
    private static ReservationService instance;
    private static final String URI_BOOKING_REFERENCE = "http://localhost:8282";
    private static final String URI_TRAIN_DATA = "http://localhost:8181";

    public static ReservationService getReservationService() {
        instance = instance != null ? instance : new ReservationService(new BookingReferenceServiceRest(URI_BOOKING_REFERENCE), new TrainDataServiceImpl(URI_TRAIN_DATA));
        return instance;
    }
}
