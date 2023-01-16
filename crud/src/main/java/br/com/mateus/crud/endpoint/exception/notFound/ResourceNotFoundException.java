package br.com.mateus.crud.endpoint.exception.notFound;

public abstract class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String msg) {
        super(msg);
    }

}