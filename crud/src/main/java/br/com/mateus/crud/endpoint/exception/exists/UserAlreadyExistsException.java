package br.com.mateus.crud.endpoint.exception.exists;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {

    public UserAlreadyExistsException(final String msg) {
        super(msg);
    }

}
