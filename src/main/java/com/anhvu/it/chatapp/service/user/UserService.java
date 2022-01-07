package com.anhvu.it.chatapp.service.user;

import com.anhvu.it.chatapp.data.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getByUsername(String username);

    public User getById(Long id);

    public User saveOne(User user);

    public List<User> search(String term);
}
