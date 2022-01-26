package com.anhvu.it.chatapp.utility.payload.request;

import com.anhvu.it.chatapp.utility.type.RoomType;
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

    private String name;
    private RoomType type;
    private Set<Long> memberIds;

}
