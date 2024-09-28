package org.example.hsqldbtest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LampRepository extends CrudRepository<Lamp, Long> {
    @Query(value = "SELECT l FROM Lamp l WHERE l.numberOfLamps = ?1")
    List<Lamp> getAllWithNLamps(int numberOfLamps);

    @Transactional
    Lamp findByProductId(long productId);
}
