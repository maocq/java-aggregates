package co.com.bancolombia.model.order.gateways;

import co.com.bancolombia.model.order.Order;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> find(int orderId);
    Order saveOrder(Order order);
}
