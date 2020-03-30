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

    public static final String SIX_SEATS_ONE_TAKEN_THREE_COACHES = "{\"seats\": {" +
            "\"1A\": {\"booking_reference\": \"bookref\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
            "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
            "\"1B\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"B\"}," +
            "\"2B\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"B\"}," +
            "\"1C\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"C\"}," +
            "\"2C\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"C\"}}" +
            "}";
}