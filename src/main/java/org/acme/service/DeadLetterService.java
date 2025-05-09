package org.acme.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.acme.dto.UserMessage;
import org.acme.entity.DeadLetter;
import org.acme.kafka.UserProducer;
import org.acme.repository.DeadLetterRepository;

import java.util.List;

@ApplicationScoped
public class DeadLetterService {
    @Inject
    DeadLetterRepository deadLetterRepository;
    @Inject
    UserProducer userProducer;

    public void retryAll() throws JsonProcessingException {
        PanacheQuery<DeadLetter> deadLetters = deadLetterRepository.findAll();
        for (DeadLetter deadLetter : deadLetters.list()) {
            String topic = deadLetter.getTopic().replaceAll("\\b(dead-letter-topic-|-in|-out)\\b", "");

            if (topic.equals("users")) {
                UserMessage user = new ObjectMapper().readValue(deadLetter.getMessage(), UserMessage.class);
                userProducer.sendUserToKafka(user);
                delete(deadLetter.getId());
            }
        }
    }

    @Transactional
    public void save(String message, String topic) {
        DeadLetter deadLetter = new DeadLetter();
        deadLetter.setMessage(message);
        deadLetter.setTopic(topic);
        deadLetterRepository.persist(deadLetter);
    }

    @Transactional
    public void delete(Long id) {
        deadLetterRepository.deleteById(id);
    }
}
