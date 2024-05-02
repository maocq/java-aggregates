package co.com.bancolombia.jpa.order.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "detail_products")
public class DetailProductData {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderData order;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    private int quantity;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
}
