package co.com.bancolombia.model.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record Product(String id, String name, BigDecimal price) {

    public Product {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Invalid price");
    }
}