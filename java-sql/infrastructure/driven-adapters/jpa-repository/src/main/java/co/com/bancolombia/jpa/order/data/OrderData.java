package co.com.bancolombia.jpa.order.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class OrderData {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_name")
    private String clientName;
    private LocalDateTime date;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailProductData> detail;
    private BigDecimal total;
    private String state;
}
