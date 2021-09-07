package ch.open.messaging;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

import static ch.open.messaging.NewFactConsumer.FACTS_IN;

public class KafkaTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {

    @Override
    public Map<String, String> start() {
        Map<String, String> env = new HashMap<>();
        Map<String, String> props1 = InMemoryConnector.switchIncomingChannelsToInMemory(FACTS_IN);
        env.putAll(props1);
        return env;
    }

    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}