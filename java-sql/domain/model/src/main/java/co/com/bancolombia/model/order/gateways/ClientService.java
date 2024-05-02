package co.com.bancolombia.model.order.gateways;

import co.com.bancolombia.model.order.Client;

import java.util.Optional;

public interface ClientService {

    Optional<Client> get(String id);
}
