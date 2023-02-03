package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Feirante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeiranteRepository extends JpaRepository<Feirante, Long> {
}
