package co.com.bancolombia.usecase.deleteorderdetail;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.ORDER_DOES_NOT_EXIST;

@RequiredArgsConstructor
public class DeleteOrderDetailUseCase {

    private final OrderRepository orderRepository;

    public Order execute(int orderId, int detailId) {
        var order = getOrder(orderId);
        var newOrder = order.removeDetail(detailId);
        return orderRepository.saveOrder(newOrder);
    }

    private Order getOrder(int orderId) {
        return orderRepository.find(orderId)
                .orElseThrow(() -> new BusinessException(ORDER_DOES_NOT_EXIST));
    }
}
