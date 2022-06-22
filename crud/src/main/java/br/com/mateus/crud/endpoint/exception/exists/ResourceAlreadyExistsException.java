package br.com.mateus.crud.endpoint.exception.exists;

public abstract class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }

}