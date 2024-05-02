package co.com.bancolombia.mongo.product;

import co.com.bancolombia.model.order.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductCollection extends ReactiveMongoRepository<Product, String> { }
