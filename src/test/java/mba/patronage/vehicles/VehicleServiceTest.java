package mba.patronage.vehicles;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import mba.patronage.vehicles.model.db.Vehicle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(DataProviderRunner.class)
public class VehicleServiceTest {

    private static final String EXPECTED_BRAND = "Vehicle brand";
    private static final String EXPECTED_MODEL = "Vehicle model";
    private static final String EXPECTED_VIN = "CBA123456789FED";
    private static final UUID EXPECTED_UUID = UUID.fromString("5f8a8312-78bc-4cce-a606-f42d107838fe");


    @Mock
    private VehicleRepository vehicleRepository;

    private VehicleService vehicleService;

    @DataProvider
    public static Object[] getVehicle() throws ParseException {
        Vehicle vehicle = Vehicle.builder()
                .uuid(EXPECTED_UUID)
                .brand(EXPECTED_BRAND)
                .model(EXPECTED_MODEL)
                .vin(EXPECTED_VIN)
                .build();
        return new Vehicle[]{vehicle};
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vehicleService = new VehicleService(vehicleRepository);
    }


    @Test
    public void findAllVehicles() {
        when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());
        List<Vehicle> vehicleList = vehicleService.findAll();
        assertTrue(vehicleList.isEmpty());
    }

    @Test
    @UseDataProvider("getVehicle")
    public void findVehicleById(final Vehicle vehicle) {
        final UUID vehicleId = vehicle.getUuid();
        when(vehicleRepository.exists(vehicleId)).thenReturn(true);
        when(vehicleRepository.findOne(vehicleId)).thenReturn(vehicle);
        Vehicle dbvVehicle = vehicleService.findById(vehicleId);

        assertEquals(EXPECTED_BRAND, dbvVehicle.getBrand());
        assertEquals(EXPECTED_MODEL, dbvVehicle.getModel());
        assertEquals(EXPECTED_VIN, dbvVehicle.getVin());
    }

    @Test
    @UseDataProvider("getVehicle")
    public void createVehicle(final Vehicle vehicle) throws Exception {
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);
        Vehicle dbVehicle = vehicleService.create(vehicle);

        assertEquals(EXPECTED_BRAND, dbVehicle.getBrand());
        assertEquals(EXPECTED_MODEL, dbVehicle.getModel());
        assertEquals(EXPECTED_VIN, dbVehicle.getVin());
    }

    @Test
    @UseDataProvider("getVehicle")
    public void updateVehicle(final Vehicle vehicle) throws Exception {
        final UUID vehicleUuid = vehicle.getUuid();
        when(vehicleRepository.exists(vehicleUuid)).thenReturn(true);
        when(vehicleRepository.findOne(vehicleUuid)).thenReturn(vehicle);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);
        Vehicle dbVehicle = vehicleService.update(vehicleUuid, vehicle);

        assertEquals(EXPECTED_BRAND, dbVehicle.getBrand());
        assertEquals(EXPECTED_MODEL, dbVehicle.getModel());
        assertEquals(EXPECTED_VIN, dbVehicle.getVin());
    }

    @Test
    public void deleteVehicle() throws Exception {
        final UUID vehicleId = UUID.randomUUID();
        when(vehicleRepository.exists(vehicleId)).thenReturn(true);
        vehicleService.delete(vehicleId);
    }


}
