package ch.open.messaging;

import ch.open.dto.NewFact;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySink;
import org.junit.jupiter.api.Test;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import static ch.open.messaging.NewFactProducer.FACTS_OUT;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@QuarkusTest
class NewFactProducerTest {

    @Inject
    NewFactProducer newFactProducer;

    @Inject
    @Any
    InMemoryConnector connector;

    @Test
    void generate_oneFact_oneNewFactInQueue() {
        // given
        InMemorySink<NewFact> sink = connector.sink(FACTS_OUT);
        int totalFactsReceived = sink.received().size();

        // when
        newFactProducer.produceNewFact();

        // then
        await().atMost(3, SECONDS)
            .until(() -> totalFactsReceived + 1 == sink.received().size());
    }
}
