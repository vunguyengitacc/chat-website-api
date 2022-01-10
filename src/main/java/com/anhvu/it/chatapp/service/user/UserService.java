package com.anhvu.it.chatapp.service.user;

import com.anhvu.it.chatapp.data.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User getByUsername(String username);

    public User getById(Long id);

    public User saveOne(User user);

    public List<User> search(String term);

    public boolean sendRequest(User target, User wanter);

    public boolean acceptRequest(User target, User wanter);

    public boolean denyRequest(User target, User wanter);

    public boolean cancelRequest(User target, User wanter);

    public boolean removeFriend(User target, User wanter);
}
