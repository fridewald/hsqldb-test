package org.example.hsqldbtest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Entity
@Table(name = "LAMP")
public class Lamp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String productName;
    private Integer numberOfLamps;
    private Double price;
    @Setter
    private Long lastUpdated = new Date().getTime();

    protected Lamp() {
    }
    public Lamp(String productName, Integer numberOfLamps, Double price) {
        this.productName = productName;
        this.numberOfLamps = numberOfLamps;
        this.price = price;
    }

}
