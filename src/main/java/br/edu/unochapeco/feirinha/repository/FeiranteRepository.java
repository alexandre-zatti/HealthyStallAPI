package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Feirante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeiranteRepository extends JpaRepository<Feirante, Long> {

    @Query("SELECT f FROM Feirante f WHERE f.active = true")
    Feirante getCurrentFeirante();
}
