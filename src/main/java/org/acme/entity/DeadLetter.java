package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "dead_letters")
public class DeadLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String topic;
    private String message;

    public void setTopic(String topic) {
        this.topic = topic;
    };

    public void setMessage(String message) {
        this.message = message;
    };

    public Long getId() {
        return id;
    };

    public String getTopic() {
        return topic;
    };

    public String getMessage() {
        return message;
    };
}
