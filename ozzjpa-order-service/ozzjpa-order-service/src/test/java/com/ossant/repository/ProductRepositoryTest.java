package com.ossant.repository;

import com.ossant.domain.Product;
import com.ossant.domain.ProductStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void saveProductTest() {
        Product product = new Product();
        product.setDescription("Test Product Description");
        product.setProductStatus(ProductStatus.NEW);
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getId());

        Product fetchedProduct = productRepository
                .findById(savedProduct.getId()).orElse(null);

        assertNotNull(fetchedProduct);
        assertNotNull(fetchedProduct.getId());
        assertNotNull(fetchedProduct.getCreatedDate());
        assertNotNull(fetchedProduct.getLastModifiedDate());
    }

}
