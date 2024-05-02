package co.com.bancolombia.api;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.usecase.addorderdetail.AddOrderDetailRequest;
import co.com.bancolombia.usecase.addorderdetail.AddOrderDetailUseCase;
import co.com.bancolombia.usecase.deleteorderdetail.DeleteOrderDetailUseCase;
import co.com.bancolombia.usecase.neworder.NewOrderRequest;
import co.com.bancolombia.usecase.neworder.NewOrderUseCase;
import co.com.bancolombia.usecase.saveorder.SaveOrderUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final SaveOrderUseCase saveOrderUseCase;
    private final NewOrderUseCase newOrderUseCase;
    private final AddOrderDetailUseCase addOrderDetailUseCase;
    private final DeleteOrderDetailUseCase deleteOrderDetailUseCase;

    @PostMapping(path = "/order/insecure")
    public Order saveInsecureOrder(@RequestBody Order request) {
        return saveOrderUseCase.execute(request);
    }

    @PostMapping(path = "/order")
    public Order newOrder(@RequestBody NewOrderRequest request) {
        return newOrderUseCase.execute(request);
    }

    @PostMapping(path = "/order/{orderId}/detail")
    public Order addOrderDetail(@PathVariable("orderId") int orderId, @RequestBody AddOrderDetailRequest request) {
        return addOrderDetailUseCase.execute(orderId, request);
    }

    @DeleteMapping(path = "/order/{orderId}/detail/{detailId}")
    public Order deleteOrderDetail(@PathVariable("orderId") int orderId, @PathVariable("detailId") int detailId) {
        return deleteOrderDetailUseCase.execute(orderId, detailId);
    }
}
