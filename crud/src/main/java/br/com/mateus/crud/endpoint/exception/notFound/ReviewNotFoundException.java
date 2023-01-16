package br.com.mateus.crud.endpoint.exception.notFound;

public class ReviewNotFoundException extends ResourceNotFoundException {

    public ReviewNotFoundException(final String msg) {
        super(msg);
    }

}
