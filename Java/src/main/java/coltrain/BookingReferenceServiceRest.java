package coltrain;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BookingReferenceServiceRest implements BookingReferenceService {

    private final String uri;

    public BookingReferenceServiceRest(String uri) {
        this.uri = uri;
    }

    @Override
    public String getBookingReference() {
        final Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
        final WebTarget webTarget = client.target(uri).path("booking_reference");
        final Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        final Response response = invocationBuilder.get();

        assert response.getStatus() == Response.Status.OK.getStatusCode();

        return response.readEntity(String.class);
    }
}