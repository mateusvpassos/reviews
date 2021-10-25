package br.com.mateus.crud.endpoint.service.exception;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String msg){
        super(msg);
    }

}