package com.anhvu.it.chatapp.service.user;

import com.anhvu.it.chatapp.respository.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getByUsername(String username);

    User getById(Long id);

    User createOne(User user);

    User saveOne(User user, boolean isNewPassword);

    List<User> suggest(User user);

    List<User> search(String term);

    boolean sendRequest(User target, User me);

    boolean acceptRequest(User target, User me);

    boolean denyRequest(User target, User me);

    boolean cancelRequest(User target, User me);

    boolean removeFriend(User target, User me);
}
