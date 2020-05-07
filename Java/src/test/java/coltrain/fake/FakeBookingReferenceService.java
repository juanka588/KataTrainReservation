package coltrain.fake;

import coltrain.domain.BookingReferenceService;

public class FakeBookingReferenceService implements BookingReferenceService {


    @Override
    public String getBookingReference() {
        return "bookRef";
    }
}
