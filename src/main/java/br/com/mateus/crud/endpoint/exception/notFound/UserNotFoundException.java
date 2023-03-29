package br.com.mateus.crud.endpoint.exception.notFound;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(final String msg) {
        super(msg);
    }

}
