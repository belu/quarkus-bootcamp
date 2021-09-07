package ch.open.dto;

import ch.open.repository.Fact;

import java.time.LocalDateTime;
import java.util.Objects;

public class FactResult {

    public long id;
    public LocalDateTime timestamp;
    public String statement;

    private FactResult() {
        // Jackson
    }

    public FactResult(long id, LocalDateTime timestamp, String statement) {
        this.id = id;
        this.timestamp = timestamp;
        this.statement = statement;
    }

    public static FactResult from(Fact fact) {
        return new FactResult(fact.id, fact.timestamp, fact.statement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FactResult that = (FactResult) o;
        return id == that.id && Objects.equals(timestamp, that.timestamp) && Objects.equals(statement, that.statement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, statement);
    }

    @Override
    public String toString() {
        return "FactResult{" +
            "id=" + id +
            ", timestamp=" + timestamp +
            ", statement='" + statement + '\'' +
            '}';
    }
}
