package br.com.mateus.crud.endpoint.exception.exists;

public class ReviewAlreadyExistsException extends ResourceAlreadyExistsException {

    public ReviewAlreadyExistsException(final String msg) {
        super(msg);
    }

}
