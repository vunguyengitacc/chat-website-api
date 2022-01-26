package com.anhvu.it.chatapp.respository.access;

import com.anhvu.it.chatapp.respository.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("user")
public interface UserDAL extends CrudRepository<User, Long> {
    List<User> findAll();

    User findByUsername(String username);

}
