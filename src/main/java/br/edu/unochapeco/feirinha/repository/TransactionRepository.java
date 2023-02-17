package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByCreatedAtBetween(Date fromDate, Date toDate, Pageable pageable);

    @Query("SELECT t FROM Transaction t JOIN Person p ON t.person.Id = p.Id WHERE p.Id = :personId")
    Page<Transaction> findByPersonId(Long personId, Pageable pageable);

    @Query("SELECT t FROM Transaction t JOIN Person p ON t.person.Id = p.Id WHERE p.Id = :personId AND t.createdAt BETWEEN :fromDate AND :toDate")
    Page<Transaction> findByPersonIdAndCreatedAtBetween(Long personId, Date fromDate, Date toDate, Pageable pageable);
}
