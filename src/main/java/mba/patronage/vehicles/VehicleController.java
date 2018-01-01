package mba.patronage.vehicles;

import javassist.NotFoundException;
import mba.patronage.config.Api;
import mba.patronage.util.ModelMapper;
import mba.patronage.vehicles.model.db.Vehicle;
import mba.patronage.vehicles.model.view.VehicleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Api.URL_VEHICLE, produces = MediaType.APPLICATION_JSON_VALUE)
class VehicleController {
    private final VehicleService vehicleService;

    @Autowired
    VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public ResponseEntity<List<VehicleView>> getVehicles() {
        final List<Vehicle> vehicles = vehicleService.findAll();
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToViewList(vehicles, VehicleView.class));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VehicleView> getVehicle(@PathVariable UUID id) throws NotFoundException {
        final Vehicle vehicle = vehicleService.findById(id);
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToView(vehicle, VehicleView.class));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<VehicleView> addVehicle(@Valid @RequestBody VehicleView vehicleView) {
        final Vehicle createdVehicle = vehicleService.create(ModelMapper.convertToModel(vehicleView, Vehicle.class));
        final URI baseLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .build().toUri();
        final String vehicleLocationString = String.format("/%s/%s", baseLocation, createdVehicle.getUuid());
        final URI location = UriComponentsBuilder.fromUriString(vehicleLocationString).build().toUri();
        return ResponseEntity
                .created(location)
                .body(ModelMapper.convertToView(createdVehicle, VehicleView.class));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<VehicleView> updateVehicle(@PathVariable UUID id, @Valid @RequestBody VehicleView vehicleView) {
        final Vehicle updatedVehicle = vehicleService.update(id, ModelMapper.convertToModel(vehicleView, Vehicle.class));
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToView(updatedVehicle, VehicleView.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable UUID id) {
        vehicleService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }
}
