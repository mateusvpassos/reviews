package br.com.mateus.crud.endpoint.exception.exists;

public class SubjectAlreadyExistsException extends ResourceAlreadyExistsException {

    public SubjectAlreadyExistsException(final String msg) {
        super(msg);
    }

}
