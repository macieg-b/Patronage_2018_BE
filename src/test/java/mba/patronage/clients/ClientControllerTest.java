package mba.patronage.clients;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import mba.patronage.clients.model.db.Client;
import mba.patronage.clients.model.db.Sex;
import mba.patronage.config.Url;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class ClientControllerTest {

    private static final int CLIENTS_LIST_SIZE = 7;
    private static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;

    @Mock
    private ClientService clientService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final ClientController clientController = new ClientController(clientService);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void getAll() throws Exception {
        final List<Client> clients = getClients();
        when(clientService.findAll()).thenReturn(clients);

        mockMvc.perform(get(Url.URL_CLIENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(CLIENTS_LIST_SIZE)));
    }

    private List<Client> getClients() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        final List<Client> clients = new ArrayList<>();
        for (int i = 0; i < CLIENTS_LIST_SIZE; i++) {
            HashSet<String> name = new HashSet<>();
            name.add("Name " + Integer.toString(i));
            clients.add(Client.builder()
                    .names(name)
                    .surname("Surname" + i)
                    .pid("9001114478" + i)
                    .dateOfBirth(dateFormat.parse(String.format("0%d/01/1990", i)))
                    .sex(i % 2 == 0 ? Sex.MALE : Sex.FEMALE)
                    .build());
        }
        return clients;
    }


}
