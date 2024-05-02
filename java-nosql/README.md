# Java NoSql

export MONGODB_VERSION=6.0-ubi8
docker run --name mongodb -d -p 27017:27017 mongodb/mongodb-community-server:$MONGODB_VERSION

# docker start mongodb 

```shell
mongosh mongodb://localhost:27017
```

### Products
```mongodb-json
db.product.insertOne(
  {
    name: "Product",
    price: "100"
  }
)
```

---

Llamado inseguro ya que no cuenta con validación de datos antes de insertar/actualizar (cliente, productos, precio ...)
```shell
curl --location 'http://localhost:8080/api/order/insecure' \
--header 'Content-Type: application/json' \
--data '{
    "id": "663026e58fb4ea5f6b185f9d",
    "client": {
        "id": "123",
        "name": "Foo"
    },
    "date": "2024-04-29T18:01:57.873",
    "detail": [
        {
            "id": "39bba8e0-8e58-4593-8ceb-2a97c1b8e8bd",
            "product": {
                "id": "66300f5a47392a565cc0bf9c",
                "name": "Product",
                "price": 100
            },
            "quantity": -1,
            "totalPrice": 10
        }        
    ],
    "total": 20,
    "state": "STARTED"
}'
```

```sh
#New order
curl --location 'http://localhost:8080/api/order' \
--header 'Content-Type: application/json' \
--data '{
    "client" : "123"
}'

#Add detail
curl --location 'http://localhost:8080/api/order/6630000eea3e0b09d72f6f43/detail' \
--header 'Content-Type: application/json' \
--data '{
    "product" : "66300f5a47392a565cc0bf9c",
    "quantity" : 1
}'

#Delete detail
curl --location --request DELETE 'http://localhost:8080/api/order/6630000eea3e0b09d72f6f43/detail/6da38014-bb8a-46c9-b727-fbd801188bba'
```