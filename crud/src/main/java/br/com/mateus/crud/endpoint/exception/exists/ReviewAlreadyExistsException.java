package br.com.mateus.crud.endpoint.exception.exists;

public class ReviewAlreadyExistsException extends ResourceAlreadyExistsException {

    public ReviewAlreadyExistsException(String msg) {
        super(msg);
    }

}
