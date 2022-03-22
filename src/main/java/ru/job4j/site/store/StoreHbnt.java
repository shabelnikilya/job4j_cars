package ru.job4j.site.store;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class StoreHbnt implements Store {
    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf;

    private StoreHbnt() {
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static final class Single {
        private static final StoreHbnt INST = new StoreHbnt();
    }

    public static StoreHbnt instance() {
        return StoreHbnt.Single.INST;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return sf;
    }
}
