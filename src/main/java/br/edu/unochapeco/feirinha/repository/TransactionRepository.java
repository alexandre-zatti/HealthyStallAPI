package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
