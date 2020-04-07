package coltrain;

public class FakeBookingReferenceService implements BookingReferenceService {


    @Override
    public String getBookingReference() {
        return "bookRef";
    }
}
