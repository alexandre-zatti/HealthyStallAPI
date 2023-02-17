package br.edu.unochapeco.feirinha.mapper;

import br.edu.unochapeco.feirinha.dto.TransactionPostDto;
import br.edu.unochapeco.feirinha.entity.Transaction;
import br.edu.unochapeco.feirinha.enums.TransactionType;
import br.edu.unochapeco.feirinha.exception.PersonNotFoundException;
import br.edu.unochapeco.feirinha.exception.ProductNotFoundException;
import br.edu.unochapeco.feirinha.exception.TransactionTypeInvalidException;
import br.edu.unochapeco.feirinha.service.PersonService;
import br.edu.unochapeco.feirinha.service.ProductService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class TransactionPostMapper {

    @Autowired
    protected PersonService personService;
    @Autowired
    protected ProductService productService;

    abstract TransactionPostDto toTransactionPostDto(Transaction transaction);

    public Transaction toTransaction(TransactionPostDto transactionPostDto)
            throws PersonNotFoundException, ProductNotFoundException, TransactionTypeInvalidException {

        var transaction = new Transaction();

        var person = this.personService.getPersonById(transactionPostDto.personId());
        transaction.setPerson(person);

        var transactionType = TransactionType.findByValue(transactionPostDto.transactionType());
        transaction.setTransactionType(transactionType);

        if(transactionPostDto.productId() != null){
            transaction.setProduct(this.productService.getProductById(transactionPostDto.productId()));
        }

        if(transactionPostDto.deposit() != null){
            transaction.setDeposit(transactionPostDto.deposit());
        }

        return transaction;
    }

}
