package com.anhvu.it.chatapp.Service.User;

import com.anhvu.it.chatapp.DataAccess.UserDAL;
import com.anhvu.it.chatapp.Model.Member;
import com.anhvu.it.chatapp.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserServiceImplement implements UserService, UserDetailsService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserDAL userDAL;

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAL.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User not found");
        Collection<GrantedAuthority> authorities= new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
