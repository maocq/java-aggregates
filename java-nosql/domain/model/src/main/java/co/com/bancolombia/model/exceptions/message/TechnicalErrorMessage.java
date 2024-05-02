package co.com.bancolombia.model.exceptions.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TechnicalErrorMessage {
    TECHNICAL_RESTCLIENT_ERROR("XXT0010","Technical error rest client"),
    EXTERNAR_MESSAGE_ERROR("XXT9999", "Error");

    private final String code;
    private final String message;
}
