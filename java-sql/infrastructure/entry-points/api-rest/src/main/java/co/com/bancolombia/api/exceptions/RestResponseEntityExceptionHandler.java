package co.com.bancolombia.api.exceptions;

import co.com.bancolombia.model.exceptions.BusinessException;
import co.com.bancolombia.model.exceptions.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = { BusinessException.class })
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        log.error("Business exception", ex);

        var response = ErrorResponse.builder()
                .code(ex.getErrorMessage().getCode())
                .message(ex.getErrorMessage().getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = { TechnicalException.class })
    protected ResponseEntity<ErrorResponse> handleTechnicalException(TechnicalException ex, WebRequest request) {
        log.error("Technical exception", ex);

        var response = ErrorResponse.builder()
                .code(ex.getErrorMessage().getCode())
                .message(ex.getErrorMessage().getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = { HttpMessageConversionException.class })
    protected ResponseEntity<ErrorResponse> handleHttpMessageException(Exception ex, WebRequest request) {
        log.error("HttpMessage error", ex);

        var response = ErrorResponse.builder()
                .code(Integer.toString(HttpStatus.BAD_REQUEST.value()))
                .message(HttpStatus.BAD_REQUEST.toString()).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<ErrorResponse> handleError(Exception ex, WebRequest request) {
        log.error("Internal error", ex);

        var response = ErrorResponse.builder()
                .code("500")
                .message("Internal error").build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}