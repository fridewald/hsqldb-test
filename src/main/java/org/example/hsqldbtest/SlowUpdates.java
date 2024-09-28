package org.example.hsqldbtest;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public class SlowUpdates {


    private final SessionFactory sessionFactory;
    private static final Logger log = LoggerFactory.getLogger(SlowUpdates.class);

    public SlowUpdates(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Scheduled(fixedRate = 10000)
    public void slowlyUpdatingLamps() {
        new Thread(new Runnable() {
            public void run() {
                log.info("try to slow update");
                try (

                        Session session = sessionFactory.openSession();) {
                    session.beginTransaction();

                    long now = System.currentTimeMillis();

                    CriteriaBuilder lCriteriaBu = session.getCriteriaBuilder();
                    CriteriaQuery<Lamp> lQuery = lCriteriaBu.createQuery(Lamp.class);
                    Root<Lamp> lRoot = lQuery.from(Lamp.class);
                    lQuery.select(lRoot).where(lCriteriaBu.equal(lRoot.get("numberOfLamps"), 3));
                    log.info("Lamp read with findById(1L):");
                    List<Lamp> lamps = session.createQuery(lQuery).getResultList();

                    for (Lamp l : lamps) {
                        l.setLastUpdated(now);
                        session.persist(l);
                        log.info(l.toString());
                    }
                    session.flush();
                    // Wait 5 seconds and keep transaction open
                    Thread.sleep(5000);
                    List<Lamp> lamps2 = session.createQuery(lQuery).getResultList();
                    for (Lamp l : lamps2) {
                        l.setLastUpdated(now);
                        session.persist(l);
                        log.info(l.toString());
                    }
                    session.getTransaction().commit();
                    log.info("Session end");
                } catch (Exception e) {
                    log.error("Error in slowlyUpdatingLamps", e);
                }
            }
        }).start();
    }
}
