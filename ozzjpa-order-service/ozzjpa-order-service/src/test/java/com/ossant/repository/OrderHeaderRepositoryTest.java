package com.ossant.repository;

import com.ossant.domain.OrderHeader;
import com.ossant.domain.OrderLine;
import com.ossant.domain.Product;
import com.ossant.domain.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setup() {
       Product newProduct = new Product();
       newProduct.setProductStatus(ProductStatus.NEW);
       newProduct.setDescription("Test Product");
       product = productRepository.saveAndFlush(newProduct);
    }

    @Test
    void saveOrderWithLine() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomerName("New Customer");

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);
        orderLine.setOrderHeader(orderHeader);

        orderHeader.setOrderLines(Set.of(orderLine));

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertEquals(savedOrder.getOrderLines().size(), 1);

        OrderHeader fetchedOrder = orderHeaderRepository
                .findById(savedOrder.getId())
                .orElse(null);

        assertNotNull(fetchedOrder);
        assertEquals(fetchedOrder.getOrderLines().size(), 1);
    }


    @Test
    void saveOrderTest() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomerName("New Customer");
        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());

        OrderHeader fetchedOrder = orderHeaderRepository
                .findById(savedOrder.getId()).orElse(null);

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
    }

}
