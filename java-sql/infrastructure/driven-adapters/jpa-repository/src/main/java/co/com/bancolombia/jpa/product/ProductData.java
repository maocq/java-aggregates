package co.com.bancolombia.jpa.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class ProductData {

    @Id
    private String id;
    private String name;
    private BigDecimal price;
}
