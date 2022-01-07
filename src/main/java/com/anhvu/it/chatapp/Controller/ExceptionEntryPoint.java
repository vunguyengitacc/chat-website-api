package com.anhvu.it.chatapp.Controller;

import com.anhvu.it.chatapp.Model.User;
import com.anhvu.it.chatapp.Util.WebPayload.Response.MainResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionEntryPoint {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MainResponse> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        ex.printStackTrace();
        MainResponse<User> mainResponse;
        String mess = ex.getConstraintViolations().stream().map(i -> i.getMessage()).collect(Collectors.joining("/", "", ""));
        ex.getConstraintViolations().stream().toArray();
        mainResponse = new MainResponse<User>(mess, "FAILED", true);
        return ResponseEntity.badRequest().body(mainResponse);
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<MainResponse> classCastExceptionHandler(ClassCastException ex) {
        ex.printStackTrace();
        MainResponse<User> mainResponse;
        String mess = "Error: Internal server exception";
        mainResponse = new MainResponse<User>(mess, "FAILED", true);
        return ResponseEntity.internalServerError().body(mainResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity httpRequestMethodNotSupportedExceptionHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MainResponse> exceptionHandler(Exception e) {
        MainResponse<User> mainResponse;
        e.printStackTrace();
        mainResponse = new MainResponse("Error: " + e.getMessage(), "FAILED", true);
        return ResponseEntity.internalServerError().body(mainResponse);
    }

    @ExceptionHandler({NullPointerException.class, NoSuchElementException.class})
    public ResponseEntity<MainResponse> nullExceptionHandler(NullPointerException e){
        MainResponse<User> mainResponse;
        e.printStackTrace();
        String mess = "Error: the resource not found";
        mainResponse = new MainResponse(mess, "FAILED", true);
        return ResponseEntity.badRequest().body(mainResponse);
    }
}
