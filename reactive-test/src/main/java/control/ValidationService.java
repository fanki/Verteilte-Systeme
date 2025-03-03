package control;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.mutiny.Multi;
import org.jboss.logging.Logger;

import messaging.ValidationRequest;
import messaging.ValidationResponse;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ValidationService {

    private static final Logger Log = Logger.getLogger(ValidationService.class);

    @Incoming("validation-request")
    @Outgoing("validation-response")
    @Broadcast
    public Multi<ValidationResponse> validateTextMessages(Multi<ValidationRequest> requests) {
        return requests
                .onItem().transform(request -> {
                    boolean valid = !request.text().contains("hftm sucks");
                    Log.info("Validating: " + request.text() + " -> " + valid);
                    return new ValidationResponse(request.id(), valid);
                });
    }
}
