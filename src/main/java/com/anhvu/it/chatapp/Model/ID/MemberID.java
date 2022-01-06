package com.anhvu.it.chatapp.Model.ID;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MemberID implements Serializable {
    private Long userId;
    private Long roomId;

    //Constructors

    public MemberID(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }

    public MemberID() {
    }

    //Getters

    public Long getUserId() {
        return userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    //Setters

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
