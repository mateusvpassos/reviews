package br.com.mateus.crud.endpoint.exception.notFound;

public abstract class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }

}