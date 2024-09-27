package org.example.hsqldbtest;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

@Entity
@ToString
public class SystemProperties {

    @Id
    private String PROPERTY_NAME;
    private String PROPERTY_VALUE;

}
