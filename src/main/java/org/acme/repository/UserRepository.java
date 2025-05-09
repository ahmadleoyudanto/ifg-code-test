package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.data.repository.BasicRepository;
import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
}
