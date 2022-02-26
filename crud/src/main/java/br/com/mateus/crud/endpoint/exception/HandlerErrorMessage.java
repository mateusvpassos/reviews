package br.com.mateus.crud.endpoint.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class HandlerErrorMessage {
    private HttpStatus status;
    private LocalDateTime date;
    private String message;
    private String description;

    public HandlerErrorMessage(HttpStatus status, LocalDateTime date, String message, String description){
        this.status = status;
        this.date = date;
        this.message = message;
        this.description = description;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getMessage() {
        return message;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
