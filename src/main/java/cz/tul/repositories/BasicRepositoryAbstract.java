package cz.tul.repositories;

import cz.tul.entities.CustomEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Marek on 29.09.2016.
 */

public abstract class BasicRepositoryAbstract {
    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public <T extends CustomEntity> void save(T entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    @Transactional
    public <T extends CustomEntity> void save(Set<T> entities) {
        for (T entity : entities) {
            sessionFactory.getCurrentSession().save(entity);
        }

    }

    @Transactional
    public <T extends CustomEntity> void update(T entity) {
        sessionFactory.getCurrentSession().merge(entity);
    }

    @Transactional
    public <T extends CustomEntity> void update(List<T> entities) {
        for (T entity : entities) {
            sessionFactory.getCurrentSession().merge(entity);
        }

    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    protected Query getQuery(String hql) {
        return getSessionFactory().getCurrentSession().createQuery(hql);
    }
}
