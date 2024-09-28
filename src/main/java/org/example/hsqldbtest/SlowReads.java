package org.example.hsqldbtest;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Service
@EnableTransactionManagement
public class SlowReads {

    @Autowired
    private DataSource dataSource;

    private final SessionFactory sessionFactory;
    private final LampRepository repository;
    private static final Logger log = LoggerFactory.getLogger(SlowReads.class);

    public SlowReads(LampRepository repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void slowlyReadingLamps() {
        new Thread(new Runnable() {
            @Transactional
            public void run() {
                try
//                        (
//
//                        Connection connection = dataSource.getConnection();
//                )
                {
//                    connection.setAutoCommit(false);
//                    connection.setReadOnly(false);
//                    Session session = sessionFactory.getCurrentSession();
//                    session.setDefaultReadOnly(true);
//                    session.setDefaultReadOnly(false);
//                    session.beginTransaction();
                    long now = System.currentTimeMillis();
//                    List<Lamp> lamps = (List<Lamp>) repository.findAll();
                    List<Lamp> lamps = repository.getAllWithNLamps(3);
                    log.info("Lamp read with findById(1L):");
                    for (Lamp l : lamps) {
                        l.setLastUpdated(now);
                        repository.save(l);
                        log.info(l.toString());
                    }
                    Thread.sleep(5000);
                    for (Lamp l : lamps) {
                        l.setLastUpdated(System.currentTimeMillis());
                        repository.save(l);
                        log.info(l.toString());
                    }
                    log.info("Session end");
//                    session.getTransaction().commit();
//                    session.close();
//                    connection.commit();
                } catch (Exception e) {
                    log.error("Error in slowlyReadingLamps", e);
                }
            }
        }).start();
    }
}
