package br.com.mateus.crud.endpoint.exception.notFound;

public class SubjectNotFoundException extends ResourceNotFoundException {

    public SubjectNotFoundException(String msg) {
        super(msg);
    }

}
