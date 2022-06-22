package br.com.mateus.crud.endpoint.exception.exists;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }

}
