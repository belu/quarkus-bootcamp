package ch.open.service;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.repository.Fact;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FactService {

    public List<FactResult> getFacts() {
        return Fact.<Fact>streamAll()
            .map(FactResult::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public FactResult addFact(NewFact newFact) {
        var fact = new Fact();
        fact.timestamp = LocalDateTime.now();
        fact.statement = newFact.statement;
        fact.persist();

        return FactResult.from(fact);
    }

    public Optional<FactResult> getFactById(long id) {
        return Fact.<Fact>findByIdOptional(id)
            .map(FactResult::from);
    }

    @Transactional
    public void reset() {
        Fact.deleteAll();
    }
}
