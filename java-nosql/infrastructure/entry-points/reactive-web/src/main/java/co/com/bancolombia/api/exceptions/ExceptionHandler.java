package co.com.bancolombia.api.exceptions;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Slf4j
@Order(-2)
@Component
public class ExceptionHandler extends AbstractErrorWebExceptionHandler {

    public ExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties resources,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources.getResources(), applicationContext);
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        return Mono.error(getError(request))
                .onErrorResume(BusinessException.class, error -> {
                    log.warn("BusinessException", error);
                    var response = new ErrorResponse(error.getErrorMessage().getCode(), error.getMessage());
                    return ServerResponse.badRequest().bodyValue(response);
                })
                .onErrorResume(TechnicalException.class, error -> {
                    log.error("TechnicalException", error);
                    var response = new ErrorResponse(error.getErrorMessage().getCode(), error.getMessage());
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).bodyValue(response);
                })
                .onErrorResume(ResponseStatusException.class, error -> {
                    log.warn("ResponseStatusException", error);
                    var response = new ErrorResponse(Integer.toString(HttpStatus.BAD_REQUEST.value()), error.getMessage());
                    return ServerResponse.status(error.getStatusCode()).bodyValue(response);
                })
                .onErrorResume(error -> {
                    log.error("Internal server error", error);
                    var response = new ErrorResponse(Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            "Internal server error");
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .bodyValue(response);
                })
                .cast(ServerResponse.class);
    }
}