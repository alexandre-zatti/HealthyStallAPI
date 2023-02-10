package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);
}
