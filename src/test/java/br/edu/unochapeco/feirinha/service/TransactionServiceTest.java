package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Feirante;
import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.entity.Product;
import br.edu.unochapeco.feirinha.entity.Transaction;
import br.edu.unochapeco.feirinha.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private TransactionService transactionService;

    private Person person;
    private Product product;
    private Transaction transactionDeposit;
    private Transaction transactionPurchase;

    @BeforeEach
    public void setup(){
        this.person = Person.builder()
                .Id(1L)
                .username("Person test")
                .balance(10.00)
                .profileImgPath("/test/path")
                .build();

        this.product = Product.builder()
                .Id(1L)
                .title("Product test")
                .description("Product description")
                .price(10.00)
                .inventory(0)
                .active(true)
                .thumbnail_path("/path/test")
                .build();

        this.transactionDeposit = Transaction
                .builder()
                .Id(1L)
                .person(this.person)
                .transactionType(TransactionType.DEPOSIT)
                .deposit(10.00)
                .build();

        this.transactionPurchase = Transaction
                .builder()
                .Id(1L)
                .person(this.person)
                .product(this.product)
                .transactionType(TransactionType.PURCHASE)
                .build();
    }

    @Test
    public void givenNewDepositTransaction_whenCreateTransaction_thenReturnTransaction(){

    }
}