package ozzjpa.orderservice.services;

import ozzjpa.orderservice.domain.Product;

public interface ProductService {

    Product saveProduct(Product product);

    Product updateQOH(Long id, Integer quantityOnHand);

}
