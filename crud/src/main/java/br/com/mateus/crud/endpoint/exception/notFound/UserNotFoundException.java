package br.com.mateus.crud.endpoint.exception.notFound;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException(String msg) {
        super(msg);
    }

}
