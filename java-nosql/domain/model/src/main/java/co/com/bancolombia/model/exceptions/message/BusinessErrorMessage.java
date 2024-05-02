package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {
    INVALID_REQUEST("XXB0001", "Invalid request"),
    DATA_VALIDATION_ERROR("XXB0002", "Validation data error"),
    CLIENT_DOES_NOT_EXIST("XXB0003", "Cient doesn't exist"),
    PRODUCT_DOES_NOT_EXIST("XXB0004", "Product doesn't exist"),
    ORDER_DOES_NOT_EXIST("XXB0005", "Order doesn't exist"),
    ORDER_DETAIL_DOES_NOT_EXIST("XXB0006", "Order detail doesn't exist"),
    INVALID_ORDER_STATE("XXB0007", "Order doesn't exist"),
    EMPTY_ORDER("XXB0007", "Empty order");

    private final String code;
    private final String message;
}
