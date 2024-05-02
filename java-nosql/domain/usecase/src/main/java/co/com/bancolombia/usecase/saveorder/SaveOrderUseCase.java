package co.com.bancolombia.usecase.saveorder;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SaveOrderUseCase {

    private final OrderRepository orderRepository;

    public Mono<Order> execute(Order order) {
        return orderRepository.saveOrder(order);
    }
}
