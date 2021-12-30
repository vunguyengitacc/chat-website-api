package com.anhvu.it.chat.app.Service.User;

import com.anhvu.it.chat.app.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getByUsername(String username);

    public User createOne(User user);
}
