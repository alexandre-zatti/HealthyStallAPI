package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
