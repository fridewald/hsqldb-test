package org.example.hsqldbtest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FastReads {

    private final SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(FastReads.class);

    public FastReads(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Scheduled(fixedRate = 500)
    public void readLamps() {
        log.info("try to fast read");
        try (
                Session session = sessionFactory.openSession();) {
            session.beginTransaction();
            session.setDefaultReadOnly(true);
            log.info("Lamp read with findById(1L):");
            Lamp lamp = session.get(Lamp.class, 1L);
            log.info(lamp.toString());
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Error in readLamps", e);
        }
    }
}
