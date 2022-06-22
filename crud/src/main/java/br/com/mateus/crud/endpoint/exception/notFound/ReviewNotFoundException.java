package br.com.mateus.crud.endpoint.exception.notFound;

public class ReviewNotFoundException extends ResourceNotFoundException {

    public ReviewNotFoundException(String msg) {
        super(msg);
    }

}
