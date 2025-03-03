package org.acme.blog.messaging;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ValidationResponseDeserializer extends ObjectMapperDeserializer<ValidationResponse> {
    public ValidationResponseDeserializer() {
        super(ValidationResponse.class);
    }
}
