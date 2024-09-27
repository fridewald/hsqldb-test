package org.example.hsqldbtest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
public class FastUpdates {

    @Autowired
    private DataSource dataSource;
    private final SessionFactory sessionFactory;
    private final LampRepository repository;
    private static final Logger log = LoggerFactory.getLogger(FastUpdates.class);

    public FastUpdates(LampRepository repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }


    @Scheduled(fixedRate = 500)
//    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateLamps() {
        try (
                Connection connection = dataSource.getConnection();
        ) {
            connection.setAutoCommit(false);
            connection.setReadOnly(false);
            long time = System.currentTimeMillis();
            Lamp lamp = repository.findByProductId(1L);
            lamp.setLastUpdated(time);
            log.info("Lamp update with findById(1L):");
            log.info(lamp.toString());
            connection.commit();
        } catch (Exception e) {
            log.error("Error in updateLamps", e);
        }
//        Session session = sessionFactory.getCurrentSession();
//        session.setDefaultReadOnly(false);
//        session.beginTransaction();
//        session.getTransaction().commit();
//        session.close();
    }
}