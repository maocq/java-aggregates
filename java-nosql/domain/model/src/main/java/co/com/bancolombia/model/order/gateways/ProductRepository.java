package co.com.bancolombia.model.order.gateways;

import co.com.bancolombia.model.order.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {

    Mono<Product> findById(String id);
}
