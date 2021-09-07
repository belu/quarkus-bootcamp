package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Path("/facts")
public class FactResource {

    private final List<FactResult> facts = new CopyOnWriteArrayList<>();

    private final AtomicLong nextId = new AtomicLong();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FactResult> getFacts() {
        return facts;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public FactResult addFact(NewFact newFact) {
        var factResult = new FactResult(nextId.incrementAndGet(), LocalDateTime.now(), newFact.statement);
        facts.add(factResult);
        return factResult;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFactById(@PathParam("id") long id) {
        return facts.stream()
            .filter(f -> f.id == id)
            .findAny()
            .map(f -> Response.ok(f).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
