package co.com.bancolombia.usecase.deleteorderdetail;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ORDER_DOES_NOT_EXIST;

@RequiredArgsConstructor
public class DeleteOrderDetailUseCase {

    private final OrderRepository orderRepository;

    public Mono<Order> execute(String orderId, String detailId) {
        return getOrder(orderId)
                .flatMap(order -> {
                    var newOrder = order
                            .removeDetail(detailId);
                    return orderRepository.saveOrder(newOrder);
                });
    }

    private Mono<Order> getOrder(String orderId) {
        return orderRepository.find(orderId)
                .switchIfEmpty(Mono.error(() -> new BusinessException(ORDER_DOES_NOT_EXIST)));
    }
}
