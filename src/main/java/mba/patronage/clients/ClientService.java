package mba.patronage.clients;

import mba.patronage.clients.model.db.Client;
import mba.patronage.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientsRepository) {
        this.clientRepository = clientsRepository;
    }

    List<Client> findAll() {
        return clientRepository.findAll();
    }

    Client findById(UUID id) throws NotFoundException {
        throwClientNotFoundIfDoesNotExist(id, Client.class);
        return clientRepository.findOne(id);
    }

    Client create(Client client) {
        return clientRepository.save(client);
    }

    Client update(UUID id, Client client) {
        throwClientNotFoundIfDoesNotExist(id, Client.class);
        client.setUuid(id);
        return clientRepository.save(client);
    }

    void delete(UUID id) {
        throwClientNotFoundIfDoesNotExist(id, Client.class);
        clientRepository.delete(id);
    }

    private void throwClientNotFoundIfDoesNotExist(UUID id, Class object) throws NotFoundException {
        if (!clientRepository.exists(id)) {
            throw new NotFoundException(object.getSimpleName());
        }
    }
}
