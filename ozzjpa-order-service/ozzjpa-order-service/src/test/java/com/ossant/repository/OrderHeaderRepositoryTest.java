package com.ossant.repository;

import com.ossant.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    Product product;

    Customer customer;

    @BeforeEach
    void setup() {
       Product newProduct = new Product();
       newProduct.setProductStatus(ProductStatus.NEW);
       newProduct.setDescription("Test Product");
       product = productRepository.saveAndFlush(newProduct);

       Address address = new Address();
       address.setAddress("test address");
       address.setCity("test city");
       address.setState("test state");
       address.setZipCode("test zipcode");

       Customer newCustomer = new Customer();
       newCustomer.setEmail("customertest@gmail.com");
       newCustomer.setPhone("9999-9999");
       newCustomer.setAddress(address);
       customer = customerRepository.saveAndFlush(newCustomer);
    }

    @Test
    void deleteCascadeTest() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(3);
        orderLine.setProduct(product);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("Me");

        orderHeader.setOrderApproval(orderApproval);
        orderHeader.addOrderLine(orderLine);

        OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

        orderHeaderRepository.deleteById(savedOrder.getId());
        orderHeaderRepository.flush();

        OrderHeader fetchedOrder = orderHeaderRepository
                .findById(savedOrder.getId()).orElse(null);

        assertNull(fetchedOrder);
    }

    @Test
    void saveOrderWithLine() {

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        OrderLine orderLine = new OrderLine();
        orderLine.setQuantityOrdered(5);
        orderLine.setProduct(product);

        //orderLine.setOrderHeader(orderHeader);
        //orderHeader.setOrderLines(Set.of(orderLine));
        orderHeader.addOrderLine(orderLine);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("Me");

        orderHeader.setOrderApproval(orderApproval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
        orderHeaderRepository.flush();

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertEquals(savedOrder.getOrderLines().size(), 1);
        assertNotNull(savedOrder.getOrderApproval().getId());

        OrderHeader fetchedOrder = orderHeaderRepository
                .findById(savedOrder.getId())
                .orElse(null);

        assertNotNull(fetchedOrder);
        assertEquals(fetchedOrder.getOrderLines().size(), 1);
        assertNotNull(fetchedOrder.getOrderApproval().getId());
    }


    @Test
    void saveOrderTest() {
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        OrderApproval orderApproval = new OrderApproval();
        orderApproval.setApprovedBy("Me");

        orderHeader.setOrderApproval(orderApproval);

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

        assertNotNull(savedOrder);
        assertNotNull(savedOrder.getId());
        assertNotNull(savedOrder.getOrderApproval().getId());

        OrderHeader fetchedOrder = orderHeaderRepository
                .findById(savedOrder.getId()).orElse(null);

        assertNotNull(fetchedOrder);
        assertNotNull(fetchedOrder.getId());
        assertNotNull(fetchedOrder.getCreatedDate());
        assertNotNull(fetchedOrder.getLastModifiedDate());
        assertNotNull(fetchedOrder.getOrderApproval().getId());
    }

}
