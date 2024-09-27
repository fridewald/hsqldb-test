package org.example.hsqldbtest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemPropertiesRepostiory extends CrudRepository<SystemProperties, String> {

    @Query(value = "SELECT * FROM INFORMATION_SCHEMA.SYSTEM_PROPERTIES", nativeQuery = true)
    List<SystemProperties> getAllProperties();

    @Query(value="SELECT * FROM INFORMATION_SCHEMA.SYSTEM_PROPERTIES WHERE PROPERTY_NAME = ?1", nativeQuery = true)
    SystemProperties getPropertyByName(String propertyName);
}

