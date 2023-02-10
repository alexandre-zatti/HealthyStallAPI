package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Feirante;
import br.edu.unochapeco.feirinha.entity.Person;
import br.edu.unochapeco.feirinha.entity.Product;
import br.edu.unochapeco.feirinha.entity.Transaction;
import br.edu.unochapeco.feirinha.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FeiranteRepository feiranteRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProductRepository productRepository;

    private Feirante feirante;

    private Person person;

    private Product product;

    private Transaction transaction;

    @BeforeEach
    public void setup() {

        this.person = personRepository.save(Person.builder()
                .username("Person test")
                .balance(10.00)
                .pixQrcode("qrcodepixteste")
                .profileImgPath("/path/test")
                .build());

        this.feirante = feiranteRepository.save(Feirante.builder()
                .person(this.person)
                .active(true)
                .build());

        this.product = productRepository.save(Product.builder()
                .title("Product test")
                .description("Product description")
                .active(true)
                .price(10.00)
                .inventory(10)
                .thumbnail_path("/path/test")
                .build());

        this.transaction = Transaction.builder()
                .person(this.person)
                .product(this.product)
                .feirante(this.feirante)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        transactionRepository.save(this.transaction);
    }

    @Test
    public void givenNewTransaction_whenSavingNewTransaction_thenReturnSavedTransaction() {

        var savedTransaction = transactionRepository.save(this.transaction);

        assertThat(savedTransaction.getId())
                .isNotNull();
    }

    @Test
    public void givenTwoOrMoreTransactions_whenFetchingAll_thenReturnListWithAllTransactions() {
        var anotherTransaction = Transaction.builder()
                .person(this.person)
                .product(this.product)
                .feirante(this.feirante)
                .transactionType(TransactionType.DEPOSIT)
                .build();

        transactionRepository.save(anotherTransaction);

        var transactionList = transactionRepository.findAll();

        assertThat(transactionList)
                .hasSize(2)
                .hasOnlyElementsOfType(Transaction.class);

    }

    @Test
    public void givenUpdatedTransactionInfo_whenSavingTransaction_thenReturnUpdatedTransaction() {

        var oldTransaction = transactionRepository.findById(1L);

        oldTransaction.ifPresent(
            value -> {
                assertThat(value.getTransactionType())
                        .isNotNull()
                        .isEqualTo(TransactionType.DEPOSIT);

                value.setTransactionType(TransactionType.PURCHASE);

                var newTransaction = transactionRepository.save(value);

                assertThat(newTransaction.getTransactionType())
                        .isNotNull()
                        .isEqualTo(TransactionType.PURCHASE);

                assertThat(newTransaction.getCreatedAt())
                        .isEqualTo(oldTransaction.get().getCreatedAt());

                assertThat(newTransaction.getId())
                        .isEqualTo(oldTransaction.get().getId());
            }
        );
    }

    @Test
    public void givenAnTransaction_whenDeletingTransaction_thenRemoveFromDatabase(){
       var transaction = transactionRepository.findById(1L);

       transaction.ifPresent(
           value -> {
               assertThat(value)
                       .isNotNull();

               transactionRepository.deleteById(value.getId());

               var allTransactions = transactionRepository.findAll();

               assertThat(allTransactions)
                       .isEmpty();
           }
       );
    }
}
