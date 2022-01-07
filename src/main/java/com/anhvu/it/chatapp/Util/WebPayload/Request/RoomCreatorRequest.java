package com.anhvu.it.chatapp.Util.WebPayload.Request;

import com.anhvu.it.chatapp.Model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreatorRequest {

    private Room room;
    private Set<Long> members;

}
