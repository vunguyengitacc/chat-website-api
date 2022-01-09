package com.anhvu.it.chatapp.utility.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username = "";
    private String password = "";
    private String name = "";
    private String avatarURI = "";



    //Another

    public void generateURI(){
        this.avatarURI = "https://avatars.dicebear.com/4.5/api/initials/"+this.name+".svg";
    }
}
