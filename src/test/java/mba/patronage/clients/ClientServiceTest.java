package mba.patronage.clients;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import mba.patronage.clients.model.db.Client;
import mba.patronage.clients.model.db.Sex;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class ClientServiceTest {

    private static final String EXPECTED_NAME = "Client name";
    private static final String EXPECTED_SURNAME = "Client surname";
    private static final String EXPECTED_PID = "92010203040";
    private static final String EXPECTED_DATE = "1991-12-31";
    private static final UUID EXPECTED_UUID = UUID.fromString("5f8a8312-78bc-4cce-a606-f42d107838fe");
    private static final Sex EXPECTED_SEX = Sex.FEMALE;


    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @DataProvider
    public static Object[] getClient() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashSet<String> name = new HashSet<>();
        name.add(EXPECTED_NAME);
        Client client = Client.builder()
                .uuid(EXPECTED_UUID)
                .names(name)
                .surname(EXPECTED_SURNAME)
                .pid(EXPECTED_PID)
                .dateOfBirth(dateFormat.parse(EXPECTED_DATE))
                .sex(EXPECTED_SEX)
                .build();
        return new Client[]{client};
    }

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

    @Test
    @UseDataProvider("getClient")
    public void findClientById(final Client client) {
        final UUID clientId = client.getUuid();
        when(clientRepository.exists(clientId)).thenReturn(true);
        when(clientRepository.findOne(clientId)).thenReturn(client);
        Client dbClient = clientService.findById(clientId);
        
        assertEquals(EXPECTED_NAME, dbClient.getNames().toArray()[0]);
        assertEquals(EXPECTED_SURNAME, dbClient.getSurname());
        assertEquals(EXPECTED_PID, dbClient.getPid());
        assertEquals(EXPECTED_SEX, dbClient.getSex());
    }

    @Test
    @UseDataProvider("getClient")
    public void createClient(final Client client) throws Exception {
        when(clientRepository.save(client)).thenReturn(client);
        Client dbClient = clientService.create(client);

        assertEquals(EXPECTED_NAME, dbClient.getNames().toArray()[0]);
        assertEquals(EXPECTED_SURNAME, dbClient.getSurname());
        assertEquals(EXPECTED_PID, dbClient.getPid());
        assertEquals(EXPECTED_SEX, dbClient.getSex());
    }

    @Test
    @UseDataProvider("getClient")
    public void updateClient(final Client client) throws Exception {
        final UUID clientUuid = client.getUuid();
        when(clientRepository.exists(clientUuid)).thenReturn(true);
        when(clientRepository.findOne(clientUuid)).thenReturn(client);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        Client dbClient = clientService.update(clientUuid, client);

        assertEquals(EXPECTED_NAME, dbClient.getNames().toArray()[0]);
        assertEquals(EXPECTED_SURNAME, dbClient.getSurname());
        assertEquals(EXPECTED_PID, dbClient.getPid());
        assertEquals(EXPECTED_SEX, dbClient.getSex());
    }

    @Test
    public void deleteClient() throws Exception {
        final UUID clientId = UUID.randomUUID();
        when(clientRepository.exists(clientId)).thenReturn(true);
        clientService.delete(clientId);
    }
}
