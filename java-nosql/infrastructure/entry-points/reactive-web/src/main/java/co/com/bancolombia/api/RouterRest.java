package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/order/insecure"), handler::listenSaveInsecureOrder)
                .andRoute(GET("/api/order/{orderId}"), handler::listenGetOrder)
                .andRoute(POST("/api/order"), handler::listenNewOrder)
                .andRoute(POST("/api/order/{orderId}/detail"), handler::listenAddOrderDetail)
                .andRoute(DELETE("/api/order/{orderId}/detail/{detailId}"), handler::listenDeleteOrderDetail);
    }
}
