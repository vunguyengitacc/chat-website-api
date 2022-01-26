package com.anhvu.it.chatapp.utility.builder.user;

import com.anhvu.it.chatapp.respository.model.User;

public interface UserBuilder {

    public UserBuilder withName(String name);

    public UserBuilder withEmail(String email);

    public UserBuilder withAvatar(String avatarURI);

    public UserBuilder withPhone(String phone);

    public UserBuilder withAddress(String address);

    public UserBuilder withBio(String bio);

    public UserBuilder registerUsername(String username);

    public UserBuilder registerPassword(String password);

    public UserBuilder addFriend(User target);

    public UserBuilder addRequest(User target);

    public UserBuilder addWait(User target);

    public User build();

    public boolean validate();

}
