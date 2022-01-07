package com.anhvu.it.chatapp.model.ID;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
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

}
