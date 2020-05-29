package coltrain;

class FakeBookingReferenceService implements BookingReferenceService {
    @Override
    public String getBookRef() {
        return "bookingReference";
    }
}
