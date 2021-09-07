package ch.open.messaging;

import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class NewFactConsumer {

    public static final String FACTS_IN = "facts-in";

    @Inject
    FactService factService;

    @Incoming(FACTS_IN)
    @Blocking
    public void consume(NewFact newFact) {
        System.out.println("Incoming Kafka message: " + newFact);
        factService.addFact(newFact);
    }
}
