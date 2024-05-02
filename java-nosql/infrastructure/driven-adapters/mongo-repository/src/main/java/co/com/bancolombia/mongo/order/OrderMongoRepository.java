package co.com.bancolombia.mongo.order;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class OrderMongoRepository implements OrderRepository {

    private final OrderCollection orderCollection;

    @Override
    public Mono<Order> find(String orderId) {
        return orderCollection.findById(orderId);
    }

    @Override
    public Mono<Order> saveOrder(Order order) {
        return orderCollection.save(order);
    }
}
