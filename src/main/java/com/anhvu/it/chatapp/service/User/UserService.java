package com.anhvu.it.chatapp.service.User;

import com.anhvu.it.chatapp.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getByUsername(String username);

    public User getById(Long id);

    public User saveOne(User user);

    public List<User> search(String term);
}
