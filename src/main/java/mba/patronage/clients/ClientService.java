package mba.patronage.clients;

import mba.patronage.clients.model.db.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
