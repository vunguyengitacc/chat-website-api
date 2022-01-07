package com.anhvu.it.chatapp.data.access;

import com.anhvu.it.chatapp.data.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("user")
public interface UserDAL extends CrudRepository<User, Long> {
    public List<User> findAll();

    public User findByUsername(String username);


}