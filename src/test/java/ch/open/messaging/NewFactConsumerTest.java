package ch.open.messaging;

import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.enterprise.inject.Any;
import javax.inject.Inject;

import static ch.open.messaging.NewFactConsumer.FACTS_IN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@QuarkusTest
@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
class NewFactConsumerTest {

    @Inject
    @Any
    InMemoryConnector connector;

    @InjectMock
    FactService factService;

    @Test
    void addFact() {
        // given
        InMemorySource<NewFact> source = connector.source(FACTS_IN);
        var fact = new NewFact("Kafka Fact");

        // when
        source.send(fact);

        // then
        var captor = ArgumentCaptor.forClass(NewFact.class);
        verify(factService, timeout(3_000)).addFact(captor.capture());

        assertThat(captor.getValue().statement).isEqualTo(fact.statement);

        verifyNoMoreInteractions(factService);
    }
}
