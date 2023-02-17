package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.entity.Product;
import br.edu.unochapeco.feirinha.entity.Transaction;
import br.edu.unochapeco.feirinha.enums.TransactionType;
import br.edu.unochapeco.feirinha.exception.*;
import br.edu.unochapeco.feirinha.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final PersonService personService;

    private final ProductService productService;

    public TransactionService(
            TransactionRepository transactionRepository,
            PersonService personService,
            ProductService productService
    ){
        this.productService = productService;
        this.personService = personService;
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction)
            throws PersonNotFoundException, ProductNotFoundException, InsuficientBalanceException, InsuficientInventoryException {

        if (transaction.getTransactionType() == TransactionType.DEPOSIT){
            var updatedPerson = this.handlePersonDepositTransaction(transaction);
            transaction.setPerson(updatedPerson);
        }

        if(transaction.getTransactionType() == TransactionType.PURCHASE){
            if (transaction.getProduct() == null){
                throw new ProductNotFoundException();
            }

            var updatedPerson = this.handlePersonPurchaseTransaction(transaction);
            var updatedProduct = this.handleProductPurchaseTransaction(transaction);

            transaction.setPerson(updatedPerson);
            transaction.setProduct(updatedProduct);
        }

        return this.transactionRepository.save(transaction);
    }

    public Page<Transaction> getAllTransactions(int pageNumber, int pageSize){
       return this.transactionRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Page<Transaction> getAllTransactionsWithDateFilter(Date fromDate, Date toDate, int pageNumber, int pageSize){
        return this.transactionRepository.findByCreatedAtBetween(fromDate, toDate, PageRequest.of(pageNumber, pageSize));
    }

    public Page<Transaction> getAllPersonTransactions(Long personId, int pageNumber, int pageSize){
        return this.transactionRepository.findByPersonId(personId,PageRequest.of(pageNumber, pageSize));
    }

    public Page<Transaction> getAllPersonTransactionsWithDateFilter(Long personId, Date fromDate, Date toDate, int pageNumber, int pageSize){
        return this.transactionRepository.findByPersonIdAndCreatedAtBetween(personId, fromDate, toDate, PageRequest.of(pageNumber, pageSize));
    }

    private Person handlePersonDepositTransaction(Transaction transaction) throws PersonNotFoundException{
       return this.personService.addToPersonBalace(transaction.getPerson().getId(), transaction.getDeposit());
    }

    private Person handlePersonPurchaseTransaction(Transaction transaction)
            throws PersonNotFoundException, InsuficientBalanceException {

       return this.personService.withdrawFromPersonBalace(
               transaction.getPerson().getId(),
               transaction.getProduct().getPrice()
       );
    }

    private Product handleProductPurchaseTransaction(Transaction transaction) throws ProductNotFoundException, InsuficientInventoryException {
        return this.productService.withdrawFromProductInventory(transaction.getProduct().getId());
    }
}
