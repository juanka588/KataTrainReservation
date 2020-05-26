package coltrain;

public class TrainTopology {
    public static final String TRAIN_WITH_TWO_SEATS_NOT_RESERVED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}" +
                    "}}";

    public static final String TRAIN_WITH_SIX_SEATS_ONE_RESERVED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"bookingReference\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
                    "\"3A\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"A\"}," +
                    "\"4A\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"A\"}," +
                    "\"5A\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"A\"}," +
                    "\"6A\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"A\"}" +
                    "}}";

    public static final String TRAIN_WITH_SIX_SEATS_TWO_COACHES_ONE_SEATS_BOOKED =
            "{\"seats\": " +
                    "{\"1A\": {\"booking_reference\": \"bookingReference\", \"seat_number\": \"1\", \"coach\": \"A\"}," +
                    "\"2A\": {\"booking_reference\": \"\", \"seat_number\": \"2\", \"coach\": \"A\"}," +
                    "\"3A\": {\"booking_reference\": \"\", \"seat_number\": \"3\", \"coach\": \"A\"}," +
                    "\"1B\": {\"booking_reference\": \"\", \"seat_number\": \"4\", \"coach\": \"B\"}," +
                    "\"2B\": {\"booking_reference\": \"\", \"seat_number\": \"5\", \"coach\": \"B\"}," +
                    "\"3B\": {\"booking_reference\": \"\", \"seat_number\": \"6\", \"coach\": \"B\"}" +
                    "}}";
}