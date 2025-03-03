import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import io.smallrye.mutiny.Multi;

@ApplicationScoped
public class MessagingTesting {

    @Incoming("source") // Empfängt Nachrichten aus Kafka
    @Outgoing("processed-a")
    public String toUpperCase(String payload) {
        return payload.toUpperCase();
    }

    @Incoming("processed-a")
    @Outgoing("processed-b")
    public String correctStrings(String payload) {
        if ("BÖSE".equals(payload)) {
            return "LIEBE";
        }
        return payload;
    }

    @Incoming("processed-b")
    @Outgoing("processed-c")
    public Multi<String> filter(Multi<String> input) {
        return input.filter(item -> item.length() > 5);
    }

    @Incoming("processed-c")
    public void sink(String word) {
        System.out.println(">> " + word);
    }
}
