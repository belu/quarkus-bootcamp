package ch.open.service;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class FactService {

    private final List<FactResult> facts = new CopyOnWriteArrayList<>();

    private final AtomicLong nextId = new AtomicLong();

    public List<FactResult> getFacts() {
        return facts;
    }

    public FactResult addFact(NewFact newFact) {
        var factResult = new FactResult(nextId.incrementAndGet(), LocalDateTime.now(), newFact.statement);
        facts.add(factResult);
        return factResult;
    }

    public Optional<FactResult> getFactById(long id) {
        return facts.stream()
            .filter(f -> f.id == id)
            .findAny();
    }

    public void reset() {
        facts.clear();
    }
}
