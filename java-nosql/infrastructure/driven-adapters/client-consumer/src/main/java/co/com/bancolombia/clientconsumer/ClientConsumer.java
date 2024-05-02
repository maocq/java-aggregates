package co.com.bancolombia.clientconsumer;

import co.com.bancolombia.model.order.Client;
import co.com.bancolombia.model.order.gateways.ClientService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ClientConsumer implements ClientService {

    @Override
    public Mono<Client> get(String id) {
        if (id.equals("0"))
            return Mono.empty();

        return Mono.just(Client.builder().id(id).name("Foo").build());
    }
}
