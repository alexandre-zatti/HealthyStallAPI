package br.edu.unochapeco.feirinha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private TransactionType transactionType;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Feirante feirante;

    @CreationTimestamp
    private Date createdAt;
}
