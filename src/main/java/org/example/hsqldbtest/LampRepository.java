package org.example.hsqldbtest;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LampRepository extends CrudRepository<Lamp, Long> {
//    List<Lamp> findByNumberOfLamps(Integer numberOfLamps);

    @Query(value="SELECT l FROM Lamp l WHERE l.numberOfLamps = ?1")
    List<Lamp> getAllWithNLamps(int numberOfLamps);
    Lamp findByProductId(long productId);
}
