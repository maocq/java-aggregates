package co.com.bancolombia.usecase.querys;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class QueryUseCase {

    private final OrderRepository orderRepository;

    public Mono<Order> getOrderById(String orderId) {
        return orderRepository.find(orderId);
    }
}
