package com.anhvu.it.chat.app.Util.Response;

public class MainResponse<T> {
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

    public MainResponse() {
    }

    //Getters

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
//Setters

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
