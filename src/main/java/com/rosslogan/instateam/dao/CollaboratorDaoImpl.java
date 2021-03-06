package com.rosslogan.instateam.dao;

import com.rosslogan.instateam.model.Collaborator;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CollaboratorDaoImpl implements CollaboratorDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @SuppressWarnings("unchecked")
    public List<Collaborator> findAll() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Get all categories with a Hibernate criteria
        List<Collaborator> collaborators = session.createCriteria(Collaborator.class).list();

        // Close session
        session.close();

        return collaborators;
    }

    @Override
    public Collaborator findById(Long id) {
        Session session = sessionFactory.openSession();
        Collaborator collaborator = session.get(Collaborator.class, id);
        Hibernate.initialize(collaborator.getProjects());
        Hibernate.initialize(collaborator.getRole());
        session.close();
        return collaborator;
    }

    @Override
    public void save(Collaborator collaborator) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Save the category
        session.saveOrUpdate(collaborator);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @Override
    public void delete(Collaborator collaborator) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(collaborator);
        session.getTransaction().commit();
        session.close();
    }
}
