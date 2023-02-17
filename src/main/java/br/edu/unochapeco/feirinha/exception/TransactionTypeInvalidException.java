package br.edu.unochapeco.feirinha.exception;

public class TransactionTypeInvalidException extends Exception {

    public TransactionTypeInvalidException(){
        super("O tipo de transação informado não é valido!");
    }

    public TransactionTypeInvalidException(String customMessage){
        super(customMessage);
    }
}
