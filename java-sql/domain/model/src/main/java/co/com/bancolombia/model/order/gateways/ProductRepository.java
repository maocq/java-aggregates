package co.com.bancolombia.model.order.gateways;

import co.com.bancolombia.model.order.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String id);
}
