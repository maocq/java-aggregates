package co.com.bancolombia.model.order;

import co.com.bancolombia.model.InmCollection;
import co.com.bancolombia.model.exceptions.BusinessException;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

import static co.com.bancolombia.model.exceptions.message.BusinessErrorMessage.*;
import static co.com.bancolombia.model.validation.ValidationService.validate;

@Builder(toBuilder = true)
public record Order(Integer id, Client client, LocalDateTime date, List<DetailProduct> detail, BigDecimal total, State state) {

    public Order(Integer id, Client client, LocalDateTime date, List<DetailProduct> detail, BigDecimal total, State state) {
        //Añadir validaciones
        validate(client, Objects::isNull, "Invalid client");

        this.id = id;
        this.client = client;
        this.date = date;
        this.detail = detail == null ? List.of() : detail;
        this.total = total == null
                ? this.detail.stream().reduce(BigDecimal.ZERO, (acc, dp) -> acc.add(dp.totalPrice()), BigDecimal::add)
                : total;
        this.state = state == null ? State.STARTED : state;
    }


    public Order addDetail(Product product, int quantity) {
        validateStatusForModification();

        DetailProduct detailProduct = DetailProduct.builder().product(product).quantity(quantity).build();
        List<DetailProduct> newDetails = InmCollection.add(detail, detailProduct);

        return this.toBuilder()
                .detail(newDetails)
                .total(total.add(detailProduct.totalPrice())).build();
    }


    public Order removeDetail(int id) {
        validateStatusForModification();

        DetailProduct oneDetail = detail.stream().filter(dp -> dp.id().equals(id)).findFirst()
                .orElseThrow(() -> new BusinessException(ORDER_DETAIL_DOES_NOT_EXIST));

        List<DetailProduct> newDetails = InmCollection.delete(detail, oneDetail);
        return this.toBuilder()
                .detail(newDetails)
                .total(total.subtract(oneDetail.totalPrice())).build();
    }

    public Order finalizeOrder() {
        validateStatusForModification();
        if (detail.isEmpty())
            throw new BusinessException(EMPTY_ORDER);

        return this.toBuilder().state(State.FINISHED).build();
    }

    private void validateStatusForModification() {
        if (state == State.FINISHED)
            throw new BusinessException(INVALID_ORDER_STATE);
    }

    public static Order init(Client client) {
        return Order.builder()
                .client(client)
                .date(LocalDateTime.now(ZoneId.of("America/Bogota")))
                .build();
    }

    public enum State {
        STARTED, FINISHED
    }
}