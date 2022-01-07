package com.anhvu.it.chatapp.utility.payload.request;

public class RegisterRequest {
    private String username;
    private String password;
    private String name;
    private String avatarURI;

    //Constructors
    public RegisterRequest(String username, String password, String name, String avatarURI) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.avatarURI = avatarURI;
    }

    public RegisterRequest(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public RegisterRequest() {
    }

    //Getters

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAvatarURI() {
        return avatarURI;
    }

    //Setters

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatarURI(String avatarURI) {
        this.avatarURI = avatarURI;
    }

    //Another

    public void generateURI(){
        this.avatarURI = "https://avatars.dicebear.com/4.5/api/initials/"+this.name+".svg";
    }
}
