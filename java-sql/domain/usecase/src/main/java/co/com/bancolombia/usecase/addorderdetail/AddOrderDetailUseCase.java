package co.com.bancolombia.usecase.addorderdetail;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.Product;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import co.com.bancolombia.model.order.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ORDER_DOES_NOT_EXIST;
import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.PRODUCT_DOES_NOT_EXIST;

@RequiredArgsConstructor
public class AddOrderDetailUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository producRepository;

    public Order execute(int orderId, AddOrderDetailRequest request) {
        var order = getOrder(orderId);
        var product = getProduct(request.product());

        var newOrder = order.addDetail(product, request.quantity());
        return orderRepository.saveOrder(newOrder);
    }

    private Order getOrder(int orderId) {
        return orderRepository.find(orderId)
                .orElseThrow(() -> new BusinessException(ORDER_DOES_NOT_EXIST));
    }

    private Product getProduct(String productId) {
        return producRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(PRODUCT_DOES_NOT_EXIST));
    }
}
