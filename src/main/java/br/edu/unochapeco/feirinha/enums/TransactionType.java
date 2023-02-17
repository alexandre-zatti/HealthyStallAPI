package br.edu.unochapeco.feirinha.enums;

import br.edu.unochapeco.feirinha.exception.TransactionTypeInvalidException;

import java.util.Arrays;

public enum TransactionType {
    DEPOSIT, PURCHASE;

    public static TransactionType findByValue(String value) throws TransactionTypeInvalidException {
        return Arrays.stream(values()).filter(type -> type.name()
                .equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(TransactionTypeInvalidException::new);
    }
}
