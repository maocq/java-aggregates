package co.com.bancolombia.mongo.product;

import co.com.bancolombia.model.order.Product;
import co.com.bancolombia.model.order.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class ProductMongoRepository implements ProductRepository {

    private final ProductCollection productCollection;

    @Override
    public Mono<Product> findById(String productId) {
        return productCollection.findById(productId);
    }
}
