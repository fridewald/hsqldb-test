package org.example.hsqldbtest;


import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
@EnableScheduling
//@Import(DatabaseConfig.class)
@EntityScan("org.example.hsqldbtest")
@EnableJpaRepositories("org.example.hsqldbtest")
public class HsqldbTestApplication {
    private static final Logger log = LoggerFactory.getLogger(HsqldbTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HsqldbTestApplication.class, args);
    }

    @Autowired
    DataSource dataSource;

//    @Bean
//    FastUpdates fastUpdates(LampRepository repository, SessionFactory sessionFactory) {
//        return new FastUpdates(repository, sessionFactory);
//    }


    @Bean
    SlowReads slowReads(LampRepository repository, SessionFactory sessionFactory) {
        return new SlowReads(repository, sessionFactory);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public CommandLineRunner demo(LampRepository repository, SystemPropertiesRepostiory systemPropertiesRepostiory) {
        return (args) -> {
//            LoggerFactory.getLogpuoger("org.hibernate").(org.slf4j.event.Level.ERROR);
//
//...

//Logger log = Logger.getLogger("org.hibernate.SQL");
//log.setLevel(Level.DEBUG);
            repository.save(new Lamp("Table Lamp", 2, 20.0));
            repository.save(new Lamp("Floor Lamp", 1, 30.0));
            repository.save(new Lamp("Door Lamp", 3, 130.0));
//            repository.save(new Lamp("Desk Lamp", 3, 10.0));

            // fetch all lamps
//            log.info("Lampo found with findAll():");
//            log.info("-------------------------------");
//            repository.findAll().forEach(lamp -> {
//                log.info(lamp.toString());
//            });
//            log.info("");
//
//            Lamp lamp = repository.findByProductId(1L);
//            log.info("Lamp found with findById(1L):");
//            log.info("--------------------------------");
//            log.info(lamp.toString());
//            log.info("");
//
//            log.info("Lamp found with findByNumberOfLamps(3):");
//            log.info("--------------------------------------------");
//            repository.findByNumberOfLamps(3).forEach(lamp1 -> {
//                log.info(lamp1.toString());
//            });
//            log.info("");
////            List<SystemProperties> systemProperties = systemPropertiesRepostiory.getAllProperties();
////            log.info("System properties found with getAllProperties():");
////            log.info("--------------------------------");
////            systemProperties.forEach(property -> {
////                log.info(property.toString());
////            });
            SystemProperties systemProperty = systemPropertiesRepostiory.getPropertyByName("hsqldb.tx");
            log.info("System property found with findById('hsqldb.tx'):");
            log.info("--------------------------------");
            log.info(systemProperty.toString());

        };
    }
}
