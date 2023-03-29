package br.com.mateus.crud.endpoint.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mateus.crud.endpoint.exception.exists.ResourceAlreadyExistsException;
import br.com.mateus.crud.endpoint.exception.notFound.ResourceNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = { ResourceNotFoundException.class })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<HandlerErrorMessage> resourceNotFoundException(final ResourceNotFoundException ex) {
        HandlerErrorMessage errorMessage = new HandlerErrorMessage(HttpStatus.NOT_FOUND, LocalDateTime.now(),
                ex.getMessage(), "Resource Not Found");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { ResourceAlreadyExistsException.class })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<HandlerErrorMessage> resourceAlreadyExistsException(final ResourceAlreadyExistsException ex) {
        HandlerErrorMessage errorMessage = new HandlerErrorMessage(HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                ex.getMessage(), "Resource already exists");
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
}