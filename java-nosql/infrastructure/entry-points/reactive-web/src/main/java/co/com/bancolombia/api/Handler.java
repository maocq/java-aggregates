package co.com.bancolombia.api;

import co.com.bancolombia.model.order.Order;
import co.com.bancolombia.usecase.addorderdetail.AddOrderDetailRequest;
import co.com.bancolombia.usecase.addorderdetail.AddOrderDetailUseCase;
import co.com.bancolombia.usecase.deleteorderdetail.DeleteOrderDetailUseCase;
import co.com.bancolombia.usecase.neworder.NewOrderRequest;
import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.usecase.neworder.NewOrderUseCase;
import co.com.bancolombia.usecase.querys.QueryUseCase;
import co.com.bancolombia.usecase.saveorder.SaveOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.INVALID_REQUEST;

@Component
@RequiredArgsConstructor
public class Handler {

    private final QueryUseCase queryUseCase;
    private final SaveOrderUseCase saveOrderUseCase;
    private final NewOrderUseCase newOrderUseCase;
    private final AddOrderDetailUseCase addOrderDetailUseCase;
    private final DeleteOrderDetailUseCase deleteOrderDetailUseCase;


    public Mono<ServerResponse> listenGetOrder(ServerRequest serverRequest) {
        String orderId = serverRequest.pathVariable("orderId");

        return queryUseCase.getOrderById(orderId)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenSaveInsecureOrder(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Order.class)
                .flatMap(saveOrderUseCase::execute)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenNewOrder(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(NewOrderRequest.class)
                .switchIfEmpty(Mono.error(() -> new BusinessException(INVALID_REQUEST)))
                .flatMap(newOrderUseCase::execute)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenAddOrderDetail(ServerRequest serverRequest) {
        String orderId = serverRequest.pathVariable("orderId");

        return serverRequest.bodyToMono(AddOrderDetailRequest.class)
                .switchIfEmpty(Mono.error(() -> new BusinessException(INVALID_REQUEST)))
                .flatMap(request -> addOrderDetailUseCase.execute(orderId, request))
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> listenDeleteOrderDetail(ServerRequest serverRequest) {
        String orderId = serverRequest.pathVariable("orderId");
        String detailId = serverRequest.pathVariable("detailId");

        return deleteOrderDetailUseCase.execute(orderId, detailId)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }
}
