package br.edu.unochapeco.feirinha.exception;

public class InsuficientBalanceException extends Exception {

    public InsuficientBalanceException(){
        super("O usuário não possui saldo suficiente para realizar a operação!");
    }

    public InsuficientBalanceException(String customMessage){
        super(customMessage);
    }
}
