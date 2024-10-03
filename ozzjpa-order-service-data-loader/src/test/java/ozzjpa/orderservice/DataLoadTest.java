package ozzjpa.orderservice;

import guru.springframework.orderservice.domain.*;
import ozzjpa.orderservice.domain.*;
import ozzjpa.orderservice.repositories.CustomerRepository;
import ozzjpa.orderservice.repositories.OrderHeaderRepository;
import ozzjpa.orderservice.repositories.ProductRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DataLoadTest {
    final String PRODUCT_D1 = "Product 1";
    final String PRODUCT_D2 = "Product 2";
    final String PRODUCT_D3 = "Product 3";

    final String TEST_CUSTOMER = "TEST CUSTOMER";

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    /**
     * From MySQL Workbench (or other client, run the following SQL statement, then test below.) Once
     * you commit, the test will complete. If test completes immediately, check autocommit settings in client.
     *
     *  {@code SELECT * FROM order_service_db.order_header where id = 1 for update; }
     */
    @Test
    void testDBLock() {
        Long id = 55l;

        OrderHeader orderHeader = orderHeaderRepository.findById(id).get();

        Address billTo = new Address();
        billTo.setAddress("Bill me");
        orderHeader.setBillToAddress(billTo);
        orderHeaderRepository.saveAndFlush(orderHeader);

        System.out.println("I updated the order");
    }

    @Test
    void testN_PlusOneProblem() {

        Customer customer = customerRepository
                .findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER).orElseGet(null);

        IntSummaryStatistics totalOrdered =  orderHeaderRepository.findAllByCustomer(customer).stream()
                .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
                .collect(Collectors.summarizingInt(OrderLine::getQuantityOrdered));

        System.out.println("Total ordered: " + totalOrdered.getSum());
    }


    @Test
    void testLazy() {
        OrderHeader orderHeader = orderHeaderRepository.findById(5L).orElseGet(null);
        System.out.println("Order Id is: " + orderHeader.getId());
        System.out.println("Customer Name is: " + orderHeader.getCustomer().getCustomerName());
    }

    @Disabled
    @Rollback(value = false)
    @Test
    void testDataLoader() {
        List<Product> products = loadProducts();
        Customer customer = loadCustomers();

        int ordersToCreate = 10000;

        for (int i = 0; i < ordersToCreate; i++){
            System.out.println("Creating order #: " + i);
            saveOrder(customer, products);
        }

        orderHeaderRepository.flush();
    }

    private OrderHeader saveOrder(Customer customer, List<Product> products){
        Random random = new Random();

        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setCustomer(customer);

        products.forEach(product -> {
            OrderLine orderLine = new OrderLine();
            orderLine.setProduct(product);
            orderLine.setQuantityOrdered(random.nextInt(20));
            //orderHeader.getOrderLines().add(orderLine);
            orderHeader.addOrderLine(orderLine);
        });

        return orderHeaderRepository.save(orderHeader);
    }

    private Customer loadCustomers() {
        return getOrSaveCustomer(TEST_CUSTOMER);
    }

    private Customer getOrSaveCustomer(String customerName) {
        return customerRepository.findCustomerByCustomerNameIgnoreCase(customerName)
                .orElseGet(() -> {
                    Customer c1 = new Customer();
                    c1.setCustomerName(customerName);
                    c1.setEmail("test@example.com");
                    Address address = new Address();
                    address.setAddress("123 Main");
                    address.setCity("New Orleans");
                    address.setState("LA");
                    c1.setAddress(address);
                    return customerRepository.save(c1);
                });
    }

    private List<Product> loadProducts(){
        List<Product> products = new ArrayList<>();

        products.add(getOrSaveProduct(PRODUCT_D1));
        products.add(getOrSaveProduct(PRODUCT_D2));
        products.add(getOrSaveProduct(PRODUCT_D3));

        return products;
    }

    private Product getOrSaveProduct(String description) {
        return productRepository.findByDescription(description)
                .orElseGet(() -> {
                    Product p1 = new Product();
                    p1.setDescription(description);
                    p1.setProductStatus(ProductStatus.NEW);
                    return productRepository.save(p1);
                });
    }

}
