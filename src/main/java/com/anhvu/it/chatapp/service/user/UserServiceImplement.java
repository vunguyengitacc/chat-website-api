package com.anhvu.it.chatapp.service.user;

import com.anhvu.it.chatapp.respository.access.UserDAL;
import com.anhvu.it.chatapp.respository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImplement implements UserService, UserDetailsService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserDAL userDAL;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDAL.save(user);
    }

    @Override
    public User saveOne(User user, boolean isNewPassword) {
        if (isNewPassword == true)
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userDAL.save(user);
    }

    @Override
    public List<User> suggest(User user) {
        List<User> temp = userDAL.findAll();
        List<Long> userFriendIds = user.getFriends().stream().map(User::getId).collect(Collectors.toList());
        temp = temp.stream()
                .filter(i -> {
                    List<Long> t = i.getFriends().stream().map(User::getId).collect(Collectors.toList());
                    return !t.contains(user.getId());
                })
                .collect(Collectors.toList());
        temp = temp.stream().filter(i-> {
                    List<Long> t = i.getFriends().stream().map(User::getId).collect(Collectors.toList());
                    return userFriendIds.stream().filter(j -> t.contains(j)).collect(Collectors.toList()).size() > 0;
                })
                .collect(Collectors.toList());
        return temp;
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
        if (user == null) throw new UsernameNotFoundException("User not found");
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("user"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public boolean sendRequest(User target, User me) {
        target.addRequest(me);
        me.addWait(target);
        userDAL.save(target);
        userDAL.save(me);
        return true;
    }

    @Override
    public boolean acceptRequest(User target, User me) {
        target.removeWait(me);
        me.removeRequest(target);
        me.addFriend(target);
        target.addFriend(me);
        userDAL.save(target);
        userDAL.save(me);
        return true;
    }

    @Override
    public boolean denyRequest(User target, User me) {
        target.removeWait(me);
        me.removeRequest(target);
        userDAL.save(target);
        userDAL.save(me);
        return true;
    }

    @Override
    public boolean cancelRequest(User target, User me) {
        target.removeRequest(me);
        me.removeWait(target);
        userDAL.save(target);
        userDAL.save(me);
        return true;
    }

    @Override
    public boolean removeFriend(User target, User me) {
        target.removeFiend(me);
        me.removeFiend(target);
        userDAL.save(target);
        userDAL.save(me);
        return true;
    }
}
