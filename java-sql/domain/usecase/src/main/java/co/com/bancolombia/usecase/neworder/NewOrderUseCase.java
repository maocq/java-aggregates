package co.com.bancolombia.usecase.neworder;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.order.Client;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.gateways.ClientService;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.CLIENT_DOES_NOT_EXIST;


@RequiredArgsConstructor
public class NewOrderUseCase {

    private final OrderRepository orderRepository;
    private final ClientService clientService;

    public Order execute(NewOrderRequest request) {
        var client = getClient(request.client());
        return orderRepository.saveOrder(Order.init(client));
    }

    private Client getClient(String clientId) {
        return clientService.get(clientId)
                .orElseThrow(() -> new BusinessException(CLIENT_DOES_NOT_EXIST));
    }
}
