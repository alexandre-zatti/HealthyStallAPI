package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
