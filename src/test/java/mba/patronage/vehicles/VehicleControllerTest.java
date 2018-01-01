package mba.patronage.vehicles;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import mba.patronage.config.Api;
import mba.patronage.util.ModelMapper;
import mba.patronage.vehicles.model.db.Vehicle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(DataProviderRunner.class)
@SpringBootTest
public class VehicleControllerTest {

    private static final int VEHICLES_LIST_SIZE = 7;
    private static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;
    private static final String EXPECTED_BRAND = "Vehicle brand";
    private static final String EXPECTED_MODEL = "Vehicle model";
    private static final String EXPECTED_VIN = "CBA123456789FED";
    private static final UUID EXPECTED_UUID = UUID.fromString("5f8a8312-78bc-4cce-a606-f42d107838fe");


    @Mock
    private VehicleService vehicleService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        final VehicleController vehicleController = new VehicleController(vehicleService);
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    @Test
    public void getAllVehicles() throws Exception {
        final List<Vehicle> vehicles = getVehicles();
        when(vehicleService.findAll()).thenReturn(vehicles);

        mockMvc.perform(get(Api.URL_VEHICLE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(VEHICLES_LIST_SIZE)));
    }

    @Test
    public void getOneVehicle() throws Exception {
        Vehicle vehicle = getVehicle();
        when(vehicleService.findById(EXPECTED_UUID)).thenReturn(vehicle);
        mockMvc.perform(get(String.format("%s/%s", Api.URL_VEHICLE, EXPECTED_UUID)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE))
                .andExpect(jsonPath("$.uuid", is(EXPECTED_UUID.toString())))
                .andExpect(jsonPath("$.brand", is(EXPECTED_BRAND)))
                .andExpect(jsonPath("$.model", is(EXPECTED_MODEL)))
                .andExpect(jsonPath("$.vin", is(EXPECTED_VIN)));
    }

    @Test
    public void createVehicle() throws Exception {
        Vehicle vehicle = getVehicle();
        when(vehicleService.create(any(Vehicle.class))).thenReturn(vehicle);
        mockMvc.perform(post(Api.URL_VEHICLE)
                .contentType(CONTENT_TYPE)
                .content(ModelMapper.convertToJsonString(vehicle)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid", is(EXPECTED_UUID.toString())))
                .andExpect(jsonPath("$.brand", is(EXPECTED_BRAND)))
                .andExpect(jsonPath("$.model", is(EXPECTED_MODEL)))
                .andExpect(jsonPath("$.vin", is(EXPECTED_VIN)));
    }


    private List<Vehicle> getVehicles() throws ParseException {

        final List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < VEHICLES_LIST_SIZE; i++) {
            vehicles.add(Vehicle.builder()
                    .brand("Brand " + i)
                    .model("model" + i)
                    .vin("ABC123456789DE" + i)
                    .build());
        }
        return vehicles;
    }

    private Vehicle getVehicle() throws ParseException {
        return Vehicle.builder()
                .uuid(EXPECTED_UUID)
                .brand(EXPECTED_BRAND)
                .model(EXPECTED_MODEL)
                .vin(EXPECTED_VIN)
                .build();
    }


}
