package mba.patronage.clients;

import mba.patronage.clients.model.db.Client;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        clientService = new ClientService(clientRepository);
    }

    @Test
    public void findAllClients() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());
        List<Client> clientList = clientService.findAll();
        assertTrue(clientList.isEmpty());
    }
}
