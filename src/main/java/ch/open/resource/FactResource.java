package ch.open.resource;

import ch.open.dto.FactResult;
import ch.open.dto.NewFact;
import ch.open.service.FactService;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.MeterRegistry;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/facts")
public class FactResource {

    @Inject
    FactService factService;

    @Inject
    MeterRegistry meterRegistry;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FactResult> getFacts(@DefaultValue("3") @QueryParam("limit") int limit) {
        meterRegistry.counter("endpoint_get_facts").increment();

        return factService.getFacts(limit);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public FactResult addFact(NewFact newFact) {
        meterRegistry.counter("endpoint_add_fact").increment();

        return factService.addFact(newFact);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Counted
    public Response getFactById(@PathParam("id") long id) {
        return factService.getFactById(id)
            .map(f -> Response.ok(f).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
}
