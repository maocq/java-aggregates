package co.com.bancolombia.mongo.order;

import co.com.bancolombia.model.order.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface OrderCollection extends ReactiveMongoRepository<Order, String> { }
