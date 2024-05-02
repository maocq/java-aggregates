package co.com.bancolombia.jpa.order;

import co.com.bancolombia.jpa.order.data.DetailProductData;
import co.com.bancolombia.jpa.order.data.OrderData;
import co.com.bancolombia.model.order.Client;
import co.com.bancolombia.model.order.DetailProduct;
import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.model.order.Product;
import co.com.bancolombia.model.order.gateways.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDbRepository implements OrderRepository {

    private final OrderTable orderTable;

    @Override
    public Optional<Order> find(int orderId) {
        return orderTable.findById(orderId)
                .map(this::toEntity);
    }

    @Override
    public Order saveOrder(Order order) {
        var result = orderTable.save(toData(order));
        return toEntity(result);
    }

    private Order toEntity(OrderData data) {
        var client = new Client(data.getClientId(), data.getClientName());
        var detail = data.getDetail().stream()
                .map(d -> {
                    var product = new Product(d.getProductId(), d.getProductName(), d.getProductPrice());
                    return new DetailProduct(d.getId(), product, d.getQuantity(), d.getTotalPrice());
                }).toList();
        return new Order(data.getId(), client, data.getDate(), detail,
                data.getTotal(), Order.State.valueOf(data.getState()));
    }

    private OrderData toData(Order entity) {
        var orderData = new OrderData(
                entity.id(), entity.client().id(), entity.client().name(), entity.date(), List.of(),
                entity.total(), entity.state().name());

        var detail = entity.detail().stream()
                .map(d -> new DetailProductData(d.id(), orderData, d.product().id(), d.product().name(),
                        d.product().price(), d.quantity(), d.totalPrice())).toList();

        orderData.setDetail(detail);
        return orderData;
    }
}
