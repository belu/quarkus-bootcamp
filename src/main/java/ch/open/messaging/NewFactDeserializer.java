package ch.open.messaging;

import ch.open.dto.NewFact;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class NewFactDeserializer extends ObjectMapperDeserializer<NewFact> {

    public NewFactDeserializer() {
        super(NewFact.class);
    }
}
