package br.edu.unochapeco.feirinha.exception;

public class UniqueUsernameValidationException extends Exception {

    public UniqueUsernameValidationException(){
        super("O username informado já esta sendo utilizado por outro usuário!");
    }

    public UniqueUsernameValidationException(String customMessage){
        super(customMessage);
    }
}
