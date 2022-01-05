package com.anhvu.it.chatapp.Service.User;

import com.anhvu.it.chatapp.DataAccess.UserDAL;
import com.anhvu.it.chatapp.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class UserServiceImplement implements UserService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserDAL userDAL;

    @Override
    public List<User> getAll() {
        return userDAL.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userDAL.findByUsername(username);
    }

    @Override
    public User getById(Long id) {
        return userDAL.findById(id).get();
    }

    @Override
    public User createOne(User user) {
        return userDAL.save(user);
    }

    @Override
    public List<User> search(String term) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> user = criteriaQuery.from(User.class);
        Predicate namePredicate = criteriaBuilder.like(user.get("name"), "%" + term + "%");
        criteriaQuery.where(namePredicate);
        TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
