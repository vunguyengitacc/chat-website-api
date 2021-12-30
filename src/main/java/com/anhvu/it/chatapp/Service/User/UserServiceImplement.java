package com.anhvu.it.chat.app.Service.User;

import com.anhvu.it.chat.app.DataAccess.UserDAL;
import com.anhvu.it.chat.app.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Component
public class UserServiceImplement implements UserService {

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
    public User createOne(User user) {
        try {
            return userDAL.save(user);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
