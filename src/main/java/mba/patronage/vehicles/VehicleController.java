package mba.patronage.vehicles;

import mba.patronage.config.Api;
import mba.patronage.util.ModelMapper;
import mba.patronage.vehicles.model.db.Vehicle;
import mba.patronage.vehicles.model.view.VehicleView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
