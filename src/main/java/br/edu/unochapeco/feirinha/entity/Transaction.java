package br.edu.unochapeco.feirinha.entity;

import br.edu.unochapeco.feirinha.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @NotNull
    private Person person;

    @ManyToOne
    private Product product;

    @DecimalMin(value = "0.00", message = "O valor do deposito nao pode ser menor que 0!")
    @Digits(integer=4, fraction=2)
    @Column(columnDefinition = "NUMERIC", precision = 2, scale = 10)
    private Double deposit = 0.00;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType transactionType;

    @CreationTimestamp
    private Date createdAt;
}
