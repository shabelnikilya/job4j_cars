package ru.job4j.site.store;

import org.hibernate.SessionFactory;

public interface Store {

    SessionFactory getSessionFactory();
}
