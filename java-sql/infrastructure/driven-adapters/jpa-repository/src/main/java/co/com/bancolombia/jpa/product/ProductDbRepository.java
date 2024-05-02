package co.com.bancolombia.jpa.product;

import co.com.bancolombia.model.order.Product;
import co.com.bancolombia.model.order.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductDbRepository implements ProductRepository {

    private final ProductTable productTable;

    @Override
    public Optional<Product> findById(String id) {
        return productTable.findById(id)
                .map(this::toEntity);
    }


    private Product toEntity(ProductData data) {
        return new Product(data.getId(), data.getName(), data.getPrice());
    }

    private ProductData toData(Product entity) {
        return new ProductData(entity.id(), entity.name(), entity.price());
    }
}
