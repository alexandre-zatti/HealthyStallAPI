package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Product;
import br.edu.unochapeco.feirinha.exception.InsuficientInventoryException;
import br.edu.unochapeco.feirinha.exception.ProductNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.repository.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.List;
import java.util.Map;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final Validator validator;

    ProductService(ProductRepository productRepository, Validator validator){
        this.productRepository = productRepository;
        this.validator = validator;
    }

    public Product createProduct(Product newProduct){
        return this.productRepository.save(newProduct);
    }

    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Product getProductById(Long id) throws ProductNotFoundException {

        var product = this.productRepository.findById(id);

        if(product.isPresent()){
            return product.get();
        }

        throw new ProductNotFoundException();
    }

    public Product updateProduct(Long id, Map<String, Object> fields)
            throws ProductNotFoundException, ConstraintViolationException {

        var product = this.getProductById(id);

        for (Map.Entry<String, Object> entry: fields.entrySet()) {
            var field = ReflectionUtils.findField(Product.class, entry.getKey());

            if (field != null){
                field.setAccessible(true);
                ReflectionUtils.setField(field, product, entry.getValue());
            }
        }

        var violations = validator.validate(product);

        if(!violations.isEmpty()){
            throw new ConstraintViolationException(violations);
        }

        return this.productRepository.save(product);
    }

    public void deleteProduct(Long id) throws ProductNotFoundException {
        var product = this.getProductById(id);
        this.productRepository.deleteById(product.getId());
    }

    public Product withdrawFromProductInventory(Long id) throws ProductNotFoundException, InsuficientInventoryException {
        var product = this.getProductById(id);

        if (product.getInventory() - 1 < 0){
            throw new InsuficientInventoryException();
        }

        product.setInventory(product.getInventory() - 1);

        return this.productRepository.save(product);
    }
}
