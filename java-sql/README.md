# Java Sql

docker run --name postgres-db -e POSTGRES_PASSWORD=pass -d -p 5432:5432 postgres


```sql
CREATE TABLE products (
    id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(20, 2) NOT NULL
);

INSERT INTO products (id, name, price) VALUES('0001', 'Product', 100.00);


CREATE TABLE orders (
    id SERIAL,
    client_id VARCHAR(20) NOT NULL,
    client_name VARCHAR(100) NOT NULL,
    date TIMESTAMP WITH TIME ZONE NOT NULL,
    total NUMERIC(20, 2) NOT NULL,
    state VARCHAR(20) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE detail_products (
    id SERIAL,
    order_id INTEGER NOT NULL,
    product_id VARCHAR(20) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    product_price NUMERIC(20, 2) NOT NULL,
    quantity INT NOT NULL,
    total_price NUMERIC(20, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_detail_products_order FOREIGN KEY (order_id) REFERENCES orders (id),
    CONSTRAINT fk_detail_products_product FOREIGN KEY (product_id) REFERENCES products (id)
);

INSERT INTO public.orders (client_id, client_name, "date", total, state)
VALUES('123', 'Foo', '2024-05-02 08:21:21.035', 200.00, 'STARTED');

INSERT INTO public.detail_products (order_id, product_id, product_name, product_price, quantity, total_price)
VALUES(1, '0001', 'Product', 100.00, 2, 200.00);
```

---


### Save order

Llamado inseguro ya que no cuenta con validación de datos antes de insertar/actualizar (cliente, productos, precio ...)
```sh
curl --location 'http://localhost:8080/api/order/insecure' \
--header 'Content-Type: application/json' \
--data '{
    "id": 22,
    "client": {
        "id": "123",
        "name": "Foo"
    },
    "date": "2024-05-02T10:57:21.716307",
    "detail": [
        {
            "id": null,
            "product": {
                "id": "0001",
                "name": "Product",
                "price": 100.00
            },
            "quantity": 1,
            "totalPrice": 100.00
        },
        {
            "id": null,
            "product": {
                "id": "0001",
                "name": "Product",
                "price": 100.00
            },
            "quantity": 1,
            "totalPrice": 100.00
        }
    ],
    "total": 102.00,
    "state": "STARTED"
}'
```
```sql
select ... from orders od1_0 left join detail_products d1_0 on od1_0.id=d1_0.order_id where od1_0.id=? # Consulta internamente para luego validar
select ... from detail_products dpd1_0 where dpd1_0.id=?                                               # Consulta internamente para luego validar
insert into detail_products (order_id,product_id,product_name,product_price,quantity,total_price) values (?,?,?,?,?,?) # Inserta nuevo, actualiza y elimina anteriores
insert into detail_products (order_id,product_id,product_name,product_price,quantity,total_price) values (?,?,?,?,?,?)
update orders set client_id=?,client_name=?,date=?,state=?,total=? where id=?
delete from detail_products where id=?
delete from detail_products where id=?
```

### New order
```sh
curl --location 'http://localhost:8080/api/order' \
--header 'Content-Type: application/json' \
--data '{
    "client" : "123"
}'
```
```sql
insert into orders (client_id,client_name,date,state,total) values (?,?,?,?,?)
```

### Add detail
```sh
curl --location 'http://localhost:8080/api/order/17/detail' \
--header 'Content-Type: application/json' \
--data '{
    "product" : "0001",
    "quantity" : 1
}'
```

```sql
select od1_0.id,od1_0.client_id, ... from orders od1_0 left join detail_products d1_0 on od1_0.id=d1_0.order_id where od1_0.id=? #Consulta Orden (Valida que exista)
select pd1_0.id,pd1_0.name,pd1_0.price from products pd1_0 where pd1_0.id=?                                                      #Consulta Producto (Valida que exista)
insert into detail_products (order_id,product_id,product_name,product_price,quantity,total_price) values (?,?,?,?,?,?)           #Inserta detalle
update orders set client_id=?,client_name=?,date=?,state=?,total=? where id=?                                                    #Actualiza total
```

### Delete detail
```sh
curl --location --request DELETE 'http://localhost:8080/api/order/17/detail/12'
```

```sql
select od1_0.id,od1_0.client_id, ... from orders od1_0 left join detail_products d1_0 on od1_0.id=d1_0.order_id where od1_0.id=? #Consulta Orden (Valida que exista)
update orders set client_id=?,client_name=?,date=?,state=?,total=? where id=?                                                    #Actualiza total
delete from detail_products where id=?                                                                                           #Elimina el detalle
```



### Json Query
```sql
SELECT json_build_object(
               'id', ord.id,
               'client', json_build_object(
                       'id', ord.client_id,
                       'name', ord.client_name
                         ),
               'detail', json_agg(
                       json_build_object(
                               'id', dp.id,
                               'product', json_build_object(
                                       'id', dp.product_id,
                                       'name', dp.product_name,
                                       'price', dp.product_price,
                                       'quantity', dp.quantity
                                          ),
                               'totalPrice', dp.total_price
                       )
                         )
       )
FROM orders ord
         JOIN detail_products dp ON dp.order_id = ord.id
WHERE ord.id = 2
GROUP BY ord.id
```