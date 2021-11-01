package br.com.mateus.crud.endpoint.controller.exception;

import br.com.mateus.crud.endpoint.service.exception.DatabaseException;
import br.com.mateus.crud.endpoint.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<HandlerErrorMessage> resourceNotFoundException(ResourceNotFoundException ex) {
        HandlerErrorMessage errorMessage =
                new HandlerErrorMessage(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage(), "");
        return new ResponseEntity<HandlerErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DatabaseException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HandlerErrorMessage> databaseException(DatabaseException ex) {
        HandlerErrorMessage errorMessage =
                new HandlerErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), ex.getMessage(), "");
        return new ResponseEntity<HandlerErrorMessage>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}