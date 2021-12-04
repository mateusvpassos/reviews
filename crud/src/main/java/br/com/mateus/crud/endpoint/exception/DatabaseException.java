package br.com.mateus.crud.endpoint.exception;

public class DatabaseException extends RuntimeException{

    public DatabaseException(String msg){
        super(msg);
    }

}