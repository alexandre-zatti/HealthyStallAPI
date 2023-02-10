package br.edu.unochapeco.feirinha.exception;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(){
        super("Usuario informado não foi encontrado!");
    }

    public PersonNotFoundException(String customMessage){
        super(customMessage);
    }
}
