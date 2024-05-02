package co.com.bancolombia.jpa.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ProductTable extends CrudRepository<ProductData, String>, QueryByExampleExecutor<ProductData> { }
