package co.com.bancolombia.jpa.order;

import co.com.bancolombia.jpa.order.data.OrderData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface OrderTable extends CrudRepository<OrderData, Integer>, QueryByExampleExecutor<OrderData> { }