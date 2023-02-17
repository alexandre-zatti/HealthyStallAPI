package br.edu.unochapeco.feirinha.dto;

import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.entity.Product;
import br.edu.unochapeco.feirinha.enums.TransactionType;

import java.util.Date;

public record TransactionGetDto(
        Long id,
        Person person,
        Product product,
        Double deposit,
        TransactionType transactionType,
        Date createdAt
) {
}
