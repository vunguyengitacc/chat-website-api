package com.anhvu.it.chat.app.Model.ID;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RoomDetailID implements Serializable {
    private Long userId;
    private Long roomId;

    //Constructors

    public RoomDetailID(Long userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
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
