package ozzjpa.orderservice.bootstrap;

import ozzjpa.orderservice.domain.Customer;
import ozzjpa.orderservice.domain.Product;
import ozzjpa.orderservice.domain.ProductStatus;
import ozzjpa.orderservice.repositories.CustomerRepository;
import ozzjpa.orderservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BootstrapOrderService bootstrapOrderService;

    @Autowired
    ProductService productService;

    @Override
    public void run(String... args) throws Exception {

        updateProduct();

        bootstrapOrderService.readOrderData();

        Customer customer = new Customer();
        customer.setCustomerName("Testing Version");
        Customer savedCustomer = customerRepository.save(customer);
        System.out.println("Version is: " + savedCustomer.getVersion());

        savedCustomer.setCustomerName("Testing Version 2");
        Customer savedCustomer2 = customerRepository.save(savedCustomer);
        System.out.println("Version is: " + savedCustomer2.getVersion());

        savedCustomer2.setCustomerName("Testing Version 3");
        Customer savedCustomer3 = customerRepository.save(savedCustomer2);
        System.out.println("Version is: " + savedCustomer3.getVersion());

        customerRepository.delete(savedCustomer3);
    }

    private void updateProduct() {
        Product product = new Product();
        product.setDescription("My Product");
        product.setProductStatus(ProductStatus.NEW);
        Product savedProduct = productService.saveProduct(product);
        Product savedProduct2 = productService.updateQOH(savedProduct.getId(), 25);
        System.out.println("Updated QTY: " + savedProduct2.getQuantityOnHand());
    }

}
