package com.myproject.dao.impl;

import com.myproject.dao.UserDao;
import com.myproject.model.User;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void add(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failure: can't add user #" + user.getId() + ":" + user);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<User> query =
                    session.getCriteriaBuilder().createQuery(User.class);
            query.from(User.class);
            return session.createQuery(query).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failure: can't retrieve list of users from DB.", e);
        }
    }
}
