package co.com.bancolombia.usecase.addorderdetail;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.Product;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import co.com.bancolombia.model.order.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ORDER_DOES_NOT_EXIST;
import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.PRODUCT_DOES_NOT_EXIST;

@RequiredArgsConstructor
public class AddOrderDetailUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository producRepository;

    public Mono<Order> execute(String orderId, AddOrderDetailRequest request) {
        return getOrder(orderId)
                .flatMap(order -> getProduct(request.product())
                .flatMap(product -> {
                    var newOrder = order
                            .addDetail(product, request.quantity());
                    return orderRepository.saveOrder(newOrder);
                }));
    }

    private Mono<Order> getOrder(String orderId) {
        return orderRepository.find(orderId)
                .switchIfEmpty(Mono.error(() -> new BusinessException(ORDER_DOES_NOT_EXIST)));
    }

    private Mono<Product> getProduct(String productId) {
        return producRepository.findById(productId)
                .switchIfEmpty(Mono.error(() -> new BusinessException(PRODUCT_DOES_NOT_EXIST)));
    }
}
