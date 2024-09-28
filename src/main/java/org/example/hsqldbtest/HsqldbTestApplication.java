package org.example.hsqldbtest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan("org.example.hsqldbtest")
@EnableJpaRepositories("org.example.hsqldbtest")
public class HsqldbTestApplication {
    private static final Logger log = LoggerFactory.getLogger(HsqldbTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HsqldbTestApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(LampRepository repository, SystemPropertiesRepostiory systemPropertiesRepostiory) {
        return (args) -> {
            repository.save(new Lamp("Table Lamp", 3, 20.0));
            repository.save(new Lamp("Floor Lamp", 1, 30.0));
            repository.save(new Lamp("Door Lamp", 3, 130.0));

            SystemProperties systemProperty = systemPropertiesRepostiory.getPropertyByName("hsqldb.tx");
            log.info("System property found with findById('hsqldb.tx'):");
            log.info("--------------------------------");
            log.info(systemProperty.toString());

        };
    }
}
