package com.anhvu.it.chatapp.Util.WebPayload.Response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MainResponse<T> implements Serializable {
    private T data;
    private String status;
    private String message;

    //Constructors

    public MainResponse(T data, String status) {
        this.data = data;
        this.status = status;
    }

    public MainResponse(String message, String status, boolean haveMessage) {
        if (haveMessage) {
            this.status = status;
            this.message = message;
        } else {
            this.status = status;
        }

    }

}
