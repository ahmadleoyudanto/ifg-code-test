package org.acme.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public void setName(String name) {
        this.name = name;
    };

    public Long getId() {
        return id;
    };

    public String getName() {
        return name;
    };
}
