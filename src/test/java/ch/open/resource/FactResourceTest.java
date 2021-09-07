package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(FactResource.class)
class FactResourceTest {

    @Inject
    FactService factService;

    @BeforeEach
    void setUp() {
        factService.reset();
    }

    @Test
    public void getFacts() {
        // given
        addFact("Fact 1");
        addFact("Fact 2");
        addFact("Fact 3");

        // when
        var facts = given()
            .when().get()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(new TypeRef<List<FactResult>>() {
            });

        // then
        assertThat(facts).hasSize(3);

        var actualFacts = facts.stream().map(fact -> fact.statement).collect(Collectors.toUnmodifiableList());
        assertThat(actualFacts).containsExactly("Fact 1", "Fact 2", "Fact 3");
    }

    @Test
    public void addFact() {
        // given
        var newFact = new NewFact("Fact 1");

        // when
        given()
            .body(newFact)
            .contentType(ContentType.JSON)
            .when().post()
            .then()
            .statusCode(HttpStatus.SC_OK);

        // then
        var facts = factService.getFacts();
        assertThat(facts).hasSize(1);

        var actualFacts = facts.stream().map(fact -> fact.statement).collect(Collectors.toUnmodifiableList());
        assertThat(actualFacts).containsExactly("Fact 1");
    }

    @Test
    public void getFactById() {
        // given
        var factResult = addFact("Fact 1");

        // when
        var response = given()
            .when().get(Long.toString(factResult.id))
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .as(FactResult.class);

        // then
        assertThat(response.id).isEqualTo(factResult.id);
        assertThat(response.timestamp).isEqualToIgnoringNanos(factResult.timestamp);
        assertThat(response.statement).isEqualTo(factResult.statement);
    }

    @Test
    public void getFactById_unknownId_notFound() {
        // given
        var unknownId = 42L;

        // when + then
        given()
            .when().get(Long.toString(unknownId))
            .then()
            .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    private FactResult addFact(String statement) {
        return factService.addFact(new NewFact(statement));
    }
}
