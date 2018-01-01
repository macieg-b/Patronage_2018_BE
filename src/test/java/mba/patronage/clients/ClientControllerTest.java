package mba.patronage.clients;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import mba.patronage.clients.model.db.Client;
import mba.patronage.clients.model.db.Sex;
import mba.patronage.config.Api;
import mba.patronage.util.ModelMapper;
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
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class ClientControllerTest {

    private static final int CLIENTS_LIST_SIZE = 7;
    private static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
    private static final String EXPECTED_NAME = "Client name";
    private static final String EXPECTED_SURNAME = "Client surname";
    private static final String EXPECTED_PID = "92010203040";
    private static final String EXPECTED_DATE = "1991-12-31";
    private static final UUID EXPECTED_UUID = UUID.fromString("5f8a8312-78bc-4cce-a606-f42d107838fe");
    private static final Sex EXPECTED_SEX = Sex.FEMALE;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
    public void getAllClients() throws Exception {
        final List<Client> clients = getClients();
        when(clientService.findAll()).thenReturn(clients);

        mockMvc.perform(get(Api.URL_CLIENT))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(CLIENTS_LIST_SIZE)));
    }

    @Test
    public void getOneClient() throws Exception {
        Client client = getClient();
        when(clientService.findById(EXPECTED_UUID)).thenReturn(client);
        mockMvc.perform(get(String.format("%s/%s", Api.URL_CLIENT, EXPECTED_UUID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.uuid", is(EXPECTED_UUID.toString())))
                .andExpect(jsonPath("$.names", hasSize(1)))
                .andExpect(jsonPath("$.names[0]", is(EXPECTED_NAME)))
                .andExpect(jsonPath("$.surname", is(EXPECTED_SURNAME)))
                .andExpect(jsonPath("$.pid", is(EXPECTED_PID)))
                .andExpect(jsonPath("$.sex", is(EXPECTED_SEX.toString())));
    }

    @Test
    public void createClient() throws Exception {
        Client client = getClient();
        when(clientService.create(any(Client.class))).thenReturn(client);
        mockMvc.perform(post(Api.URL_CLIENT)
                .contentType(CONTENT_TYPE)
                .content(ModelMapper.convertToJsonString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", is(EXPECTED_UUID.toString())))
                .andExpect(jsonPath("$.names", hasSize(1)))
                .andExpect(jsonPath("$.names[0]", is(EXPECTED_NAME)))
                .andExpect(jsonPath("$.surname", is(EXPECTED_SURNAME)))
                .andExpect(jsonPath("$.pid", is(EXPECTED_PID)))
                .andExpect(jsonPath("$.sex", is(EXPECTED_SEX.toString())));
    }

    @Test
    public void updateClient() throws Exception {
        Client client = getClient();
        when(clientService.update(eq(EXPECTED_UUID), any(Client.class))).thenReturn(client);
        mockMvc.perform(put(String.format("%s/%s", Api.URL_CLIENT, EXPECTED_UUID))
                .contentType(CONTENT_TYPE)
                .content(ModelMapper.convertToJsonString(client)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteClient() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(clientService).delete(id);
        mockMvc.perform(delete(String.format("%s/%s", Api.URL_CLIENT, id)))
                .andExpect(status().isOk());
    }

    private List<Client> getClients() throws ParseException {

        final List<Client> clients = new ArrayList<>();
        for (int i = 0; i < CLIENTS_LIST_SIZE; i++) {
            HashSet<String> name = new HashSet<>();
            name.add("Name " + Integer.toString(i));
            clients.add(Client.builder()
                    .names(name)
                    .surname("Surname" + i)
                    .pid("9001114478" + i)
                    .dateOfBirth(dateFormat.parse(String.format("0%d-01-1990", i)))
                    .sex(i % 2 == 0 ? Sex.MALE : Sex.FEMALE)
                    .build());
        }
        return clients;
    }

    private Client getClient() throws ParseException {
        HashSet<String> name = new HashSet<>();
        name.add(EXPECTED_NAME);
        return Client.builder()
                .uuid(EXPECTED_UUID)
                .names(name)
                .surname(EXPECTED_SURNAME)
                .pid(EXPECTED_PID)
                .dateOfBirth(dateFormat.parse(EXPECTED_DATE))
                .sex(EXPECTED_SEX)
                .build();
    }


}
