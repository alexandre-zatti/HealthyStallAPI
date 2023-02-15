package br.edu.unochapeco.feirinha.exception;

public class NoActiveFeiranteException extends Exception {

    public NoActiveFeiranteException(){
        super("Nenhum feirante encontra-se ativo no momento!");
    }

    public NoActiveFeiranteException(String customMessage){
        super(customMessage);
    }
}
