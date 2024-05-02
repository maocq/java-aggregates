package co.com.bancolombia.clientconsumer;

import co.com.bancolombia.model.order.Client;
import co.com.bancolombia.model.order.gateways.ClientService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientConsumer implements ClientService {
    @Override
    public Optional<Client> get(String id) {
        if (id.equals("0"))
            return Optional.empty();

        return Optional.of(Client.builder().id(id).name("Foo").build());
    }
}
