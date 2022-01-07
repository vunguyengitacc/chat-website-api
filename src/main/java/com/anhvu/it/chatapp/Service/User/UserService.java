package com.anhvu.it.chatapp.Service.User;

import com.anhvu.it.chatapp.Model.User;
import org.springframework.stereotype.Component;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getByUsername(String username);

    public User getById(Long id);

    public User saveOne(User user);

    public List<User> search(String term);
}
