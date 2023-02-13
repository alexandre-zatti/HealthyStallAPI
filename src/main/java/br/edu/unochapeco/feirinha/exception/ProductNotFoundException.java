package br.edu.unochapeco.feirinha.exception;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(){
        super("O produto informado não foi encontrado!");
    }

    public ProductNotFoundException(String customMessage){
        super(customMessage);
    }
}
