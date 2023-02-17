package br.edu.unochapeco.feirinha.exception;

public class InsuficientInventoryException extends Exception {

    public InsuficientInventoryException(){
        super("O produto selecionado não possui estoque suficiente para realizar a operação!");
    }

    public InsuficientInventoryException(String customMessage){
        super(customMessage);
    }
}
