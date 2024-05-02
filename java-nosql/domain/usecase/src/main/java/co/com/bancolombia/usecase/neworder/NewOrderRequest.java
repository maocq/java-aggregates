package co.com.bancolombia.usecase.neworder;

import co.com.bancolombia.model.validation.ValidationService;

import static co.com.bancolombia.model.validation.ValidationService.validate;

public record NewOrderRequest(String client) {
    public NewOrderRequest {
        validate(client, ValidationService::isBlankString, "Invalid client");
    }
}
