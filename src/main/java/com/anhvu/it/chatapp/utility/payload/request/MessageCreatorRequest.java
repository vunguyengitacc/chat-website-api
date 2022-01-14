package com.anhvu.it.chatapp.utility.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreatorRequest {
    private String content;
    private Long roomId;
}
