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

    private final ClientRepository clientsRepository;

    @Autowired
    public ClientService(ClientRepository clientsRepository) {
        this.clientsRepository = clientsRepository;
    }

    List<Client> findAll() {
        return clientsRepository.findAll();
    }

    Client findById(UUID id) throws NotFoundException {
        throwClientNotFoundIfDoesNotExist(id, Client.class);
        return clientsRepository.findOne(id);
    }

    private void throwClientNotFoundIfDoesNotExist(UUID id, Class object) throws NotFoundException {
        if (!clientsRepository.exists(id)) {
            throw new NotFoundException(object.getSimpleName());
        }
    }
}
