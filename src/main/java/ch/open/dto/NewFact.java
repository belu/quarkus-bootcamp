package ch.open.dto;

public class NewFact {

    public String statement;

    private NewFact() {
        // Jackson
    }

    public NewFact(String statement) {
        this.statement = statement;
    }

    @Override
    public String toString() {
        return "NewFact{" +
            "statement='" + statement + '\'' +
            '}';
    }
}
