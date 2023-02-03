package br.edu.unochapeco.feirinha.repository;

import br.edu.unochapeco.feirinha.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    public void setup() {
        this.product = Product.builder()
                .price(10.00)
                .title("Product test")
                .description("Product test")
                .inventory(10)
                .thumbnail_path("/test/path")
                .active(true)
                .build();

        productRepository.save(this.product);
    }

    @Test
    public void givenNewProduct_whenSavingNewProduct_thenReturnSavedProduct() {

        var savedProduct = productRepository.save(this.product);

        assertThat(savedProduct.getId())
                .isNotNull();
    }

    @Test
    public void givenTwoOrMoreProducts_whenFetchingAll_thenReturnListWithAllProducts() {
         var anotherProduct = Product.builder()
                .price(10.00)
                .title("Product test")
                .description("Product test")
                .inventory(10)
                .thumbnail_path("/test/path")
                .active(true)
                .build();

        productRepository.save(anotherProduct);

        var productsList = productRepository.findAll();

        assertThat(productsList)
                .hasSize(2)
                .hasOnlyElementsOfType(Product.class);

    }

    @Test
    public void givenUpdatedProductInfo_whenSavingProduct_thenReturnUpdatedProduct() {

        var oldProduct = productRepository.findById(1L);

        oldProduct.ifPresent(
            value -> {
                assertThat(value.getTitle())
                        .isNotNull()
                        .isEqualTo("Product test");

                value.setTitle("Updated title!");

                var newProduct = productRepository.save(value);

                assertThat(newProduct.getTitle())
                        .isNotNull()
                        .isEqualTo("Updated username");

                assertThat(newProduct.getCreatedAt())
                        .isEqualTo(oldProduct.get().getCreatedAt());

                assertThat(newProduct.getId())
                        .isEqualTo(oldProduct.get().getId());
            }
        );
    }

    @Test
    public void givenAnProduct_whenDeletingProduct_thenRemoveFromDatabase(){
       var product = productRepository.findById(1L);

       product.ifPresent(
           value -> {
               assertThat(value)
                       .isNotNull();

               productRepository.deleteById(value.getId());

               var allPersons = productRepository.findAll();

               assertThat(allPersons)
                       .isEmpty();
           }
       );
    }
}
