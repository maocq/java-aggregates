package co.com.bancolombia.model.exceptions;

import co.com.bancolombia.model.exceptions.message.BusinessErrorMessage;

public class BusinessDetailException extends BusinessException {
    private final String detail;

    public BusinessDetailException(BusinessErrorMessage errorMessage, String detail) {
        super(errorMessage);
        this.detail = detail;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": " + detail;
    }
}
