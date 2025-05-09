package org.acme.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.dto.UserMessage;
import org.acme.kafka.UserProducer;
import org.acme.service.DeadLetterService;

@Path("/webhook")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WebhookResource {
    @Inject
    UserProducer userProducer;
    @Inject
    DeadLetterService deadLetterService;

    @POST
    @Path("/user")
    public Response userWebhook(UserMessage user) throws JsonProcessingException {
        userProducer.sendUserToKafka(user);
        return Response.ok().build();
    }

    @POST
    @Path("/retry-dead-letter-user")
    public Response userWebhook() throws JsonProcessingException {
        deadLetterService.retryAll();
        return Response.ok().build();
    }
}
