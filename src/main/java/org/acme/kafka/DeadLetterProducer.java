package org.acme.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.UserMessage;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.kafka.Record;

import java.util.UUID;

@ApplicationScoped
public class DeadLetterProducer {
    @Inject
    @Channel("users-out")
    Emitter<Record<UUID, String>> emitter;
    @Inject
    ObjectMapper objectMapper;

    public void sendUserToKafka(UserMessage user) throws JsonProcessingException {
        UUID uuid = UUID.randomUUID();
        String json = objectMapper.writeValueAsString(user);
        emitter.send(Record.of(uuid, json));
    }
}
