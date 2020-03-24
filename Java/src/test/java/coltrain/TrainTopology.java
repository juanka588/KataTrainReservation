package coltrain;

public class TrainTopology {
    public static final String TWO_SEATS_ONE_TAKEN = "{\"seats\": {" +
            "\"1A\": {\"booking_reference\": \"bookRef\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
            "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}}" +
            "}";

    public static final String EMPTY_TRAIN_WITH_TWO_SEATS = "{\"seats\": {" +
            "\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
            "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}}" +
            "}";
}