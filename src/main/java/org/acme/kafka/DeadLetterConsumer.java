package org.acme.kafka;

import io.smallrye.reactive.messaging.kafka.Record;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.service.DeadLetterService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class DeadLetterConsumer {
    private final org.jboss.logging.Logger logger = Logger.getLogger(DeadLetterConsumer.class);

    @Inject
    DeadLetterService deadLetterService;

    @Incoming("dead-letter-topic-users-in")
    public void receiveUserDLQ(ConsumerRecord<UUID, String> record) {
        try {
            logger.infof("Got a deadletter: %s", record.value());

            deadLetterService.save(record.value(), record.topic());
        } catch (Exception e) {
            // handle error
        }

    }
}
