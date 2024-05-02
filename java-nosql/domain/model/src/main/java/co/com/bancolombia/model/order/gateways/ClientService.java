package co.com.bancolombia.model.order.gateways;

import co.com.bancolombia.model.order.Client;
import reactor.core.publisher.Mono;

public interface ClientService {

    Mono<Client> get(String id);
}
