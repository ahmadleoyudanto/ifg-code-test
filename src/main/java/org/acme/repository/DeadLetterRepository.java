package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.DeadLetter;
import org.acme.entity.User;

@ApplicationScoped
public class DeadLetterRepository implements PanacheRepository<DeadLetter> {
}
