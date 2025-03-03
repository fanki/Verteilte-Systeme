package boundry;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/test")
public class MessagingResource {

    @Inject
    @Channel("source-outgoing") // Verwendet den Outgoing-Channel
    Emitter<String> emitter;

    @GET
    @Path("/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String source(String name) {
        emitter.send(name);
        return "Message sent to Kafka: " + name;
    }
}
