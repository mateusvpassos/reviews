package br.com.mateus.crud.endpoint.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class HandlerErrorMessage {
    private HttpStatus status;
    private LocalDateTime date;
    private String message;
    private String description;

    public HandlerErrorMessage(final HttpStatus status, final LocalDateTime date, final String message,
            final String description) {
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

    public void setDescription(final String description) {
        this.description = description;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setStatus(final HttpStatus status) {
        this.status = status;
    }
}
