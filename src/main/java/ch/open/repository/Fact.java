package ch.open.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Fact extends PanacheEntity {

    public LocalDateTime timestamp;
    public String statement;
}
