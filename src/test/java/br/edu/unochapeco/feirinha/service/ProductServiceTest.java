package br.edu.unochapeco.feirinha.service;

import br.edu.unochapeco.feirinha.entity.Product;
import br.edu.unochapeco.feirinha.exception.ProductNotFoundException;
import br.edu.unochapeco.feirinha.exception.UniqueUsernameValidationException;
import br.edu.unochapeco.feirinha.repository.ProductRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Validator validator;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setup(){
       this.product = Product.builder()
               .Id(1L)
               .title("Product test")
               .description("Product description")
               .price(10.00)
               .inventory(0)
               .active(true)
               .thumbnail_path("/path/test")
               .build();
    }

    @Test
    public void givenNewProduct_whenCreateProduct_thenReturnCreatedProduct() throws UniqueUsernameValidationException {
        given(this.productRepository.save(any(Product.class)))
                .willReturn(this.product);

        var newProduct = this.productService.createProduct(this.product);

        assertThat(newProduct)
                .isNotNull()
                .isExactlyInstanceOf(Product.class);

        assertThat(newProduct.getId()).isNotNull()
                .isEqualTo(1L);
    }

    @Test
    public void givenListOfProducts_whenGetAllProducts_thenReturnListWithAllProducts(){
        given(this.productRepository.findAll())
                .willReturn(List.of(this.product, this.product));

        var listOfProducts = this.productService.getAllProducts();

        assertThat(listOfProducts)
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .hasOnlyElementsOfType(Product.class);
    }

    @Test
    public void givenEmptyListOfProducts_whenGetAllProducts_thenReturnEmptyList(){
        given(this.productRepository.findAll())
                .willReturn(Collections.emptyList());

        var listOfProducts = this.productService.getAllProducts();

        assertThat(listOfProducts)
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void givenExistingProduct_whenGetProductById_thenReturnProduct() throws ProductNotFoundException {
        given(this.productRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(this.product));

        var product = this.productService.getProductById(1L);

        assertThat(product)
                .isNotNull()
                .isExactlyInstanceOf(Product.class);

        assertThat(product.getId())
                .isNotNull()
                .isEqualTo(1);
    }

    @Test
    public void givenNonExistingProduct_whenGetProductById_thenThrowException() {
        given(this.productRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(ProductNotFoundException.class, () -> {
           this.productService.getProductById(1L);
        });
    }

    @Test
    public void givenProduct_whenUpdateProduct_thenReturnUpdatedProduct() throws ProductNotFoundException {
        given(this.productRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(this.product));

        given(this.productRepository.save(any(Product.class)))
                .willReturn(this.product);

        given(this.validator.validate(any(Product.class)))
                .willReturn(Collections.emptySet());

        var oldTitle = this.product.getTitle();
        var updatedProduct = this.productService.updateProduct(this.product.getId(), Map.of("title", "Updated title"));

        assertThat(updatedProduct.getTitle())
                .isEqualTo("Updated title");

        assertThat(updatedProduct.getTitle())
                .isNotEqualTo(oldTitle);
    }

    @Test
    public void givenInvalidProductId_whenUpdateProduct_thenThrowException(){
        given(this.productRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(ProductNotFoundException.class, () -> {
            this.productService.updateProduct(this.product.getId(), Map.of("username", "Invalid Username"));
        });
    }

    @Test
    public void givenProduct_whenDeleteProduct_thenReturnNothing() throws ProductNotFoundException {
        given(this.productRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(this.product));

        willDoNothing()
                .given(this.productRepository)
                .deleteById(any(Long.class));

        this.productService.deleteProduct(1L);

        verify(this.productRepository,times(1)).deleteById(1L);
    }

    @Test
    public void givenNonExistingProduct_whenDeleteProduct_thenThrowException() {
        given(this.productRepository.findById(any(Long.class)))
                .willReturn(Optional.empty());

        org.junit.jupiter.api.Assertions.assertThrows(ProductNotFoundException.class, () -> {
            this.productService.deleteProduct(1L);
        });
    }
}