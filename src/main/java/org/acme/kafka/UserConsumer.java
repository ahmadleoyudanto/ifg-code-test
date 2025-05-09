package org.acme.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.dto.UserMessage;
import org.acme.service.UserService;
import org.apache.kafka.common.errors.RetriableException;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class UserConsumer {
    private final org.jboss.logging.Logger logger = Logger.getLogger(UserConsumer.class);

    @Inject
    UserService userService;

    @Incoming("users-in")
    @Retry(delay = 2, maxRetries = 3)
    @Blocking
    public void receiveUser(Record<UUID, String> record) {
        try {
            UserMessage user = new ObjectMapper().readValue(record.value(), UserMessage.class);
            logger.infof("Got a user: %s", record.value());

            // throw new RuntimeException("error!");

            switch (user.getType()) {
                case "add" -> userService.save(user);
                case "edit" -> userService.edit(user);
                case "delete" -> userService.delete(user.getId());
            }

        } catch (Exception e) {
            throw new RuntimeException("retry this");
        }

    }


}
