package com.anhvu.it.chatapp.utility.builder.user;

import com.anhvu.it.chatapp.data.model.Member;
import com.anhvu.it.chatapp.data.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class UserBuilderImplement implements UserBuilder {

    private User bean;

    private String username;

    private String password;

    private String name;

    private String email;

    private String avatarURI;

    private Set<Member> members;

    private Set<User> friends;

    private Set<User> requests;

    private Set<User> waits;

    private String phone;

    private String address;

    private String bio;

    public UserBuilderImplement(User bean) {
        this.bean = bean;
        this.friends = bean.getFriends();
        this.requests = bean.getRequests();
        this.waits = bean.getWaits();
    }

    @Override
    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public UserBuilder withAvatar(String avatarURI) {
        this.avatarURI = avatarURI;
        return this;
    }

    @Override
    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    public UserBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    @Override
    public UserBuilder withBio(String bio) {
        this.bio = bio;
        return this;
    }

    @Override
    public UserBuilder registerUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public UserBuilder registerPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public UserBuilder addFriend(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.friends == null) this.friends = new HashSet<>();
        this.friends.add(target);
        return this;
    }

    @Override
    public UserBuilder addRequest(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.requests == null) this.requests = new HashSet<>();
        this.requests.add(target);
        return this;
    }

    @Override
    public UserBuilder addWait(User target) {
        if (target.getUsername().equals(this.username)) throw new RuntimeException("You can't add you as a friend");
        if (this.waits == null) this.waits = new HashSet<>();
        this.waits.add(target);
        return this;
    }


    @Override
    public boolean validate() {

        Pattern letterPattern = Pattern.compile("\\\"^(?=.*[A-Za-z])[A-Za-z\\\\d@$!%*#?&]$\\\"");
        Pattern numberPattern = Pattern.compile("\\\"^(?=.*\\\\d)[A-Za-z\\\\d@$!%*#?&]$\\\"");
        Pattern specialPattern = Pattern.compile("\\\"^(?=.*[@$!%*#?&])[A-Za-z\\\\d@$!%*#?&]$\\\"");

        ArrayList<String> errors = new ArrayList<>();
        if (this.password.length() < 8 || this.password.length() > 20)
            errors.add("The password length must be between 8 and 20 characters");
        if (!letterPattern.matcher(this.password).matches()) {
            errors.add("The password value must have at least one letter");
        }
        if (!numberPattern.matcher(this.password).matches()) {
            errors.add("The password value must have at least one number");
        }
        if (!specialPattern.matcher(this.password).matches()) {
            errors.add("The password value must have at least one special character");
        }
        if (this.password.length() < 8 || this.password.length() > 20)
            errors.add("The password length must be between 8 and 20 characters");
        if (this.username.length() < 8 || this.username.length() > 20)
            errors.add("The username length must be between 8 and 20 characters");
        if (errors.size() == 0)
            return true;
        else throw new RuntimeException(String.join("|", errors));
    }

    @Override
    public User build() {
        validate();
        if (bean == null) bean = new User();
        if (this.name != null) bean.setName(this.name);
        if (this.email != null) bean.setEmail(this.email);
        if (this.username != null) bean.setUsername(this.username);
        if (this.password != null) bean.setPassword(this.password);
        if (this.address != null) bean.setAddress(this.address);
        if (this.bio != null) bean.setBio(this.bio);
        if (this.avatarURI != null) bean.setAvatarURI(this.avatarURI);
        if (this.phone != null) bean.setPhone(this.phone);
        bean.setFriends(this.friends);
        bean.setRequests(this.requests);
        bean.setWaits(this.waits);
        return bean;
    }
}
