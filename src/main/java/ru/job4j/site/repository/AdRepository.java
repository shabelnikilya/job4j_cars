package ru.job4j.site.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.site.Post;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

public class AdRepository implements Repository {
    private final static Logger LOG = LoggerFactory.getLogger(AdRepository.class);
    private final SessionFactory sf;

    public AdRepository(SessionFactory sf) {
        this.sf = sf;
    }

    @Override
    public List<Post> findAll() {
        return this.tx(session -> session.createQuery("from Post").list());
    }

    @Override
    public List<Post> lastDayPost() {
        Instant now = Instant.now();
        Date beforeOneDay = Date.from(now.minus(Duration.ofDays(1)));
        Date dateNow = Date.from(now);
        return this.tx(session -> {
                    Query query = session.createQuery(
                            "from Post where created BETWEEN :paramInc and :paramEnd");
                    query.setParameter("paramInc", beforeOneDay);
                    query.setParameter("paramEnd", dateNow);
                    return query.list();
        });
    }

    @Override
    public List<Post> postWithPhoto() {
        return this.tx(session -> session.createQuery(
                "select po from Post po left join fetch po.photos ph where ph.post is not null",
                        Post.class)
                .list());
    }

    @Override
    public List<Post> findPostByBrand(String brand) {
        return this.tx(session -> session.createQuery(
                "select po from Post po join fetch po.car c where c.brand.name = :param", Post.class
                        ).setParameter("param", brand)
                        .list()
                        );
    }

    private <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        T rsl = null;
        try {
            rsl = command.apply(session);
            tx.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Error with transaction", e);
        } finally {
            session.close();
        }
        return rsl;
    }
}
