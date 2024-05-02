package co.com.bancolombia.model.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record DetailProduct(Integer id, Product product, int quantity, BigDecimal totalPrice) {

    public DetailProduct(Integer id, Product product, int quantity, BigDecimal totalPrice) {
        //Validations ...
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice == null
                ? product.price().multiply(BigDecimal.valueOf(quantity))
                : totalPrice;
    }
}